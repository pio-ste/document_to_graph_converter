package org.example.Controller;

import javafx.scene.control.Alert;

public class ErrorWindowController {
    public void errorWindow(String errorType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Wystąpił błąd");
        alert.setContentText(errorType);
        alert.showAndWait();
    }
}