module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires gson;
    requires org.json;
    requires com.google.common;
    requires mongo.java.driver;

    opens org.example.Controller to javafx.fxml;
    opens org.example.Service to javafx.base;
    opens org.example.DTO to javafx.base;
    exports org.example;
}