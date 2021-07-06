package org.example.Controller;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FirstStageController {

    @FXML
    private TextField directoryFileField;

    @FXML
    private TextField directoryCatalogField;

    @FXML
    private void nextStep(ActionEvent actionEvent) throws IOException {
        if(directoryFileField.getText().equals("")){
            errorWindow("Aby przejść dalej upewnij się, że wybrany jest plik oraz katalog!!!");
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/FXML/secondStage.fxml"));
            Parent root = loader.load();
            SecondStageController secondController = loader.getController();
            secondController.setPath(directoryFileField.getText(), directoryCatalogField.getText());
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    public void selectFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = fileChooser.showOpenDialog(null);
        if(file != null){
            directoryFileField.setText(file.getAbsolutePath());
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

    public void selectDirectory(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);

        if(selectedDirectory != null){
            directoryCatalogField.setText(selectedDirectory.getAbsolutePath());
        }else{
            errorWindow("Wystapił błąd w wyborze ściezki. Spróbuj ponownie.");
        }
    }
}
