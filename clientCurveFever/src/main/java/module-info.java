module com.example.clientap6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
            
                            
    opens com.example.clientap6 to javafx.fxml;
    exports com.example.clientap6;
    exports com.example.clientap6.controllers;
    opens com.example.clientap6.controllers to javafx.fxml;
}