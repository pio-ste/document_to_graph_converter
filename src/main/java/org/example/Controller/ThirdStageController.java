package org.example.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.Service.CypherService;
import org.example.Service.JsonService;

import java.io.IOException;

public class ThirdStageController {


    @FXML
    public void initialize(){

        /*Platform.runLater(() -> {
            CypherService cypherService = new CypherService();
            try {
                cypherService.convert(documentObjects);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });*/


    }

    @FXML
    private void previousStep(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/FXML/secondStage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
