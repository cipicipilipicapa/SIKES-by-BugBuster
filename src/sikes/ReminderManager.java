package sikes;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReminderManager {
    private static final String REMINDER_FILE = "reminders.xml";
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final Map<String, Reminder> activeReminders = new HashMap<>();
    private static Stage currentPopup = null;
    private static boolean schedulerStarted = false;
    
    public static class Reminder {
        public String id;
        public String username;
        public String namaObat;
        public String frekuensi;
        public String waktu;
        public String catatan;
        public boolean active;
        
        public Reminder(String id, String username, String namaObat, String frekuensi, String waktu, String catatan) {
            this.id = id;
            this.username = username;
            this.namaObat = namaObat;
            this.frekuensi = frekuensi;
            this.waktu = waktu;
            this.catatan = catatan;
            this.active = true;
        }
        
        public Reminder(String username, String namaObat, String frekuensi, String waktu, String catatan) {
            this(UUID.randomUUID().toString(), username, namaObat, frekuensi, waktu, catatan);
        }
    }
    
    public static void initialize() {
        loadReminders();
        startScheduler();
    }
    
    public static void initializeWithoutScheduler() {
        loadReminders();
        // Don't start scheduler - will be started after login
    }
    
    public static void startNotifications() {
        if (!schedulerStarted) {
            startScheduler();
            schedulerStarted = true;
        }
    }
    
    public static void shutdown() {
        scheduler.shutdown();
    }
    
    public static void addReminder(String username, String namaObat, String frekuensi, String waktu, String catatan) {
        Reminder reminder = new Reminder(username, namaObat, frekuensi, waktu, catatan);
        activeReminders.put(reminder.id, reminder);
        scheduleReminder(reminder);
        saveReminders();
    }
    
    public static void deleteReminder(String reminderId) {
        Reminder reminder = activeReminders.remove(reminderId);
        if (reminder != null) {
            reminder.active = false;
        }
        saveReminders();
    }
    
    public static List<Reminder> getRemindersForUser(String username) {
        List<Reminder> userReminders = new ArrayList<>();
        for (Reminder reminder : activeReminders.values()) {
            if (reminder.username.equals(username) && reminder.active) {
                userReminders.add(reminder);
            }
        }
        return userReminders;
    }
    
    private static void scheduleReminder(Reminder reminder) {
        try {
            LocalTime reminderTime = LocalTime.parse(reminder.waktu, DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime now = LocalTime.now();
            
            long initialDelay;
            if (reminderTime.isAfter(now)) {
                // Reminder is today
                initialDelay = java.time.Duration.between(now, reminderTime).toSeconds();
            } else {
                // Reminder is tomorrow
                initialDelay = java.time.Duration.between(now, reminderTime.plusHours(24)).toSeconds();
            }
            
            scheduler.scheduleAtFixedRate(() -> {
                if (reminder.active) {
                    Platform.runLater(() -> showReminderPopup(reminder));
                }
            }, initialDelay, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void showReminderPopup(Reminder reminder) {
        try {
            // Close existing popup if any
            if (currentPopup != null) {
                currentPopup.close();
            }
            
            FXMLLoader loader = new FXMLLoader(ReminderManager.class.getResource("reminder_popup.fxml"));
            Parent root = loader.load();
            
            ReminderPopupController controller = loader.getController();
            controller.setReminder(reminder);
            
            currentPopup = new Stage();
            currentPopup.initStyle(StageStyle.UNDECORATED);
            currentPopup.initModality(Modality.APPLICATION_MODAL);
            currentPopup.setTitle("Pengingat Obat");
            currentPopup.setScene(new Scene(root));
            currentPopup.setAlwaysOnTop(true);
            
            // Center the popup on screen
            currentPopup.centerOnScreen();
            currentPopup.show();
            
        } catch (Exception e) {
            e.printStackTrace();
            // Fallback to simple alert if popup fails
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Pengingat Obat");
                alert.setHeaderText("Waktunya minum obat!");
                alert.setContentText(reminder.namaObat + " - " + reminder.frekuensi + "\n" + reminder.catatan);
                alert.showAndWait();
            });
        }
    }
    
    private static void startScheduler() {
        // Reschedule all existing reminders
        for (Reminder reminder : activeReminders.values()) {
            if (reminder.active) {
                scheduleReminder(reminder);
            }
        }
    }
    
    private static void saveReminders() {
        try {
            StringBuilder xml = new StringBuilder();
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xml.append("<reminders>\n");
            
            for (Reminder reminder : activeReminders.values()) {
                xml.append("  <reminder>\n");
                xml.append("    <id>").append(reminder.id).append("</id>\n");
                xml.append("    <username>").append(reminder.username).append("</username>\n");
                xml.append("    <namaObat>").append(reminder.namaObat).append("</namaObat>\n");
                xml.append("    <frekuensi>").append(reminder.frekuensi).append("</frekuensi>\n");
                xml.append("    <waktu>").append(reminder.waktu).append("</waktu>\n");
                xml.append("    <catatan>").append(reminder.catatan != null ? reminder.catatan : "").append("</catatan>\n");
                xml.append("    <active>").append(reminder.active).append("</active>\n");
                xml.append("  </reminder>\n");
            }
            
            xml.append("</reminders>");
            
            try (PrintWriter writer = new PrintWriter(new FileWriter(REMINDER_FILE))) {
                writer.write(xml.toString());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void loadReminders() {
        File file = new File(REMINDER_FILE);
        if (!file.exists()) {
            return;
        }
        
        try {
            javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document document = builder.parse(file);
            
            org.w3c.dom.NodeList reminderNodes = document.getElementsByTagName("reminder");
            
            for (int i = 0; i < reminderNodes.getLength(); i++) {
                org.w3c.dom.Node node = reminderNodes.item(i);
                if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    
                    String id = getElementText(element, "id");
                    String username = getElementText(element, "username");
                    String namaObat = getElementText(element, "namaObat");
                    String frekuensi = getElementText(element, "frekuensi");
                    String waktu = getElementText(element, "waktu");
                    String catatan = getElementText(element, "catatan");
                    boolean active = Boolean.parseBoolean(getElementText(element, "active"));
                    
                    Reminder reminder = new Reminder(id, username, namaObat, frekuensi, waktu, catatan);
                    reminder.active = active;
                    activeReminders.put(id, reminder);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static String getElementText(org.w3c.dom.Element parent, String tagName) {
        org.w3c.dom.NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }
    
    public static void closeCurrentPopup() {
        if (currentPopup != null) {
            currentPopup.close();
            currentPopup = null;
        }
    }
} 