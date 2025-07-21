package sikes;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class ReminderPopupController {
    
    @FXML
    private VBox reminderDetailsContainer;
    
    @FXML
    private Button okButton;
    
    private ReminderManager.Reminder reminder;
    
    public void setReminder(ReminderManager.Reminder reminder) {
        this.reminder = reminder;
        displayReminderDetails();
    }
    
    private void displayReminderDetails() {
        reminderDetailsContainer.getChildren().clear();
        
        if (reminder != null) {
            Label namaObatLabel = new Label(reminder.namaObat);
            namaObatLabel.setStyle("-fx-text-fill: #333; -fx-font-weight: bold; -fx-font-size: 16px;");
            
            Label frekuensiLabel = new Label(reminder.frekuensi);
            frekuensiLabel.setStyle("-fx-text-fill: #333; -fx-font-size: 14px;");
            
            Label catatanLabel = new Label(reminder.catatan != null && !reminder.catatan.isEmpty() ? reminder.catatan : "");
            catatanLabel.setStyle("-fx-text-fill: #333; -fx-font-size: 14px;");
            
            reminderDetailsContainer.getChildren().addAll(namaObatLabel, frekuensiLabel);
            if (reminder.catatan != null && !reminder.catatan.isEmpty()) {
                reminderDetailsContainer.getChildren().add(catatanLabel);
            }
        }
    }
    
    @FXML
    private void handleOK(ActionEvent event) {
        // Close the popup
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        
        // Close the popup in ReminderManager
        ReminderManager.closeCurrentPopup();
    }
} 