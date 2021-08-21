module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires mongo.java.driver;
    requires org.neo4j.driver;

    opens org.example.Controller to javafx.fxml;
    opens org.example.Service to javafx.base;
    opens org.example.DTO to javafx.base;
    exports org.example;
}