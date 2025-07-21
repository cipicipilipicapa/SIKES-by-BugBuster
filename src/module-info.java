module sikes {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    
    opens sikes to javafx.fxml;
    exports sikes;
} 