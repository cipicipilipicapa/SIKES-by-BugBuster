package sikes;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;

public class ProfileMenuController {
    @FXML private Label userNameLabel;

    @FXML
    public void initialize() {
        userNameLabel.setText(Main.getCurrentUsername());
    }

    @FXML
    private void handleEditPassword() {
        Main.showEditPasswordPage();
    }

    @FXML
    private void handleEditProfile() {
        Main.showEditProfilePage();
    }

    @FXML
    private void handleContact() {
        // Bisa diarahkan ke halaman/hubungi kami
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hubungi Kami");
        alert.setHeaderText("Butuh bantuan? Bicara dengan kami!");
        alert.setContentText("Email: application.support@gmail.com\nTelp: (021) 888888888");
        alert.showAndWait();
    }

    @FXML
    private void handleClose() {
        // Tutup pop-up/modal
        // Ambil window dari komponen apapun di scene (misal userNameLabel)
        Stage stage = (Stage) userNameLabel.getScene().getWindow();
        stage.close();
    }
} 