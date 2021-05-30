module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires gson;
    requires org.json;

    opens org.example.Controller to javafx.fxml;
    exports org.example;
}