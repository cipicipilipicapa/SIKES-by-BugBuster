package sikes;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import java.util.List;

public class DokterDetailController {
    
    @FXML private Label dokterNameLabel;
    @FXML private Label spesialisasiLabel;
    @FXML private Label pengalamanLabel;
    @FXML private Label pendidikanLabel;
    @FXML private Label noSIPLabel;
    @FXML private Label rumahSakitLabel;
    @FXML private Label jamPraktikLabel;
    @FXML private Label ratingLabel;
    @FXML private Label konsultasiLabel;
    @FXML private Label statusLabel;
    @FXML private ImageView dokterImageView;
    @FXML private VBox sertifikasiContainer;
    @FXML private Button chatButton;
    @FXML private Button backButton;
    
    private Dokter dokter;
    
    public void setDokter(Dokter dokter) {
        this.dokter = dokter;
        updateUI();
    }
    
    private void updateUI() {
        if (dokter == null) return;
        
        // Update informasi dasar
        dokterNameLabel.setText("Dr. " + dokter.getNama());
        spesialisasiLabel.setText(dokter.getSpesialisasi());
        pengalamanLabel.setText(dokter.getPengalaman());
        pendidikanLabel.setText(dokter.getPendidikan());
        noSIPLabel.setText(dokter.getNoSIP());
        rumahSakitLabel.setText(dokter.getRumahSakit());
        jamPraktikLabel.setText(dokter.getJamPraktik());
        
        // Update rating dan konsultasi
        ratingLabel.setText(String.format("%.1f *", dokter.getRating()));
        konsultasiLabel.setText(dokter.getJumlahKonsultasi() + " konsultasi");
        
        // Update status
        dokter.updateStatusOnline();
        statusLabel.setText(dokter.getStatusText());
        statusLabel.setStyle("-fx-text-fill: " + dokter.getStatusColor() + "; -fx-font-weight: bold;");
        
        // Update gambar dokter
        try {
            Image image = new Image(getClass().getResourceAsStream(dokter.getFotoUrl()));
            dokterImageView.setImage(image);
        } catch (Exception e) {
            // Gunakan gambar default jika tidak ada
            System.err.println("Tidak dapat memuat gambar dokter: " + e.getMessage());
        }
        
        // Update sertifikasi
        sertifikasiContainer.getChildren().clear();
        List<String> sertifikasi = dokter.getSertifikasi();
        for (String sertif : sertifikasi) {
            Label sertifLabel = new Label("• " + sertif);
            sertifLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 12;");
            sertifikasiContainer.getChildren().add(sertifLabel);
        }
        
        // Update tombol chat
        chatButton.setDisable(!dokter.isOnline());
        if (dokter.isOnline()) {
            chatButton.setText("Chat");
            chatButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
        } else {
            chatButton.setText("Offline");
            chatButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        }
    }
    
    @FXML
    private void handleChat() {
        if (dokter != null && dokter.isOnline()) {
            // Increment jumlah konsultasi
            DokterManager.getInstance().incrementKonsultasi(dokter.getId());
            
            // Tampilkan pesan konfirmasi
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mulai Konsultasi");
            alert.setHeaderText(null);
            alert.setContentText("Memulai konsultasi dengan Dr. " + dokter.getNama() + "\n\n" +
                               "Spesialisasi: " + dokter.getSpesialisasi() + "\n" +
                               "Rumah Sakit: " + dokter.getRumahSakit());
            alert.showAndWait();
            
            // Kembali ke halaman konsultasi
            handleBack();
        }
    }
    
    @FXML
    private void handleBack() {
        try {
            Main.showKonsultasiPage();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Tidak dapat kembali ke halaman konsultasi");
            alert.showAndWait();
        }
    }
    
    @FXML
    public void initialize() {
        // Setup tombol
        if (chatButton != null) {
            chatButton.setOnAction(e -> handleChat());
        }
        
        if (backButton != null) {
            backButton.setOnAction(e -> handleBack());
        }
    }
} 