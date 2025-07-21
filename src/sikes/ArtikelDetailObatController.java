package sikes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ArtikelDetailObatController {
    @FXML
    private Button backButton;

    @FXML
    private Label userNameLabel;

    @FXML private ImageView imgTerkaitMakanan;
    @FXML private Label descTerkaitMakanan;
    @FXML private ImageView imgTerkaitPenyebab;
    @FXML private Label descTerkaitPenyebab;

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
        if (imgTerkaitMakanan != null) {
            imgTerkaitMakanan.setOnMouseClicked(e -> handleBacaSelengkapnyaMakanan());
        }
        if (descTerkaitMakanan != null) {
            descTerkaitMakanan.setOnMouseClicked(e -> handleBacaSelengkapnyaMakanan());
        }
        if (imgTerkaitPenyebab != null) {
            imgTerkaitPenyebab.setOnMouseClicked(e -> handleBacaSelengkapnyaPenyebab());
        }
        if (descTerkaitPenyebab != null) {
            descTerkaitPenyebab.setOnMouseClicked(e -> handleBacaSelengkapnyaPenyebab());
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
    private void handleBacaSelengkapnyaMakanan() {
        Main.showArtikelDetailMakananPage();
    }
    
    @FXML
    private void handleBacaSelengkapnyaPenyebab() {
        Main.showArtikelDetailPage();
    }
} 