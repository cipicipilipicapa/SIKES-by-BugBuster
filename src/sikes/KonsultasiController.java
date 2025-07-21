package sikes;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class KonsultasiController {
    
    @FXML private Button berandaButton;
    @FXML private Button inputDataButton;
    @FXML private Button dataButton;
    @FXML private Button artikelButton;
    @FXML private Button logoutButton;
    @FXML private Label userNameLabel;
    
    @FXML private ListView<sikes.Dokter> dokterListView;
    @FXML private TextField searchDokterField;
    @FXML private Label dokterNameLabel;
    @FXML private Label dokterStatusLabel;
    @FXML private VBox chatMessagesContainer;
    @FXML private TextField messageField;
    @FXML private Button sendButton;
    @FXML private Button notificationButton;
    
    private DokterManager dokterManager;
    private Dokter selectedDokter = null;
    
    public void setUsername(String username) {
        if (userNameLabel != null) {
            userNameLabel.setText(username);
        }
    }
    
    @FXML
    private void initialize() {
        // Null checks untuk memastikan semua elemen FXML terikat dengan benar
        if (dokterListView == null) {
            System.err.println("Error: dokterListView tidak terikat dengan FXML");
            return;
        }
        if (searchDokterField == null) {
            System.err.println("Error: searchDokterField tidak terikat dengan FXML");
            return;
        }
        if (dokterNameLabel == null) {
            System.err.println("Error: dokterNameLabel tidak terikat dengan FXML");
            return;
        }
        if (dokterStatusLabel == null) {
            System.err.println("Error: dokterStatusLabel tidak terikat dengan FXML");
            return;
        }
        if (chatMessagesContainer == null) {
            System.err.println("Error: chatMessagesContainer tidak terikat dengan FXML");
            return;
        }
        if (messageField == null) {
            System.err.println("Error: messageField tidak terikat dengan FXML");
            return;
        }
        if (sendButton == null) {
            System.err.println("Error: sendButton tidak terikat dengan FXML");
            return;
        }
        
        // Inisialisasi DokterManager
        dokterManager = DokterManager.getInstance();
        
        if (dokterListView != null) {
            dokterListView.setItems(dokterManager.getDokterList());
            dokterListView.setCellFactory(lv -> new DokterListCell());
            
            // Tambahkan event handler untuk double-click
            dokterListView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    sikes.Dokter selectedDokter = dokterListView.getSelectionModel().getSelectedItem();
                    if (selectedDokter != null) {
                        showDokterDetail(selectedDokter);
                    }
                }
            });
        } else {
            System.err.println("Warning: dokterListView tidak terikat dengan FXML");
        }
        
        // Handle seleksi dokter
        if (dokterListView != null && dokterNameLabel != null && dokterStatusLabel != null) {
            dokterListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    selectedDokter = newVal;
                    dokterNameLabel.setText("Dr. " + newVal.getNama());
                    dokterStatusLabel.setText(newVal.getStatusText());
                    dokterStatusLabel.setStyle("-fx-text-fill: " + newVal.getStatusColor() + ";");
                    
                    // Bersihkan chat sebelumnya
                    if (chatMessagesContainer != null) {
                        chatMessagesContainer.getChildren().clear();
                        
                        // Tambahkan pesan default jika dokter online
                        if (newVal.isOnline()) {
                            addMessage("Dr. " + newVal.getNama(), "Hello, ada yang bisa saya bantu?", false);
                        } else {
                            addMessage("System", "Dokter sedang offline. Silakan pilih dokter lain.", false);
                        }
                    }
                }
            });
        } else {
            System.err.println("Warning: dokterListView, dokterNameLabel, atau dokterStatusLabel tidak terikat dengan FXML");
        }
        
        // Filter pencarian dokter
        if (searchDokterField != null) {
            searchDokterField.textProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal == null || newVal.isEmpty()) {
                    dokterListView.setItems(dokterManager.getDokterList());
                } else {
                    List<Dokter> filteredList = dokterManager.searchDokter(newVal);
                    dokterListView.setItems(FXCollections.observableArrayList(filteredList));
                }
            });
        } else {
            System.err.println("Warning: searchDokterField tidak terikat dengan FXML");
        }

        // Tambahkan listener untuk mengaktifkan/menonaktifkan tombol kirim
        if (messageField != null && sendButton != null) {
            messageField.textProperty().addListener((obs, oldVal, newVal) -> {
                sendButton.setDisable(newVal == null || newVal.trim().isEmpty());
            });

            // Nonaktifkan tombol kirim awal
            sendButton.setDisable(true);
        } else {
            System.err.println("Warning: messageField atau sendButton tidak terikat dengan FXML");
        }

    }
    
    @FXML
    private void handleSendMessage() {
        try {
            if (selectedDokter == null) {
                showAlert("Peringatan", "Silakan pilih dokter terlebih dahulu");
                return;
            }
        
            if (!selectedDokter.isOnline()) {
                showAlert("Peringatan", "Dokter sedang offline. Tidak dapat mengirim pesan.");
                return;
            }
        
            if (messageField == null) {
                System.err.println("Error: messageField tidak tersedia");
                return;
            }
            
            String message = messageField.getText().trim();
            if (message.isEmpty()) {
                return;
            }
        
            // Tambahkan pesan pengguna
            addMessage("Anda", message, true);
        
            // Simulasikan balasan dokter setelah 1-3 detik
            if (selectedDokter.isOnline()) {
                new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            String[] responses = {
                                "Bisa Anda jelaskan lebih detail gejala yang Anda alami?",
                                "Apakah Anda sudah mencoba mengonsumsi obat tertentu?",
                                "Saya sarankan Anda untuk beristirahat yang cukup",
                                "Berapa lama keluhan ini berlangsung?",
                                "Apakah ada gejala lain yang menyertai?",
                                "Untuk kondisi seperti ini, saya rekomendasikan pemeriksaan lebih lanjut",
                                "Apakah Anda memiliki riwayat penyakit serupa sebelumnya?"
                            };
                            String response = responses[(int)(Math.random() * responses.length)];
                            javafx.application.Platform.runLater(() -> {
                                addMessage(selectedDokter.getNama(), response, false);
                            });
                        }
                    }, 
                    (long)(1000 + Math.random() * 2000) // Delay 1-3 detik
                );
            }
        
            messageField.clear();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Terjadi kesalahan saat mengirim pesan");
        }
    }
    
    private void addMessage(String sender, String message, boolean isUser) {
        if (chatMessagesContainer == null) {
            System.err.println("Error: chatMessagesContainer tidak tersedia");
            return;
        }
        
        LocalTime time = LocalTime.now();
        String formattedTime = time.format(DateTimeFormatter.ofPattern("HH:mm"));
        
        // Container untuk satu pesan
        VBox messageContainer = new VBox();
        messageContainer.setStyle("-fx-spacing: 2; -fx-padding: 5;");
        
        // Header (sender dan waktu)
        HBox header = new HBox();
        header.setStyle("-fx-spacing: 5;");
        
        Label senderLabel = new Label(sender);
        senderLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: " + (isUser ? "#667eea" : "#333") + ";");
        
        Label timeLabel = new Label(formattedTime);
        timeLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 11;");
        
        header.getChildren().addAll(senderLabel, timeLabel);
        
        // Isi pesan
        TextFlow messageText = new TextFlow();
        messageText.setStyle("-fx-background-color: " + (isUser ? "#e6f0ff" : "#f0f0f0") + "; " +
                             "-fx-background-radius: 10; " +
                             "-fx-padding: 8 12;");
        
        Text text = new Text(message);
        text.setStyle("-fx-fill: #333;");
        messageText.getChildren().add(text);
        
        messageContainer.getChildren().addAll(header, messageText);
        
        // Atur alignment berdasarkan pengirim
        HBox messageBox = new HBox();
        messageBox.setStyle("-fx-alignment: " + (isUser ? "center-right" : "center-left") + "; " +
                           "-fx-padding: 5 0;");
        messageBox.getChildren().add(messageContainer);
        
        chatMessagesContainer.getChildren().add(messageBox);
        
        // Scroll ke bawah dengan cara yang lebih aman
        javafx.application.Platform.runLater(() -> {
            if (chatMessagesContainer.getParent() instanceof ScrollPane) {
                ScrollPane scrollPane = (ScrollPane) chatMessagesContainer.getParent();
                scrollPane.setVvalue(1.0);
            }
        });

        // Sinkronkan ke notifikasi jika balasan dokter
        if (!isUser) {
            NotificationPopupController.addNotification(sender, formattedTime, message);
        }
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showDokterDetail(sikes.Dokter dokter) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detail Dokter");
        alert.setHeaderText("Dr. " + dokter.getNama());
        
        String content = String.format(
            "Spesialisasi: %s\n" +
            "Pengalaman: %s\n" +
            "Pendidikan: %s\n" +
            "No. SIP: %s\n" +
            "Rumah Sakit: %s\n" +
            "Jam Praktik: %s\n" +
            "Rating: * %.1f\n" +
            "Jumlah Konsultasi: %d\n" +
            "Status: %s\n\n" +
            "Sertifikasi:\n%s",
            dokter.getSpesialisasi(),
            dokter.getPengalaman(),
            dokter.getPendidikan(),
            dokter.getNoSIP(),
            dokter.getRumahSakit(),
            dokter.getJamPraktik(),
            dokter.getRating(),
            dokter.getJumlahKonsultasi(),
            dokter.getStatusText(),
            String.join("\n• ", dokter.getSertifikasi())
        );
        
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    // Custom cell untuk menampilkan dokter
    private class DokterListCell extends ListCell<sikes.Dokter> {
        @Override
        protected void updateItem(sikes.Dokter dokter, boolean empty) {
            super.updateItem(dokter, empty);
        
            if (empty || dokter == null) {
                setText(null);
                setGraphic(null);
            } else {
                VBox container = new VBox();
                container.setStyle("-fx-spacing: 2; -fx-padding: 8;");
            
                HBox header = new HBox();
                header.setStyle("-fx-spacing: 5; -fx-alignment: center-left;");
            
                Label nameLabel = new Label("Dr. " + dokter.getNama());
                nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
            
                Label statusLabel = new Label(dokter.getStatusText());
                statusLabel.setStyle("-fx-text-fill: " + dokter.getStatusColor() + "; " +
                                "-fx-font-size: 11;");
            
                header.getChildren().addAll(nameLabel, statusLabel);
            
                Label spesialisasiLabel = new Label(dokter.getSpesialisasi());
                spesialisasiLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 12;");
                
                Label ratingLabel = new Label(String.format("* %.1f (%d konsultasi)", dokter.getRating(), dokter.getJumlahKonsultasi()));
                ratingLabel.setStyle("-fx-text-fill: #888; -fx-font-size: 11;");
            
                container.getChildren().addAll(header, spesialisasiLabel, ratingLabel);
                setGraphic(container);
            }
        }
    }

    
    // Navigation methods
    @FXML
    private void handleBeranda() {
        Main.showDashboardPage();
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
    private void handleArtikel() {
        Main.showArtikelPage();
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
    private void handleEditProfile() {
        Main.showProfileMenu();
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