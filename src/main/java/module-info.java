module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example.Controller to javafx.fxml;
    exports org.example;
}