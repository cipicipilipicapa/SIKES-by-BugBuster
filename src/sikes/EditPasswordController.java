package sikes;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EditPasswordController {
    @FXML private PasswordField oldPasswordField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private TextField oldEmailField;
    @FXML private TextField newEmailField;
    @FXML private TextField confirmEmailField;
    @FXML private Button saveButton;
    @FXML private Label errorLabel;

    private Main.UserUtil.User currentUser;

    @FXML
    public void initialize() {
        System.out.println("EditPasswordController initialize start");
        String username = Main.getCurrentUsername();
        System.out.println("Current username: " + username);
        currentUser = Main.UserUtil.getUserByUsername(username);
        System.out.println("Current user: " + currentUser);
        if (currentUser != null) {
            oldEmailField.setText(currentUser.email);
        }
        System.out.println("EditPasswordController initialize end");
    }

    @FXML
    private void handleSave() {
        String oldPass = oldPasswordField.getText();
        String newPass = newPasswordField.getText();
        String confirmPass = confirmPasswordField.getText();
        String oldEmail = oldEmailField.getText();
        String newEmail = newEmailField.getText();
        String confirmEmail = confirmEmailField.getText();

        if (oldPass.isEmpty() && oldEmail.isEmpty()) {
            showError("Isi password/email lama!");
            return;
        }
        // Validasi password
        if (!oldPass.isEmpty() && (!oldPass.equals(currentUser.password))) {
            showError("Password lama salah!");
            return;
        }
        if (!newPass.isEmpty() && !newPass.equals(confirmPass)) {
            showError("Konfirmasi password tidak cocok!");
            return;
        }
        // Validasi email
        if (!oldEmail.isEmpty() && (!oldEmail.equals(currentUser.email))) {
            showError("Gmail lama salah!");
            return;
        }
        if (!newEmail.isEmpty() && !newEmail.equals(confirmEmail)) {
            showError("Konfirmasi gmail tidak cocok!");
            return;
        }
        // Update user
        String finalPass = newPass.isEmpty() ? currentUser.password : newPass;
        String finalEmail = newEmail.isEmpty() ? currentUser.email : newEmail;
        Main.UserUtil.User updated = new Main.UserUtil.User(currentUser.username, finalEmail, finalPass, currentUser.phone);
        boolean ok = Main.UserUtil.updateUser(currentUser.username, updated);
        if (ok) {
            // Tutup modal setelah berhasil
            ((javafx.stage.Stage) saveButton.getScene().getWindow()).close();
            // Jika ingin redirect ke login/dashboard, panggil di luar modal
        } else {
            showError("Gagal menyimpan perubahan!");
        }
    }

    @FXML
    private void handleBack() {
        // Tutup modal
        ((javafx.stage.Stage) saveButton.getScene().getWindow()).close();
    }

    private void showError(String msg) {
        errorLabel.setText(msg);
        errorLabel.setVisible(true);
    }
} 