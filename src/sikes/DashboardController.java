package sikes;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public class DashboardController {
    
    @FXML
    private Button logoutButton;
    
    @FXML
    private Label userNameLabel;
    
    @FXML
    private Button notificationButton;
    
    @FXML private Label gulaTerakhirValue;
    @FXML private Label gulaTerakhirStatus;
    @FXML private Label tekananTerakhirValue;
    @FXML private Label tekananTerakhirStatus;
    @FXML private Label tinggiBeratValue;
    @FXML private Label beratIdealStatus;
    
    @FXML private VBox articleCard1;
    @FXML private VBox articleCard2;
    @FXML private VBox articleCard3;
    
    public void setUsername(String username) {
        if (userNameLabel != null) {
            userNameLabel.setText(username);
        }
    }
    
    @FXML
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Logout");
        alert.setHeaderText(null);
        alert.setContentText("Apakah Anda yakin ingin keluar?");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Main.showLoginPage();
            }
        });
    }
    
    @FXML
    private void handleArtikel() {
        Main.showArtikelPage();
    }
    
    @FXML
    private void handleInputData() {
        Main.showInputDataPage();
    }

    @FXML
    private void handleData() {
        Main.showDataPage();
    }

    @FXML
    private void handleBacaSelengkapnyaPenyebab() {
        Main.showArtikelDetailPage();
    }

    @FXML
    private void handleBacaSelengkapnyaMakanan() {
        Main.showArtikelDetailMakananPage();
    }

    @FXML
    private void handleBacaSelengkapnyaObat() {
        Main.showArtikelDetailObatPage();
    }

    @FXML
    private void handleEditProfile() {
        Main.showProfileMenu();
    }

    @FXML
    public void initialize() {
        tampilkanDataTerakhir();
        // Event klik pada kotak artikel
        if (articleCard1 != null) {
            articleCard1.setOnMouseClicked(e -> handleBacaSelengkapnyaPenyebab());
        }
        if (articleCard2 != null) {
            articleCard2.setOnMouseClicked(e -> handleBacaSelengkapnyaMakanan());
        }
        if (articleCard3 != null) {
            articleCard3.setOnMouseClicked(e -> handleBacaSelengkapnyaObat());
        }
    }

    @FXML
    private void handleKonsultasi() {
        try {
            Main.showKonsultasiPage();
        } catch (Exception e) {
            e.printStackTrace();
            // Menggunakan Alert langsung tanpa method showAlert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Tidak dapat membuka halaman konsultasi");
            alert.showAndWait();
        }
    }

    @FXML
    private void handlePengingat() {
        try {
            Main.showPengingatPage();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Tidak dapat membuka halaman pengingat");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleNotificationClick() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("notification_popup.fxml"));
            Parent root = loader.load();
            
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.NONE);
            popupStage.initOwner(notificationButton.getScene().getWindow());
            popupStage.setTitle("Notifikasi");
            popupStage.setScene(new Scene(root));
            popupStage.setResizable(false);
            
            // Position the popup near the notification button
            popupStage.setX(notificationButton.getScene().getWindow().getX() + 
                           notificationButton.getScene().getWindow().getWidth() - 350);
            popupStage.setY(notificationButton.getScene().getWindow().getY() + 80);
            
            popupStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Tidak dapat membuka notifikasi");
            alert.showAndWait();
        }
    }

    private void tampilkanDataTerakhir() {
        java.util.List<Main.RiwayatData> riwayatList = Main.UserUtil.loadRiwayat(Main.getCurrentUsername());
        if (riwayatList.isEmpty()) {
            if (gulaTerakhirValue != null) gulaTerakhirValue.setText("-");
            if (gulaTerakhirStatus != null) gulaTerakhirStatus.setText("-");
            if (tekananTerakhirValue != null) tekananTerakhirValue.setText("-");
            if (tekananTerakhirStatus != null) tekananTerakhirStatus.setText("-");
            if (tinggiBeratValue != null) tinggiBeratValue.setText("-");
            if (beratIdealStatus != null) beratIdealStatus.setText("-");
            return;
        }
        Main.RiwayatData last = riwayatList.get(riwayatList.size() - 1);
        // Gula darah
        double gula = 0;
        try { gula = Double.parseDouble(last.gula); } catch(Exception e){}
        String gulaStatus;
        if (gula < 140) gulaStatus = "Normal";
        else if (gula < 200) gulaStatus = "Pra-Diabetes";
        else gulaStatus = "Diabetes";
        if (gulaTerakhirValue != null) gulaTerakhirValue.setText(last.gula + " mg/dL");
        if (gulaTerakhirStatus != null) gulaTerakhirStatus.setText(gulaStatus);
        // Tekanan darah
        if (tekananTerakhirValue != null) tekananTerakhirValue.setText(last.tekanan + " mmHg");
        // Status tekanan darah sederhana (bisa dikembangkan)
        String statusTekanan = "-";
        String[] tekanan = last.tekanan.split("/");
        int sistolik = 0, diastolik = 0;
        if (tekanan.length == 2) {
            try {
                diastolik = Integer.parseInt(tekanan[0].replaceAll("[^0-9]", ""));
                sistolik = Integer.parseInt(tekanan[1].replaceAll("[^0-9]", ""));
            } catch(Exception e){}
        }
        if (sistolik < 120 && diastolik < 80) statusTekanan = "Ideal";
        else if (sistolik < 130 && diastolik < 85) statusTekanan = "Normal";
        else if (sistolik < 140 && diastolik < 90) statusTekanan = "Waspada";
        else statusTekanan = "Tinggi";
        if (tekananTerakhirStatus != null) tekananTerakhirStatus.setText(statusTekanan);
        // Tinggi & Berat Badan + Status Ideal
        if (tinggiBeratValue != null && beratIdealStatus != null) {
            String tinggiStr = last.tinggi != null ? last.tinggi : "-";
            String beratStr = last.berat != null ? last.berat : "-";
            tinggiBeratValue.setText(tinggiStr + " cm / " + beratStr + " kg");
            try {
                double tinggi = Double.parseDouble(tinggiStr);
                double berat = Double.parseDouble(beratStr);
                double tinggiMeter = tinggi / 100.0;
                double bmi = berat / (tinggiMeter * tinggiMeter);
                String status;
                if (bmi < 18.5) status = "Kurus";
                else if (bmi < 25) status = "Ideal";
                else if (bmi < 30) status = "Gemuk";
                else status = "Obesitas";
                beratIdealStatus.setText(status);
            } catch(Exception e) {
                beratIdealStatus.setText("-");
            }
        }
    }
}