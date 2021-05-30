package org.example.Controller;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.example.App;
import org.example.Service.JsonService;

public class FirstStageController {

    @FXML
    private TextField directoryField;

    @FXML
    private void nextStep() throws IOException {
        if(directoryField.getText().equals("")){
            errorWindow("Wybierz plik do konwersji aby przejść dalej!!!");
        } else {
            App.setRoot("FXML/secondStage");
        }
    }

    public void selectFile(ActionEvent event) {
        JsonService jsonService = new JsonService();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = fileChooser.showOpenDialog(null);

        if(file != null){
            directoryField.setText(file.getAbsolutePath());
            try {
                jsonService.parseJson(file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            errorWindow("Wystapił błąd w wyborze pliku. Spróbuj ponownie.");
        }
    }

    public void errorWindow(String errorType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Wystąpił błąd");
        alert.setContentText(errorType);
        alert.showAndWait();
    }
}
