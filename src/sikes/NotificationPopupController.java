package sikes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;

public class NotificationPopupController {
    
    @FXML
    private Button viewReplyButton1;
    
    @FXML
    private Button viewReplyButton2;
    
    @FXML
    private Button viewReplyButton3;
    
    @FXML
    private Button markAllReadButton;
    
    @FXML
    private Button viewAllButton;
    
    @FXML
    private VBox emptyState;
    
    @FXML
    private VBox notificationItem1;
    
    @FXML
    private VBox notificationItem2;
    
    @FXML
    private VBox notificationItem3;
    
    @FXML
    private VBox notificationList;

    // Model notifikasi
    public static class Notification {
        public String dokter;
        public String waktu;
        public String pesan;
        public Notification(String dokter, String waktu, String pesan) {
            this.dokter = dokter;
            this.waktu = waktu;
            this.pesan = pesan;
        }
    }
    public static ObservableList<Notification> notifications = FXCollections.observableArrayList();

    private static final String NOTIF_FILE = "notifications.xml";

    @FXML
    public void initialize() {
        loadNotifications();
        renderNotifications();
        notifications.addListener((javafx.collections.ListChangeListener<Notification>) c -> {
            renderNotifications();
            saveNotifications();
        });
    }

    public static void addNotification(String dokter, String waktu, String pesan) {
        notifications.add(new Notification(dokter, waktu, pesan));
    }

    private void saveNotifications() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(NOTIF_FILE))) {
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<notifications>");
            for (Notification notif : notifications) {
                writer.println("  <notification>");
                writer.println("    <dokter>" + escapeXml(notif.dokter) + "</dokter>");
                writer.println("    <waktu>" + escapeXml(notif.waktu) + "</waktu>");
                writer.println("    <pesan>" + escapeXml(notif.pesan) + "</pesan>");
                writer.println("  </notification>");
            }
            writer.println("</notifications>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadNotifications() {
        notifications.clear();
        try {
            File file = new File(NOTIF_FILE);
            if (!file.exists()) return;
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            NodeList notifNodes = doc.getElementsByTagName("notification");
            for (int i = 0; i < notifNodes.getLength(); i++) {
                Element elem = (Element) notifNodes.item(i);
                String dokter = getTagValue(elem, "dokter");
                String waktu = getTagValue(elem, "waktu");
                String pesan = getTagValue(elem, "pesan");
                notifications.add(new Notification(dokter, waktu, pesan));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getTagValue(Element elem, String tag) {
        NodeList nl = elem.getElementsByTagName(tag);
        if (nl.getLength() > 0 && nl.item(0).getFirstChild() != null) {
            return nl.item(0).getFirstChild().getNodeValue();
        }
        return "";
    }

    private static String escapeXml(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&apos;");
    }

    private void renderNotifications() {
        if (notificationList == null) return;
        notificationList.getChildren().clear();
        for (Notification notif : notifications) {
            VBox card = new VBox(6);
            card.setStyle("-fx-background-color: white; -fx-background-radius: 18; -fx-effect: dropshadow(gaussian, #bbb, 6, 0.1, 0, 2); -fx-padding: 18 18 18 18;");
            HBox header = new HBox(10);
            VBox vbox = new VBox(2);
            Label dokterLabel = new Label(notif.dokter);
            dokterLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #222;");
            Label waktuLabel = new Label(notif.waktu);
            waktuLabel.setStyle("-fx-font-size: 15px; -fx-text-fill: #888;");
            vbox.getChildren().addAll(dokterLabel, waktuLabel);
            Region spacer = new Region();
            HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
            Button menuBtn = new Button("⋮");
            menuBtn.setStyle("-fx-background-color: transparent; -fx-font-size: 22px; -fx-text-fill: #222; -fx-cursor: hand;");
            header.getChildren().addAll(vbox, spacer, menuBtn);
            Label pesanLabel = new Label(notif.pesan);
            pesanLabel.setWrapText(true);
            pesanLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #222; -fx-padding: 8 0 0 0;");
            card.getChildren().addAll(header, pesanLabel);
            notificationList.getChildren().add(card);
        }
    }
    
    @FXML
    private void handleViewReply() {
        // Navigate to consultation page to view the specific reply
        Main.showKonsultasiPage();
    }
    
    @FXML
    private void handleMarkAllRead() {
        // Hide all notification items
        if (notificationItem1 != null) {
            notificationItem1.setVisible(false);
        }
        if (notificationItem2 != null) {
            notificationItem2.setVisible(false);
        }
        if (notificationItem3 != null) {
            notificationItem3.setVisible(false);
        }
        
        // Show empty state
        setEmptyState(true);
        
        // Show confirmation
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Notifikasi");
        alert.setHeaderText(null);
        alert.setContentText("Semua notifikasi telah ditandai sebagai dibaca");
        alert.showAndWait();
    }
    
    @FXML
    private void handleViewAll() {
        // Navigate to consultation page to view all conversations
        Main.showKonsultasiPage();
    }
    
    public void setEmptyState(boolean isEmpty) {
        if (isEmpty) {
            emptyState.setVisible(true);
        } else {
            emptyState.setVisible(false);
        }
    }

    @FXML
    private void handleClose(javafx.event.ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }
    

} 