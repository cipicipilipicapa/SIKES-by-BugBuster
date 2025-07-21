package sikes;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class TambahPengingatController {
    
    @FXML
    private TextField namaObatField;
    
    @FXML
    private TextField frekuensiField;
    
    @FXML
    private TextField jamField;
    
    @FXML
    private TextField menitField;
    
    @FXML
    private TextArea catatanField;
    
    @FXML
    private Button saveButton;
    
    @FXML
    private Button cancelButton;
    
    private PengingatController pengingatController;
    private ReminderManager.Reminder editingReminder;
    
    public void setPengingatController(PengingatController controller) {
        this.pengingatController = controller;
    }
    
    public void setReminderForEditing(ReminderManager.Reminder reminder) {
        this.editingReminder = reminder;
        populateFields(reminder);
    }
    
    private void populateFields(ReminderManager.Reminder reminder) {
        namaObatField.setText(reminder.namaObat);
        frekuensiField.setText(reminder.frekuensi);
        
        String[] timeParts = reminder.waktu.split(":");
        if (timeParts.length == 2) {
            jamField.setText(timeParts[0]);
            menitField.setText(timeParts[1]);
        }
        
        if (reminder.catatan != null) {
            catatanField.setText(reminder.catatan);
        }
    }
    
    @FXML
    private void handleSave(ActionEvent event) {
        if (!validateFields()) {
            return;
        }
        
        String namaObat = namaObatField.getText().trim();
        String frekuensi = frekuensiField.getText().trim();
        String jam = jamField.getText().trim();
        String menit = menitField.getText().trim();
        String catatan = catatanField.getText().trim();
        
        String waktu = String.format("%02d:%02d", Integer.parseInt(jam), Integer.parseInt(menit));
        String username = Main.getCurrentUsername();
        
        if (editingReminder != null) {
            // Delete old reminder and add new one
            ReminderManager.deleteReminder(editingReminder.id);
        }
        
        ReminderManager.addReminder(username, namaObat, frekuensi, waktu, catatan);
        
        if (pengingatController != null) {
            pengingatController.refreshReminders();
        }
        
        closeDialog(event);
    }
    
    @FXML
    private void handleCancel(ActionEvent event) {
        closeDialog(event);
    }
    
    private void closeDialog(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    private boolean validateFields() {
        // Validate nama obat
        if (namaObatField.getText().trim().isEmpty()) {
            showAlert("Error", "Nama obat harus diisi");
            namaObatField.requestFocus();
            return false;
        }
        
        // Validate frekuensi
        if (frekuensiField.getText().trim().isEmpty()) {
            showAlert("Error", "Frekuensi harus diisi");
            frekuensiField.requestFocus();
            return false;
        }
        
        // Validate jam
        String jam = jamField.getText().trim();
        if (jam.isEmpty()) {
            showAlert("Error", "Jam harus diisi");
            jamField.requestFocus();
            return false;
        }
        
        try {
            int jamInt = Integer.parseInt(jam);
            if (jamInt < 0 || jamInt > 23) {
                showAlert("Error", "Jam harus antara 0-23");
                jamField.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Jam harus berupa angka");
            jamField.requestFocus();
            return false;
        }
        
        // Validate menit
        String menit = menitField.getText().trim();
        if (menit.isEmpty()) {
            showAlert("Error", "Menit harus diisi");
            menitField.requestFocus();
            return false;
        }
        
        try {
            int menitInt = Integer.parseInt(menit);
            if (menitInt < 0 || menitInt > 59) {
                showAlert("Error", "Menit harus antara 0-59");
                menitField.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Menit harus berupa angka");
            menitField.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    public void initialize() {
        // Add input validation for time fields
        jamField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                jamField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (newValue.length() > 2) {
                jamField.setText(oldValue);
            }
        });
        
        menitField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                menitField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (newValue.length() > 2) {
                menitField.setText(oldValue);
            }
        });
    }
} 