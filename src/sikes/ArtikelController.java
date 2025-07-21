package sikes;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.List;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class ArtikelController {
    
    @FXML
    private Button berandaButton;
    
    @FXML
    private Button inputDataButton;
    
    @FXML
    private Button dataButton;
    
    @FXML
    private Button konsultasiButton;
    
    @FXML
    private Button logoutButton;
    
    @FXML
    private Label userNameLabel;
    
    @FXML
    private TextField searchField;
    
    @FXML
    private Button notificationButton;
    
    @FXML
    private HBox artikelItem1;
    
    @FXML
    private HBox artikelItem2;
    
    @FXML
    private HBox artikelItem3;
    
    @FXML
    private VBox emptyState;
    
    @FXML
    private VBox artikelContainer;
    
    @FXML
    private TreeView<String> kategoriTree;
    
    private ArtikelManager artikelManager;
    
    public void setUsername(String username) {
        if (userNameLabel != null) {
            userNameLabel.setText(username);
        }
    }
    
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
            // Menggunakan Alert langsung tanpa method showAlert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Tidak dapat membuka halaman konsultasi");
            alert.showAndWait();
        }
    }

    @FXML
    private void handlePengingat() {
        try {
            Main.showPengingatPage();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Tidak dapat membuka halaman pengingat");
            alert.showAndWait();
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
    private void handleBacaSelengkapnyaPenyebab() {
        Main.showArtikelDetailPage();
    }

    @FXML
    private void handleBacaSelengkapnyaMakanan() {
        Main.showArtikelDetailMakananPage();
    }

    @FXML
    private void handleBacaSelengkapnyaObat() {
        Main.showArtikelDetailObatPage();
    }

    @FXML
    private void handleEditProfile() {
        Main.showProfileMenu();
    }

    @FXML
    public void initialize() {
        setUsername(Main.getCurrentUsername());
        
        // Inisialisasi ArtikelManager
        artikelManager = ArtikelManager.getInstance();
        
        // Setup search functionality
        if (searchField != null) {
            searchField.textProperty().addListener((obs, oldVal, newVal) -> {
                performSearch(newVal);
            });
        }
        // Tambahkan event handler klik pada kotak artikel
        if (artikelItem1 != null) {
            artikelItem1.setOnMouseClicked(e -> handleBacaSelengkapnyaPenyebab());
        }
        if (artikelItem2 != null) {
            artikelItem2.setOnMouseClicked(e -> handleBacaSelengkapnyaMakanan());
        }
        if (artikelItem3 != null) {
            artikelItem3.setOnMouseClicked(e -> handleBacaSelengkapnyaObat());
        }
        // Setup kategori tree
        if (kategoriTree != null) {
            TreeItem<String> root = new TreeItem<>("Kategori Artikel");
            root.setExpanded(true);
            TreeItem<String> kesehatan = new TreeItem<>("Kesehatan");
            TreeItem<String> diabetes = new TreeItem<>("Diabetes");
            TreeItem<String> penyebab = new TreeItem<>("Penyebab");
            diabetes.getChildren().add(penyebab);
            TreeItem<String> makanan = new TreeItem<>("Makanan");
            TreeItem<String> aman = new TreeItem<>("Aman");
            TreeItem<String> berbahaya = new TreeItem<>("Berbahaya");
            makanan.getChildren().addAll(aman, berbahaya);
            TreeItem<String> obat = new TreeItem<>("Obat");
            TreeItem<String> resep = new TreeItem<>("Resep");
            TreeItem<String> bebas = new TreeItem<>("Bebas");
            obat.getChildren().addAll(resep, bebas);
            root.getChildren().addAll(kesehatan, diabetes, makanan, obat);
            kategoriTree.setRoot(root);
            kategoriTree.setShowRoot(false);
            kategoriTree.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null && newVal.isLeaf()) {
                    String kategori = null;
                    switch (newVal.getValue()) {
                        case "Penyebab": kategori = "Penyakit"; break;
                        case "Aman": kategori = "Nutrisi"; break;
                        case "Berbahaya": kategori = "Nutrisi"; break;
                        case "Resep": kategori = "Pengobatan"; break;
                        case "Bebas": kategori = "Pengobatan"; break;
                        default: kategori = newVal.getValue(); break;
                    }
                    List<Artikel> hasil = artikelManager.getArtikelByKategori(kategori);
                    if (artikelContainer != null) {
                        artikelContainer.getChildren().clear();
                        if (hasil.isEmpty()) {
                            if (emptyState != null) {
                                artikelContainer.getChildren().add(emptyState);
                                emptyState.setVisible(true);
                            }
                        } else {
                            for (Artikel artikel : hasil) {
                                if (artikel.getJudul().equalsIgnoreCase("Penyebab Diabetes") && artikelItem1 != null) {
                                    artikelContainer.getChildren().add(artikelItem1);
                                } else if (artikel.getJudul().equalsIgnoreCase("Makanan untuk Penderita Diabetes") && artikelItem2 != null) {
                                    artikelContainer.getChildren().add(artikelItem2);
                                } else if (artikel.getJudul().equalsIgnoreCase("5 Obat Diabetes di Apotek") && artikelItem3 != null) {
                                    artikelContainer.getChildren().add(artikelItem3);
                                }
                            }
                            if (emptyState != null) emptyState.setVisible(false);
                        }
                    }
                }
            });
        }
    }
    
    private void performSearch(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            // Tampilkan semua artikel
            showAllArticles();
        } else {
            // Cari artikel berdasarkan keyword
            List<Artikel> hasilPencarian = artikelManager.searchArtikel(keyword.trim());
            
            // Sort hasil pencarian berdasarkan relevansi (exact match first, then partial match)
            hasilPencarian.sort((a1, a2) -> {
                String judul1 = a1.getJudul().toLowerCase();
                String judul2 = a2.getJudul().toLowerCase();
                String keywordLower = keyword.trim().toLowerCase();
                
                boolean exactMatch1 = judul1.equals(keywordLower);
                boolean exactMatch2 = judul2.equals(keywordLower);
                
                if (exactMatch1 && !exactMatch2) return -1;
                if (!exactMatch1 && exactMatch2) return 1;
                
                // Jika keduanya exact match atau tidak, urutkan berdasarkan posisi keyword
                int pos1 = judul1.indexOf(keywordLower);
                int pos2 = judul2.indexOf(keywordLower);
                
                if (pos1 != pos2) {
                    return Integer.compare(pos1, pos2);
                }
                
                // Jika posisi sama, urutkan berdasarkan panjang judul (yang lebih pendek di atas)
                return Integer.compare(judul1.length(), judul2.length());
            });
            
            filterArticles(hasilPencarian);
        }
    }
    
    private void showAllArticles() {
        if (artikelContainer == null) return;
        
        // Clear container
        artikelContainer.getChildren().clear();
        
        // Add all articles in original order
        if (artikelItem1 != null) artikelContainer.getChildren().add(artikelItem1);
        if (artikelItem2 != null) artikelContainer.getChildren().add(artikelItem2);
        if (artikelItem3 != null) artikelContainer.getChildren().add(artikelItem3);
        
        // Hide empty state
        if (emptyState != null) {
            emptyState.setVisible(false);
        }
    }
    
    private void filterArticles(List<Artikel> hasilPencarian) {
        if (artikelContainer == null) return;
        
        // Clear container
        artikelContainer.getChildren().clear();
        
        // Hide empty state initially
        if (emptyState != null) {
            emptyState.setVisible(false);
        }
        
        // Add articles based on search results
        for (Artikel artikel : hasilPencarian) {
            if (artikel.getJudul().equalsIgnoreCase("Penyebab Diabetes")) {
                if (artikelItem1 != null) {
                    artikelContainer.getChildren().add(artikelItem1);
                }
            } else if (artikel.getJudul().equalsIgnoreCase("Makanan untuk Penderita Diabetes")) {
                if (artikelItem2 != null) {
                    artikelContainer.getChildren().add(artikelItem2);
                }
            } else if (artikel.getJudul().equalsIgnoreCase("5 Obat Diabetes di Apotek")) {
                if (artikelItem3 != null) {
                    artikelContainer.getChildren().add(artikelItem3);
                }
            }
        }
        
        // Show empty state if no results
        if (hasilPencarian.isEmpty()) {
            if (emptyState != null) {
                artikelContainer.getChildren().add(emptyState);
                emptyState.setVisible(true);
            }
        }
    }
    
    private boolean containsArtikel(List<Artikel> artikelList, String title) {
        return artikelList.stream().anyMatch(artikel -> artikel.getJudul().equalsIgnoreCase(title));
    }
    
    @FXML
    private void handleSearch() {
        if (searchField != null) {
            performSearch(searchField.getText());
        }
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