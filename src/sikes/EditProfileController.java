package sikes;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EditProfileController {
    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private Button saveButton;
    @FXML private Label errorLabel;

    private Main.UserUtil.User currentUser;

    @FXML
    public void initialize() {
        System.out.println("EditProfileController initialize called");
        String username = Main.getCurrentUsername();
        currentUser = Main.UserUtil.getUserByUsername(username);
        if (currentUser != null) {
            nameField.setText(currentUser.username);
            phoneField.setText(currentUser.phone);
        }
    }

    @FXML
    private void handleSave() {
        String newName = nameField.getText().trim();
        String newPhone = phoneField.getText().trim();
        if (newName.isEmpty() || newPhone.isEmpty()) {
            showError("Nama dan nomor telepon tidak boleh kosong!");
            return;
        }
        // Update user object
        Main.UserUtil.User updated = new Main.UserUtil.User(newName, currentUser.email, currentUser.password, newPhone);
        boolean ok = Main.UserUtil.updateUser(currentUser.username, updated);
        if (ok) {
            Main.setCurrentUsername(newName);
            // Tutup modal setelah berhasil
            ((javafx.stage.Stage) saveButton.getScene().getWindow()).close();
        } else {
            showError("Gagal menyimpan perubahan!");
        }
    }

    @FXML
    private void handleBack() {
        System.out.println("handleBack called, saveButton=" + saveButton);
        ((javafx.stage.Stage) saveButton.getScene().getWindow()).close();
    }

    // Overload: versi dengan ActionEvent
    @FXML
    private void handleBack(javafx.event.ActionEvent event) {
        System.out.println("handleBack called (event version)");
        ((javafx.stage.Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }

    private void showError(String msg) {
        errorLabel.setText(msg);
        errorLabel.setVisible(true);
    }
} 