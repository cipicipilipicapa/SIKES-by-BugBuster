package sikes;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class InputDataController {
    
    @FXML private TextField tinggiBadanField;
    @FXML private TextField beratBadanField;
    @FXML private TextField gulaDarahField;
    @FXML private TextField tekananDarahField;
    @FXML private Button simpanButton;
    @FXML private VBox riwayatContainer;
    @FXML private Label userNameLabel;
    @FXML private Button notificationButton;
    
    // Other button declarations...
    
    // Tambahkan class untuk menyimpan data riwayat
    public static class RiwayatData {
        public String tanggal, tinggi, berat, gula, tekanan;
        public RiwayatData(String tanggal, String tinggi, String berat, String gula, String tekanan) {
            this.tanggal = tanggal;
            this.tinggi = tinggi;
            this.berat = berat;
            this.gula = gula;
            this.tekanan = tekanan;
        }
    }
    // List statis untuk menyimpan riwayat
    private java.util.List<Main.RiwayatData> riwayatList = new java.util.ArrayList<>();
    
    // Tambahkan variabel untuk mode edit
    private int editIndex = -1;
    
    public void setUsername(String username) {
        if (userNameLabel != null) {
            userNameLabel.setText(username);
        }
        // Load riwayat dari XML
        riwayatList = Main.UserUtil.loadRiwayat(username);
        tampilkanRiwayat();
    }
    
    @FXML
    private void handleSimpan() {
        // Validate inputs
        if (tinggiBadanField.getText().isEmpty() || beratBadanField.getText().isEmpty() || gulaDarahField.getText().isEmpty() || tekananDarahField.getText().isEmpty()) {
            
            showAlert("Error", "Semua field harus diisi!");
            return;
        }
        
        try {
            // Create history labels
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String currentDate = LocalDate.now().format(formatter);
            
            if (editIndex >= 0) {
                // Edit mode: update data
                Main.RiwayatData data = riwayatList.get(editIndex);
                data.tinggi = tinggiBadanField.getText();
                data.berat = beratBadanField.getText();
                data.gula = gulaDarahField.getText();
                data.tekanan = tekananDarahField.getText();
                // Optionally update date: data.tanggal = currentDate;
                Main.UserUtil.saveRiwayat(userNameLabel.getText(), riwayatList);
                tampilkanRiwayat();
                showAlert("Sukses", "Data berhasil diupdate!");
                editIndex = -1;
                simpanButton.setText("Simpan Data");
            } else {
                // Simpan ke list statis
                riwayatList.add(new Main.RiwayatData(
                    currentDate,
                    tinggiBadanField.getText(),
                    beratBadanField.getText(),
                    gulaDarahField.getText(),
                    tekananDarahField.getText()
                ));
                Main.UserUtil.saveRiwayat(userNameLabel.getText(), riwayatList);
                tampilkanRiwayat();
                showAlert("Sukses", "Data berhasil disimpan!");
            }
            // Bersihkan field setelah simpan/edit
            tinggiBadanField.clear();
            beratBadanField.clear();
            gulaDarahField.clear();
            tekananDarahField.clear();
            
        } catch (Exception e) {
            showAlert("Error", "Terjadi kesalahan saat menyimpan data");
        }
    }
    
    @FXML
    private void handleHapusRiwayat() {
        riwayatList.clear();
        Main.UserUtil.saveRiwayat(userNameLabel.getText(), riwayatList);
        tampilkanRiwayat();
    }
    
    private Label createHistoryLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: #333; -fx-padding: 3px 0;");
        return label;
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    // Method untuk menampilkan seluruh riwayat
    private void tampilkanRiwayat() {
        riwayatContainer.getChildren().clear();
        if (riwayatList.isEmpty()) {
            riwayatContainer.getChildren().add(createHistoryLabel("Belum ada data yang disimpan"));
        } else {
            for (int i = 0; i < riwayatList.size(); i++) {
                Main.RiwayatData data = riwayatList.get(i);
                Button btnHapus = new Button("Hapus");
                btnHapus.setStyle("-fx-background-color: #ff3b3b; -fx-text-fill: white; -fx-font-size: 15px; -fx-cursor: hand; -fx-padding: 4 24 4 24; -fx-background-radius: 7; -fx-border-color: #b71c1c; -fx-border-width: 2px; -fx-font-weight: bold;");
                int idx = i;
                btnHapus.setOnAction(e -> {
                    riwayatList.remove(idx);
                    Main.UserUtil.saveRiwayat(userNameLabel.getText(), riwayatList);
                    tampilkanRiwayat();
                });
                Button btnEdit = new Button("Edit");
                btnEdit.setStyle("-fx-background-color: #fbbf24; -fx-text-fill: #333; -fx-font-size: 15px; -fx-cursor: hand; -fx-padding: 4 24 4 24; -fx-background-radius: 7; -fx-border-color: #b45309; -fx-border-width: 2px; -fx-font-weight: bold;");
                btnEdit.setMinWidth(80);
                btnEdit.setMaxWidth(120);
                btnEdit.setOnAction(e -> {
                    editRiwayat(idx);
                });
                VBox detailBox = new VBox(
                    createHistoryLabel("Tanggal: " + data.tanggal),
                    createHistoryLabel("Tinggi Badan: " + data.tinggi + " cm"),
                    createHistoryLabel("Berat Badan: " + data.berat + " kg"),
                    createHistoryLabel("Kadar Gula Darah: " + data.gula + " mg/dl"),
                    createHistoryLabel("Tekanan Darah: " + data.tekanan + " mmHg")
                );
                detailBox.setSpacing(2);
                HBox.setHgrow(detailBox, Priority.ALWAYS);
                btnHapus.setMinWidth(80);
                btnHapus.setMaxWidth(120);
                HBox row = new HBox(20, detailBox, btnEdit, btnHapus);
                row.setStyle("-fx-alignment: center-left; -fx-padding: 8 0 8 0;");
                riwayatContainer.getChildren().add(row);
            }
        }
    }

    // Ubah method editRiwayat agar mengisi field dan set edit mode
    private void editRiwayat(int idx) {
        Main.RiwayatData data = riwayatList.get(idx);
        tinggiBadanField.setText(data.tinggi);
        beratBadanField.setText(data.berat);
        gulaDarahField.setText(data.gula);
        tekananDarahField.setText(data.tekanan);
        editIndex = idx;
        simpanButton.setText("Update Data");
    }
    
    // Other navigation methods...
    @FXML private void handleBeranda() { Main.showDashboardPage(); }
    @FXML private void handleData() { Main.showDataPage(); }
    @FXML private void handleArtikel() { Main.showArtikelPage(); }
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
    
    @FXML private void handleEditProfile() {
        Main.showProfileMenu();
    }
    
    @FXML private void handleLogout() { 
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
}