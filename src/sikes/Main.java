package sikes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

public class Main extends Application {
    
    private static Stage primaryStage;
    private static String currentUsername = "";
    
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("Sikes - Health Management System");
        
        // Initialize ReminderManager but don't start notifications yet
        ReminderManager.initializeWithoutScheduler();
        
        // Start with Login page
        showLoginPage();
        
        primaryStage.show();
    }
    
    public static void showRegisterPage() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("register.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1000, 700);
            scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
            primaryStage.setTitle("Sikes - Register");
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void showLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1000, 700);
            scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
            primaryStage.setTitle("Sikes - Login");
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void showDashboardPage() {
        try {
            // DO NOT start reminder notifications here
            // ReminderManager.startNotifications();
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("dashboard.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1400, 800);
            scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
            primaryStage.setTitle("Sikes - Dashboard");
            primaryStage.setScene(scene);
            
            DashboardController controller = loader.getController();
            if (controller != null) {
                controller.setUsername(currentUsername);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void showArtikelPage() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("artikel.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1400, 800);
            scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
            primaryStage.setTitle("Sikes - Artikel");
            primaryStage.setScene(scene);
            
            ArtikelController controller = loader.getController();
            if (controller != null) {
                controller.setUsername(currentUsername);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void showInputDataPage() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("inputdata.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1400, 800);
            scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
            primaryStage.setTitle("Sikes - Input Data");
            primaryStage.setScene(scene);
            
            InputDataController controller = loader.getController();
            if (controller != null) {
                controller.setUsername(currentUsername);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showDataPage() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("data.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1400, 800);
            scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
            primaryStage.setTitle("Sikes - Data");
            primaryStage.setScene(scene);
            
            DataController controller = loader.getController();
            if (controller != null) {
                controller.setUsername(currentUsername);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showKonsultasiPage() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("konsultasi.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1400, 800);
            scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
            primaryStage.setTitle("Sikes - Konsultasi");
            primaryStage.setScene(scene);
            
            KonsultasiController controller = loader.getController();
            if (controller != null) {
                controller.setUsername(currentUsername);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Tidak dapat memuat halaman konsultasi");
        }
    }

    public static void showPengingatPage() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("pengingat.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1400, 800);
            scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
            primaryStage.setTitle("Sikes - Pengingat");
            primaryStage.setScene(scene);
            
            PengingatController controller = loader.getController();
            if (controller != null) {
                controller.setUsername(currentUsername);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Tidak dapat memuat halaman pengingat");
        }
    }

    public static void showArtikelDetailPage() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("artikel_detail.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1400, 800);
            scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
            primaryStage.setTitle("Sikes - Detail Artikel");
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showArtikelDetailMakananPage() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("artikel_detail_makanan.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1400, 800);
            scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
            primaryStage.setTitle("Sikes - Detail Artikel Makanan");
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showArtikelDetailObatPage() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("artikel_detail_obat.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1400, 800);
            scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
            primaryStage.setTitle("Sikes - Detail Artikel Obat");
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showEditProfilePage() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("edit_profile.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 500, 600);
            scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Profil");
            dialogStage.setScene(scene);
            dialogStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void showEditPasswordPage() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("edit_password.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 500, 600);
            scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ubah Password & Gmail");
            dialogStage.setScene(scene);
            dialogStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showProfileMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("profile_menu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 350, 350);
            scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Profil");
            dialogStage.setScene(scene);
            dialogStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method untuk menampilkan alert dari Main class
    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void setCurrentUsername(String username) {
        currentUsername = username;
    }
    
    public static String getCurrentUsername() {
        return currentUsername;
    }
    
    // Utility class for user management
    public static class RiwayatData {
        public String tanggal, usia, tinggi, berat, golongan, gula, tekanan;
        public RiwayatData(String tanggal, String usia, String tinggi, String berat, String golongan, String gula, String tekanan) {
            this.tanggal = tanggal;
            this.usia = usia;
            this.tinggi = tinggi;
            this.berat = berat;
            this.golongan = golongan;
            this.gula = gula;
            this.tekanan = tekanan;
        }
        public RiwayatData(String tanggal, String tinggi, String berat, String gula, String tekanan) {
            this.tanggal = tanggal;
            this.usia = "";
            this.tinggi = tinggi;
            this.berat = berat;
            this.golongan = "";
            this.gula = gula;
            this.tekanan = tekanan;
        }
    }

    public static class UserUtil {
        private static final String USER_FILE = "users.xml";
        private static final String RIWAYAT_FILE = "riwayat.xml";

        public static boolean registerUser(String username, String email, String password) {
            return registerUser(username, email, password, "");
        }
        public static boolean registerUser(String username, String email, String password, String phone) {
            if (isUserExists(username, email)) return false;
            try {
                File xmlFile = new File(USER_FILE);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc;
                Element root;
                if (!xmlFile.exists()) {
                    doc = dBuilder.newDocument();
                    root = doc.createElement("users");
                    doc.appendChild(root);
                } else {
                    doc = dBuilder.parse(xmlFile);
                    root = (Element) doc.getElementsByTagName("users").item(0);
                }
                Element userElem = doc.createElement("user");
                Element usernameElem = doc.createElement("username");
                usernameElem.setTextContent(username);
                Element emailElem = doc.createElement("email");
                emailElem.setTextContent(email);
                Element passwordElem = doc.createElement("password");
                passwordElem.setTextContent(password);
                Element phoneElem = doc.createElement("phone");
                phoneElem.setTextContent(phone);
                userElem.appendChild(usernameElem);
                userElem.appendChild(passwordElem);
                userElem.appendChild(emailElem);
                userElem.appendChild(phoneElem);
                root.appendChild(userElem);
                // Save XML
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer transformer = tf.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(xmlFile);
                transformer.transform(source, result);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public static boolean registerUser(String username, String email, String password, String phone, String tanggalLahir, String golonganDarah) {
            if (isUserExists(username, email)) return false;
            try {
                File xmlFile = new File(USER_FILE);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc;
                Element root;
                if (!xmlFile.exists()) {
                    doc = dBuilder.newDocument();
                    root = doc.createElement("users");
                    doc.appendChild(root);
                } else {
                    doc = dBuilder.parse(xmlFile);
                    root = (Element) doc.getElementsByTagName("users").item(0);
                }
                Element userElem = doc.createElement("user");
                Element usernameElem = doc.createElement("username");
                usernameElem.setTextContent(username);
                Element emailElem = doc.createElement("email");
                emailElem.setTextContent(email);
                Element passwordElem = doc.createElement("password");
                passwordElem.setTextContent(password);
                Element phoneElem = doc.createElement("phone");
                phoneElem.setTextContent(phone);
                Element tglLahirElem = doc.createElement("tanggalLahir");
                tglLahirElem.setTextContent(tanggalLahir);
                Element goldarElem = doc.createElement("golonganDarah");
                goldarElem.setTextContent(golonganDarah);
                userElem.appendChild(usernameElem);
                userElem.appendChild(passwordElem);
                userElem.appendChild(emailElem);
                userElem.appendChild(phoneElem);
                userElem.appendChild(tglLahirElem);
                userElem.appendChild(goldarElem);
                root.appendChild(userElem);
                // Save XML
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer transformer = tf.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(xmlFile);
                transformer.transform(source, result);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public static boolean isUserExists(String username, String email) {
            try {
                File xmlFile = new File(USER_FILE);
                if (!xmlFile.exists()) return false;
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlFile);
                NodeList users = doc.getElementsByTagName("user");
                for (int i = 0; i < users.getLength(); i++) {
                    Element userElem = (Element) users.item(i);
                    String uname = userElem.getElementsByTagName("username").item(0).getTextContent();
                    String emailVal = userElem.getElementsByTagName("email").item(0).getTextContent();
                    if (uname.equalsIgnoreCase(username) || emailVal.equalsIgnoreCase(email)) {
                        return true;
                    }
                }
            } catch (Exception e) {}
            return false;
        }

        public static String getExactUsername(String username) {
            try {
                File xmlFile = new File(USER_FILE);
                if (!xmlFile.exists()) return null;
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlFile);
                NodeList users = doc.getElementsByTagName("user");
                for (int i = 0; i < users.getLength(); i++) {
                    Element userElem = (Element) users.item(i);
                    String uname = userElem.getElementsByTagName("username").item(0).getTextContent();
                    if (uname.equalsIgnoreCase(username)) {
                        return uname; // Return the exact username as stored
                    }
                }
            } catch (Exception e) {}
            return null;
        }

        public static boolean validateLogin(String username, String email, String password) {
            try {
                File xmlFile = new File(USER_FILE);
                if (!xmlFile.exists()) return false;
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlFile);
                NodeList users = doc.getElementsByTagName("user");
                for (int i = 0; i < users.getLength(); i++) {
                    Element userElem = (Element) users.item(i);
                    String uname = userElem.getElementsByTagName("username").item(0).getTextContent();
                    String emailVal = userElem.getElementsByTagName("email").item(0).getTextContent();
                    String passVal = userElem.getElementsByTagName("password").item(0).getTextContent();
                    if (uname.equalsIgnoreCase(username) && emailVal.equalsIgnoreCase(email) && passVal.equals(password)) {
                        return true;
                    }
                }
            } catch (Exception e) {}
            return false;
        }

        public static User getUserByUsername(String username) {
            try {
                File xmlFile = new File(USER_FILE);
                if (!xmlFile.exists()) return null;
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlFile);
                NodeList users = doc.getElementsByTagName("user");
                for (int i = 0; i < users.getLength(); i++) {
                    Element userElem = (Element) users.item(i);
                    String uname = userElem.getElementsByTagName("username").item(0).getTextContent();
                    String email = userElem.getElementsByTagName("email").item(0).getTextContent();
                    String password = userElem.getElementsByTagName("password").item(0).getTextContent();
                    String phone = userElem.getElementsByTagName("phone").item(0).getTextContent();
                    if (uname.equalsIgnoreCase(username)) {
                        return new User(uname, email, password, phone);
                    }
                }
            } catch (Exception e) {}
            return null;
        }

        public static boolean updateUser(String oldUsername, User newUser) {
            File xmlFile = new File(USER_FILE);
            boolean updated = false;
            try {
                if (!xmlFile.exists()) return false;
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlFile);
                NodeList users = doc.getElementsByTagName("user");
                for (int i = 0; i < users.getLength(); i++) {
                    Element userElem = (Element) users.item(i);
                    String uname = userElem.getElementsByTagName("username").item(0).getTextContent();
                    if (uname.equalsIgnoreCase(oldUsername)) {
                        userElem.getElementsByTagName("username").item(0).setTextContent(newUser.username);
                        userElem.getElementsByTagName("email").item(0).setTextContent(newUser.email);
                        userElem.getElementsByTagName("password").item(0).setTextContent(newUser.password);
                        userElem.getElementsByTagName("phone").item(0).setTextContent(newUser.phone);
                        updated = true;
                        break;
                    }
                }
                if (updated) {
                    TransformerFactory tf = TransformerFactory.newInstance();
                    Transformer transformer = tf.newTransformer();
                    DOMSource source = new DOMSource(doc);
                    StreamResult result = new StreamResult(xmlFile);
                    transformer.transform(source, result);
                }
            } catch (Exception e) { return false; }
            return updated;
        }

        public static boolean saveRiwayat(String username, java.util.List<RiwayatData> riwayatList) {
            try {
                File xmlFile = new File(RIWAYAT_FILE);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc;
                Element root;
                if (!xmlFile.exists()) {
                    doc = dBuilder.newDocument();
                    root = doc.createElement("riwayats");
                    doc.appendChild(root);
                } else {
                    doc = dBuilder.parse(xmlFile);
                    root = (Element) doc.getElementsByTagName("riwayats").item(0);
                }
                // Cari user node, jika ada hapus dulu
                NodeList userNodes = root.getElementsByTagName("user");
                for (int i = 0; i < userNodes.getLength(); i++) {
                    Element userElem = (Element) userNodes.item(i);
                    if (userElem.getAttribute("username").equalsIgnoreCase(username)) {
                        root.removeChild(userElem);
                        break;
                    }
                }
                // Buat user node baru
                Element userElem = doc.createElement("user");
                userElem.setAttribute("username", username);
                for (RiwayatData data : riwayatList) {
                    Element riwayatElem = doc.createElement("riwayat");
                    riwayatElem.setAttribute("tanggal", data.tanggal);
                    riwayatElem.setAttribute("usia", data.usia);
                    riwayatElem.setAttribute("tinggi", data.tinggi);
                    riwayatElem.setAttribute("berat", data.berat);
                    riwayatElem.setAttribute("golongan", data.golongan);
                    riwayatElem.setAttribute("gula", data.gula);
                    riwayatElem.setAttribute("tekanan", data.tekanan);
                    userElem.appendChild(riwayatElem);
                }
                root.appendChild(userElem);
                // Simpan XML
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer transformer = tf.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(xmlFile);
                transformer.transform(source, result);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public static java.util.List<RiwayatData> loadRiwayat(String username) {
            java.util.List<RiwayatData> list = new java.util.ArrayList<>();
            try {
                File xmlFile = new File(RIWAYAT_FILE);
                if (!xmlFile.exists()) return list;
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlFile);
                NodeList userNodes = doc.getElementsByTagName("user");
                for (int i = 0; i < userNodes.getLength(); i++) {
                    Element userElem = (Element) userNodes.item(i);
                    if (userElem.getAttribute("username").equalsIgnoreCase(username)) {
                        NodeList riwayats = userElem.getElementsByTagName("riwayat");
                        for (int j = 0; j < riwayats.getLength(); j++) {
                            Element r = (Element) riwayats.item(j);
                            list.add(new RiwayatData(
                                r.getAttribute("tanggal"),
                                r.getAttribute("usia"),
                                r.getAttribute("tinggi"),
                                r.getAttribute("berat"),
                                r.getAttribute("golongan"),
                                r.getAttribute("gula"),
                                r.getAttribute("tekanan")
                            ));
                        }
                        break;
                    }
                }
            } catch (Exception e) {}
            return list;
        }

        public static class User {
            public String username, email, password, phone;
            public User(String username, String email, String password, String phone) {
                this.username = username;
                this.email = email;
                this.password = password;
                this.phone = phone;
            }
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void stop() throws Exception {
        ReminderManager.shutdown();
        super.stop();
    }
}