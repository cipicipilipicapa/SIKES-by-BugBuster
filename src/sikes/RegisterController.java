package sikes;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class RegisterController {
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private TextField emailField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private PasswordField confirmPasswordField;
    
    @FXML
    private Button signInButton;
    
    @FXML
    private Label errorLabel;
    
    @FXML
    private Button alreadyAccountButton;
    
    @FXML
    private DatePicker tanggalLahirPicker;
    @FXML
    private RadioButton golA;
    @FXML
    private RadioButton golB;
    @FXML
    private RadioButton golAB;
    @FXML
    private RadioButton golO;
    @FXML
    private ToggleGroup golonganDarahGroup;
    
    @FXML
    private TextField phoneField;
    
    @FXML
    private VBox formContainer;
    
    @FXML
    private void initialize() {
        // Pastikan ToggleGroup diinisialisasi manual agar hanya satu RadioButton bisa dipilih
        golonganDarahGroup = new ToggleGroup();
        golA.setToggleGroup(golonganDarahGroup);
        golB.setToggleGroup(golonganDarahGroup);
        golAB.setToggleGroup(golonganDarahGroup);
        golO.setToggleGroup(golonganDarahGroup);
    }
    

    
    @FXML
    private void handleSignIn() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String tanggalLahir = tanggalLahirPicker.getValue() != null ? tanggalLahirPicker.getValue().toString() : "";
        String golonganDarah = "";
        if (golA.isSelected()) golonganDarah = "A";
        else if (golB.isSelected()) golonganDarah = "B";
        else if (golAB.isSelected()) golonganDarah = "AB";
        else if (golO.isSelected()) golonganDarah = "O";
        
        // Validasi semua field
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ||
            phone.isEmpty() || tanggalLahir.isEmpty() || golonganDarah.isEmpty()) {
            showError("Semua field harus diisi!");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            showError("Password tidak cocok!");
            return;
        }
        
        if (!isValidEmail(email)) {
            showError("Email tidak valid!");
            return;
        }
        
        // Check if username exists with case-insensitive search
        String exactUsername = Main.UserUtil.getExactUsername(username);
        if (exactUsername != null) {
            if (username.equals(exactUsername)) {
                showError("Username sudah terdaftar!");
            } else {
                showError("Username tidak valid!");
            }
            return;
        }
        
        // Cek apakah email sudah ada
        if (Main.UserUtil.isUserExists("", email)) {
            showError("Email sudah terdaftar!");
            return;
        }
        
        // Simpan user baru dengan tanggal lahir, golongan darah, dan nomor telepon
        boolean success = Main.UserUtil.registerUser(username, email, password, phone, tanggalLahir, golonganDarah);
        if (!success) {
            showError("Gagal menyimpan user!");
            return;
        }
        
        // Tampilkan konfirmasi registrasi berhasil
        showSuccessAlert("Registrasi Berhasil", "Akun Anda telah berhasil dibuat! Silakan login dengan username dan password yang telah Anda daftarkan.");
        
        Main.showLoginPage();
    }
    
    @FXML
    private void handleAlreadyAccount() {
        Main.showLoginPage();
    }
    
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
    
    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }
    
    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}