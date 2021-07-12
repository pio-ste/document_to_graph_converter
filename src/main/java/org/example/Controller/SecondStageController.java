package org.example.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.DTO.Document;
import org.example.Service.JsonService;

public class SecondStageController {

    @FXML
    private TextArea jsonContentTextField;

    @FXML
    private TableView<Document> tableRelations;

    @FXML
    private TableColumn<Document, String> object1Column;

    @FXML
    private TableColumn<Document, String> relationColumn;

    @FXML
    private TableColumn<Document, String> object2Column;

    public String filePath;
    public String catalogPath;
    public String jsonContent;
    private List<Document> documentObjects = new ArrayList<>();

    @FXML
    public void setPath(String filePathField, String catalogPathField){
        filePath = filePathField;
        catalogPath = catalogPathField;
    }

    @FXML
    public void initialize(){

        Platform.runLater(() -> {
            JsonService jsonService = new JsonService();
            try {
                jsonContent = jsonService.readFileAsString(filePath);
                jsonContentTextField.setText(jsonContent);
                documentObjects = jsonService.iterateOverJson(jsonContent);
                tableRelations.setItems(observableList());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        object1Column.setCellValueFactory(new PropertyValueFactory<>("EdgeJoinName"));
        relationColumn.setCellValueFactory(new PropertyValueFactory<>("NodeName"));
        object2Column.setCellValueFactory(new PropertyValueFactory<>("EdgeName"));

    }

    ObservableList<Document> observableList() {
        ObservableList<Document> documents = FXCollections.observableArrayList();
        for (Document document : documentObjects) {
            if (document.getJoinId() != null && document.getType().equals("Object")){
                documents.add(new Document(document.getEdgeJoinName(), document.getNodeName(), document.getEdgeName()));
            }
        }
        return documents;
    }

    @FXML
    private void nextStep(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/FXML/thirdStage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void previousStep(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/FXML/firstStage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}