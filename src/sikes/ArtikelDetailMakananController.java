package sikes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ArtikelDetailMakananController {
    @FXML
    private Button backButton;

    @FXML
    private Label userNameLabel;

    @FXML private ImageView imgTerkaitPenyebab;
    @FXML private Label descTerkaitPenyebab;
    @FXML private ImageView imgTerkaitObat;
    @FXML private Label descTerkaitObat;

    @FXML
    private void handleBack() {
        Main.showArtikelPage();
    }

    @FXML
    public void initialize() {
        if (userNameLabel != null) {
            userNameLabel.setText(Main.getCurrentUsername());
        }
        // Event klik artikel terkait
        if (imgTerkaitPenyebab != null) {
            imgTerkaitPenyebab.setOnMouseClicked(e -> handleBacaSelengkapnyaPenyebab());
        }
        if (descTerkaitPenyebab != null) {
            descTerkaitPenyebab.setOnMouseClicked(e -> handleBacaSelengkapnyaPenyebab());
        }
        if (imgTerkaitObat != null) {
            imgTerkaitObat.setOnMouseClicked(e -> handleBacaSelengkapnyaObat());
        }
        if (descTerkaitObat != null) {
            descTerkaitObat.setOnMouseClicked(e -> handleBacaSelengkapnyaObat());
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
    private void handleKonsultasi() {
        try {
            Main.showKonsultasiPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePengingat() {
        try {
            Main.showPengingatPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleLogout() {
        Main.showLoginPage();
    }
    
    @FXML
    private void handleBacaSelengkapnyaPenyebab() {
        Main.showArtikelDetailPage();
    }
    
    @FXML
    private void handleBacaSelengkapnyaObat() {
        Main.showArtikelDetailObatPage();
    }
} 