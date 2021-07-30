package org.example.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.example.DTO.Document;
import org.example.Service.CypherService;

import java.io.IOException;
import java.util.TreeMap;

public class ThirdStageController {

    @FXML
    private TextArea queryTextArea;

    private TreeMap<Integer, Document> documentObjects = new TreeMap<>();
    private String catalogPath;
    private String fileName;
    private String path;

    @FXML
    public void setPath(TreeMap<Integer, Document> documentObjectsSet, String catalogPathSet, String fileNameSet){
        documentObjects = documentObjectsSet;
        catalogPath = catalogPathSet;
        fileName = fileNameSet;
    }

    @FXML
    public void initialize(){
        Platform.runLater(() -> {
            CypherService cypherService = new CypherService();
            try {
                path = cypherService.changePath(catalogPath, fileName);
                cypherService.convert(documentObjects, path);
                queryTextArea.setText(cypherService.readFile(path));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void firstStep(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/FXML/firstStage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
