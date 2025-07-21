package sikes;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.List;

public class PengingatController {
    
    @FXML
    private Button logoutButton;
    
    @FXML
    private Label userNameLabel;
    
    @FXML
    private VBox reminderListContainer;
    
    @FXML
    private Button addReminderButton;
    @FXML
    private Button notificationButton;
    
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
    private void handleBeranda() {
        Main.showDashboardPage();
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
    private void handleKonsultasi() {
        try {
            Main.showKonsultasiPage();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Tidak dapat membuka halaman konsultasi");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditProfile() {
        Main.showProfileMenu();
    }
    
    @FXML
    private void handleAddReminder() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("tambah_pengingat.fxml"));
            Parent root = loader.load();
            
            TambahPengingatController controller = loader.getController();
            controller.setPengingatController(this);
            
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Tambah Pengingat");
            dialog.setScene(new Scene(root));
            dialog.setResizable(false);
            dialog.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void refreshReminders() {
        loadReminders();
    }
    
    @FXML
    public void initialize() {
        loadReminders();
    }
    
    private void loadReminders() {
        reminderListContainer.getChildren().clear();
        
        String currentUsername = Main.getCurrentUsername();
        List<ReminderManager.Reminder> reminders = ReminderManager.getRemindersForUser(currentUsername);
        
        if (reminders.isEmpty()) {
            Label noRemindersLabel = new Label("Belum ada pengingat obat");
            noRemindersLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 16px; -fx-padding: 20;");
            reminderListContainer.getChildren().add(noRemindersLabel);
            return;
        }
        
        for (ReminderManager.Reminder reminder : reminders) {
            reminderListContainer.getChildren().add(createReminderCard(reminder));
        }
    }
    
    private VBox createReminderCard(ReminderManager.Reminder reminder) {
        VBox card = new VBox();
        card.setStyle("-fx-background-color: #667eea; -fx-background-radius: 12; -fx-padding: 20; -fx-spacing: 10;");
        card.setMaxWidth(Double.MAX_VALUE);
        
        HBox headerBox = new HBox();
        headerBox.setSpacing(15);
        headerBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        
        Label bellIcon = new Label("🔔");
        bellIcon.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        VBox reminderInfo = new VBox();
        reminderInfo.setSpacing(5);
        
        Label namaObatLabel = new Label(reminder.namaObat);
        namaObatLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 18px;");
        
        Label frekuensiLabel = new Label(reminder.frekuensi);
        frekuensiLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        
        Label waktuLabel = new Label(reminder.waktu);
        waktuLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        
        Label catatanLabel = new Label(reminder.catatan != null && !reminder.catatan.isEmpty() ? reminder.catatan : "");
        catatanLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        
        reminderInfo.getChildren().addAll(namaObatLabel, frekuensiLabel, waktuLabel);
        if (reminder.catatan != null && !reminder.catatan.isEmpty()) {
            reminderInfo.getChildren().add(catatanLabel);
        }
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        HBox actionButtons = new HBox();
        actionButtons.setSpacing(10);
        
        Button editButton = new Button("Edit");
        editButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 12px; -fx-cursor: hand; -fx-padding: 5 10;");
        editButton.setOnAction(e -> handleEditReminder(reminder));
        
        Button deleteButton = new Button("Hapus");
        deleteButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 12px; -fx-cursor: hand; -fx-padding: 5 10;");
        deleteButton.setOnAction(e -> handleDeleteReminder(reminder));
        
        actionButtons.getChildren().addAll(editButton, deleteButton);
        
        headerBox.getChildren().addAll(bellIcon, reminderInfo, spacer, actionButtons);
        card.getChildren().add(headerBox);
        
        return card;
    }
    
    private void handleEditReminder(ReminderManager.Reminder reminder) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("tambah_pengingat.fxml"));
            Parent root = loader.load();
            
            TambahPengingatController controller = loader.getController();
            controller.setPengingatController(this);
            controller.setReminderForEditing(reminder);
            
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Edit Pengingat");
            dialog.setScene(new Scene(root));
            dialog.setResizable(false);
            dialog.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void handleDeleteReminder(ReminderManager.Reminder reminder) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Hapus");
        alert.setHeaderText(null);
        alert.setContentText("Apakah Anda yakin ingin menghapus pengingat ini?");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                ReminderManager.deleteReminder(reminder.id);
                refreshReminders();
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