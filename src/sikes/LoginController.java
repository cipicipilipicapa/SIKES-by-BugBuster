package sikes;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Button signInButton;
    
    @FXML
    private Label errorLabel;
    
    @FXML
    private Button forgotPasswordButton;
    
    @FXML
    private Button createAccountButton;
    
    // Valid credentials
    private static final String VALID_USERNAME = "AhmadLuthfan";
    private static final String VALID_EMAIL = "luthfan744@gmail.com";
    private static final String VALID_PASSWORD = "Luthfan123654";
    
    @FXML
    private void handleSignIn() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            showError("Semua field harus diisi!");
            return;
        }
        
        // Check if username exists with case-insensitive search
        String exactUsername = Main.UserUtil.getExactUsername(username);
        if (exactUsername == null) {
            showError("Username tidak valid!");
            return;
        }
        
        // Check if the username case matches exactly
        if (!username.equals(exactUsername)) {
            showError("Username tidak valid!");
            return;
        }
        
        // Get user data to check password
        Main.UserUtil.User user = Main.UserUtil.getUserByUsername(username);
        if (user == null) {
            showError("Username tidak valid!");
            return;
        }
        
        // Check password
        if (!password.equals(user.password)) {
            showError("Password salah!");
            return;
        }
        
        // Login successful, set username and go to dashboard
        Main.setCurrentUsername(username);
        
        // Tampilkan konfirmasi login berhasil
        showSuccessAlert("Login Berhasil", "Klik OK Untuk melanjutkan " + username + "!");
        
        Main.showDashboardPage();
    }
    
    @FXML
    private void handleForgotPassword() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Lupa Password");
        alert.setHeaderText(null);
        alert.setContentText("Fitur lupa password belum tersedia. Silakan hubungi administrator.");
        alert.showAndWait();
    }
    
    @FXML
    private void handleCreateAccount() {
        Main.showRegisterPage();
    }
    
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        showAlert("Peringatan", message);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}