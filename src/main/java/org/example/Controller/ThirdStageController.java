package org.example.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.example.DTO.Document;
import org.example.Service.CypherService;
import org.example.Service.DbService;

import java.io.IOException;
import java.util.TreeMap;

public class ThirdStageController {

    @FXML
    private TextArea queryTextArea;

    @FXML
    private Label statusNeo4jLabel;

    private TreeMap<Integer, Document> documentObjects = new TreeMap<>();
    private String catalogPath;
    private String fileName;
    private String path;
    private String uri;
    private String userName;
    private String password;
    private String cypherQuery;

    @FXML
    public void setPath(TreeMap<Integer, Document> documentObjectsSet, String catalogPathSet, String fileNameSet, String uriNeo4j, String userNameNeo4j, String passwordNeo4j){
        documentObjects = documentObjectsSet;
        catalogPath = catalogPathSet;
        fileName = fileNameSet;
        uri = uriNeo4j;
        userName = userNameNeo4j;
        password = passwordNeo4j;
    }

    @FXML
    public void initialize(){
        statusNeo4jLabel.setVisible(false);
        Platform.runLater(() -> {
            CypherService cypherService = new CypherService();
            try {
                path = cypherService.changePath(catalogPath, fileName);
                cypherService.convert(documentObjects, path);
                cypherQuery = cypherService.readFile(path);
                queryTextArea.setText(cypherQuery);
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

    public void executeQuery(ActionEvent event) {
        ErrorWindowController errorWindowController = new ErrorWindowController();
        if (uri.equals("") || userName.equals("") || password.equals("")){
            errorWindowController.errorWindow("Brak podanych danych do połączenia się z Neo4j !!!");
        } else {
            try {
                DbService dbService = new DbService();
                statusNeo4jLabel.setVisible(false);
                dbService.executeQueryNeo4j(uri, userName, password, cypherQuery);
                statusNeo4jLabel.setVisible(true);
            } catch (Exception e) {
                errorWindowController.errorWindow("Błąd w wykonaniu polecenia!!!");
            }
        }
    }
}
