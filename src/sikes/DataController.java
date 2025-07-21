package sikes;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.List;

public class DataController {
    
    @FXML private PieChart gulaChart;
    @FXML private LineChart<String, Number> tekananChart;
    @FXML private LineChart<String, Number> gulaLineChart;
    @FXML private ListView<String> gulaHistoryList;
    
    // Personal info labels
    @FXML private Label userNameLabel;
    @FXML private Label nameLabel;
    @FXML private Label usiaValue;
    @FXML private Label tinggiValue;
    @FXML private Label beratValue;
    @FXML private Label golonganDarahValue;
    
    // Gula darah labels
    @FXML private Label gulaPercentage;
    @FXML private Label gulaRange;
    
    // Tekanan darah label
    @FXML private Label tekananValue;
    
    // Navigation buttons
    @FXML private Button berandaButton;
    @FXML private Button inputDataButton;
    @FXML private Button artikelButton;
    @FXML private Button konsultasiButton;
    @FXML private Button logoutButton;
    @FXML private Button notificationButton;
    
    private Timeline timeline;
    private Random random = new Random();
    
    public void initialize() {
        setupPersonalInfo();
        setupCharts();
        //startDataAnimation(); // Hapus animasi random
        tampilkanDataInput();
    }
    
    public void setUsername(String username) {
        if (userNameLabel != null) {
            userNameLabel.setText(username);
        }
        if (nameLabel != null) {
            nameLabel.setText(username);
        }
        tampilkanDataInput();
    }
    
    private void setupPersonalInfo() {
        // Set greeting menjadi 'Hello'
        if (userNameLabel != null) {
            javafx.scene.Parent parent = userNameLabel.getParent();
            if (parent instanceof javafx.scene.layout.VBox) {
                for (javafx.scene.Node node : ((javafx.scene.layout.VBox)parent).getChildren()) {
                    if (node instanceof javafx.scene.control.Label && "greeting-label".equals(node.getStyleClass().contains("greeting-label") ? "greeting-label" : null)) {
                        ((javafx.scene.control.Label)node).setText("Hello");
                    }
                }
            }
        }
        // Ambil data user dari users.xml
        Main.UserUtil.User user = Main.UserUtil.getUserByUsername(Main.getCurrentUsername());
        if (user != null) {
            // Hitung usia dari tanggal lahir
            String tglLahir = "";
            String goldar = "";
            try {
                // Baca users.xml manual untuk ambil tanggalLahir dan golonganDarah
                java.io.File xmlFile = new java.io.File("users.xml");
                javax.xml.parsers.DocumentBuilderFactory dbFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
                javax.xml.parsers.DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                org.w3c.dom.Document doc = dBuilder.parse(xmlFile);
                org.w3c.dom.NodeList users = doc.getElementsByTagName("user");
                for (int i = 0; i < users.getLength(); i++) {
                    org.w3c.dom.Element userElem = (org.w3c.dom.Element) users.item(i);
                    String uname = userElem.getElementsByTagName("username").item(0).getTextContent();
                    if (uname.equalsIgnoreCase(user.username)) {
                        tglLahir = userElem.getElementsByTagName("tanggalLahir").item(0) != null ? userElem.getElementsByTagName("tanggalLahir").item(0).getTextContent() : "";
                        goldar = userElem.getElementsByTagName("golonganDarah").item(0) != null ? userElem.getElementsByTagName("golonganDarah").item(0).getTextContent() : "";
                        break;
                    }
                }
            } catch (Exception e) {}
            // Hitung usia
            int usia = 0;
            if (!tglLahir.isEmpty()) {
                try {
                    java.time.LocalDate birth = java.time.LocalDate.parse(tglLahir);
                    java.time.LocalDate now = java.time.LocalDate.now();
                    usia = java.time.Period.between(birth, now).getYears();
                } catch(Exception e) {}
            }
            usiaValue.setText(usia > 0 ? String.valueOf(usia) : "-");
            golonganDarahValue.setText(goldar.isEmpty() ? "-" : goldar);
        } else {
            usiaValue.setText("-");
            golonganDarahValue.setText("-");
        }
        tinggiValue.setText("178");
        beratValue.setText("64");
    }
    
    private void setupCharts() {
        // Setup Kadar Gula PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Normal", 70),
            new PieChart.Data("Pra-Diabetes", 20),
            new PieChart.Data("Diabetes", 10)
        );
        gulaChart.setData(pieChartData);
        
        // Setup Tekanan Darah LineChart with two series (systolic and diastolic)
        XYChart.Series<String, Number> systolicSeries = new XYChart.Series<>();
        systolicSeries.setName("Sistolik");
        XYChart.Series<String, Number> diastolicSeries = new XYChart.Series<>();
        diastolicSeries.setName("Diastolik");
        tekananChart.getData().addAll(systolicSeries, diastolicSeries);
    }
    
    private void startDataAnimation() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            updateChartsWithRandomData();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    private void updateChartsWithRandomData() {
        // Update Kadar Gula distribution
        double normal = 60 + random.nextInt(30);
        double preDiabetes = 10 + random.nextInt(20);
        double diabetes = 5 + random.nextInt(15);
        
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Normal", normal),
            new PieChart.Data("Pra-Diabetes", preDiabetes),
            new PieChart.Data("Diabetes", diabetes)
        );
        gulaChart.setData(pieChartData);
        
        // Update Gula details
        double percentage = (random.nextDouble() * 5); // 0-5%
        int lower = 100 + random.nextInt(60); // 100-160
        int upper = lower + 20 + random.nextInt(20); // +20-40
        
        gulaPercentage.setText(String.format("%.1f%%", percentage));
        gulaRange.setText(String.format("%d/%d", lower, upper));
        
        // Update Tekanan Darah data (7 days)
        ObservableList<XYChart.Data<String, Number>> systolicData = FXCollections.observableArrayList();
        ObservableList<XYChart.Data<String, Number>> diastolicData = FXCollections.observableArrayList();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
        
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            int systolic = 110 + random.nextInt(30); // Random between 110-140
            int diastolic = 70 + random.nextInt(20); // Random between 70-90
            systolicData.add(new XYChart.Data<>(date.format(formatter), systolic));
            diastolicData.add(new XYChart.Data<>(date.format(formatter), diastolic));
        }
        
        tekananChart.getData().get(0).setData(systolicData);
        tekananChart.getData().get(1).setData(diastolicData);
        
        // Update current tekanan darah value
        int currentSystolic = 110 + random.nextInt(30);
        int currentDiastolic = 70 + random.nextInt(20);
        tekananValue.setText(String.format("%d/%d", currentDiastolic, currentSystolic));
    }
    
    private void tampilkanDataInput() {
        java.util.List<Main.RiwayatData> riwayatList = Main.UserUtil.loadRiwayat(Main.getCurrentUsername());
        if (riwayatList.isEmpty()) {
            // Ambil data user dari users.xml untuk usia dan golongan darah
            Main.UserUtil.User user = Main.UserUtil.getUserByUsername(Main.getCurrentUsername());
            String tglLahir = "";
            String goldar = "";
            try {
                java.io.File xmlFile = new java.io.File("users.xml");
                javax.xml.parsers.DocumentBuilderFactory dbFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
                javax.xml.parsers.DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                org.w3c.dom.Document doc = dBuilder.parse(xmlFile);
                org.w3c.dom.NodeList users = doc.getElementsByTagName("user");
                for (int i = 0; i < users.getLength(); i++) {
                    org.w3c.dom.Element userElem = (org.w3c.dom.Element) users.item(i);
                    String uname = userElem.getElementsByTagName("username").item(0).getTextContent();
                    if (uname.equalsIgnoreCase(user.username)) {
                        tglLahir = userElem.getElementsByTagName("tanggalLahir").item(0) != null ? userElem.getElementsByTagName("tanggalLahir").item(0).getTextContent() : "";
                        goldar = userElem.getElementsByTagName("golonganDarah").item(0) != null ? userElem.getElementsByTagName("golonganDarah").item(0).getTextContent() : "";
                        break;
                    }
                }
            } catch (Exception e) {}
            // Hitung usia
            int usia = 0;
            if (!tglLahir.isEmpty()) {
                try {
                    java.time.LocalDate birth = java.time.LocalDate.parse(tglLahir);
                    java.time.LocalDate now = java.time.LocalDate.now();
                    usia = java.time.Period.between(birth, now).getYears();
                } catch(Exception e) {}
            }
            usiaValue.setText(usia > 0 ? String.valueOf(usia) : "-");
            golonganDarahValue.setText(goldar.isEmpty() ? "-" : goldar);
            tinggiValue.setText("-");
            beratValue.setText("-");
            if (gulaChart != null) gulaChart.getData().clear();
            if (gulaLineChart != null) gulaLineChart.getData().clear();
            if (gulaHistoryList != null) gulaHistoryList.getItems().clear();
            gulaPercentage.setText("-");
            gulaRange.setText("-");
            tekananValue.setText("-");
            return;
        }
        // Ambil data terakhir untuk personal info
        Main.RiwayatData last = riwayatList.get(riwayatList.size() - 1);
        // Usia dan golongan darah sudah diambil dari users.xml di setupPersonalInfo
        tinggiValue.setText(last.tinggi);
        beratValue.setText(last.berat);
        // PieChart gula darah: distribusi seluruh data
        double normal = 0, prediabetes = 0, diabetes = 0;
        double total = 0;
        for (Main.RiwayatData data : riwayatList) {
            double gula = 0;
            try { gula = Double.parseDouble(data.gula); } catch(Exception e){}
            if (gula < 140) normal++;
            else if (gula < 200) prediabetes++;
            else diabetes++;
            total++;
        }
        javafx.collections.ObservableList<PieChart.Data> pieChartData = javafx.collections.FXCollections.observableArrayList(
            new PieChart.Data("Normal", normal),
            new PieChart.Data("Pra-Diabetes", prediabetes),
            new PieChart.Data("Diabetes", diabetes)
        );
        gulaChart.setData(pieChartData);
        // Tampilkan persentase gula terakhir
        double gulaTerakhir = 0;
        try { gulaTerakhir = Double.parseDouble(last.gula); } catch(Exception e){}
        gulaPercentage.setText(String.format("%.1f%%", gulaTerakhir));
        gulaRange.setText(last.gula + " mg/dl");
        // LineChart tekanan darah: tampilkan seluruh riwayat
        javafx.collections.ObservableList<XYChart.Data<String, Number>> systolicData = javafx.collections.FXCollections.observableArrayList();
        javafx.collections.ObservableList<XYChart.Data<String, Number>> diastolicData = javafx.collections.FXCollections.observableArrayList();
        for (Main.RiwayatData data : riwayatList) {
            String tanggal = data.tanggal;
            String[] tekanan = data.tekanan.split("/");
            int sistolik = 0, diastolik = 0;
            if (tekanan.length == 2) {
                try {
                    diastolik = Integer.parseInt(tekanan[0].replaceAll("[^0-9]", ""));
                    sistolik = Integer.parseInt(tekanan[1].replaceAll("[^0-9]", ""));
                } catch(Exception e){}
            }
            systolicData.add(new XYChart.Data<>(tanggal, sistolik));
            diastolicData.add(new XYChart.Data<>(tanggal, diastolik));
        }
        tekananChart.getData().clear();
        XYChart.Series<String, Number> systolicSeries = new XYChart.Series<>();
        systolicSeries.setName("Sistolik");
        systolicSeries.setData(systolicData);
        XYChart.Series<String, Number> diastolicSeries = new XYChart.Series<>();
        diastolicSeries.setName("Diastolik");
        diastolicSeries.setData(diastolicData);
        tekananChart.getData().addAll(systolicSeries, diastolicSeries);
        // Tampilkan tekanan darah terakhir
        tekananValue.setText(last.tekanan + " mmHg");
        // LineChart kadar gula darah: tren seluruh riwayat
        if (gulaLineChart != null) {
            gulaLineChart.getData().clear();
            XYChart.Series<String, Number> gulaSeries = new XYChart.Series<>();
            gulaSeries.setName("Kadar Gula Darah");
            for (Main.RiwayatData data : riwayatList) {
                double gula = 0;
                try { gula = Double.parseDouble(data.gula); } catch(Exception e){}
                gulaSeries.getData().add(new XYChart.Data<>(data.tanggal, gula));
            }
            gulaLineChart.getData().add(gulaSeries);
        }
        // Panel kecil riwayat gula darah
        if (gulaHistoryList != null) {
            gulaHistoryList.getItems().clear();
            for (Main.RiwayatData data : riwayatList) {
                double gula = 0;
                try { gula = Double.parseDouble(data.gula); } catch(Exception e){}
                String status;
                if (gula < 140) status = "Normal";
                else if (gula < 200) status = "Pra-Diabetes";
                else status = "Diabetes";
                String entry = String.format("%s: %.0f mg/dl (%s)", data.tanggal, gula, status);
                gulaHistoryList.getItems().add(entry);
            }
        }
    }
    
    // Navigation methods (same as before)
    @FXML
    private void handleBeranda() {
        Main.showDashboardPage();
    }
    
    @FXML
    private void handleInputData() {
        Main.showInputDataPage();
    }
    
    @FXML
    private void handleArtikel() {
        Main.showArtikelPage();
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
    private void handleEditProfile() {
        Main.showProfileMenu();
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