package org.example.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.DTO.Document;
import org.example.Service.JsonService;

public class SecondStageController {

    @FXML
    private TextArea jsonContentTextField;

    @FXML
    private TableView<Document> tableRelations;

    @FXML
    private TableColumn<Document, Integer> idColumn;

    @FXML
    private TableColumn<Document, String> object1Column;

    @FXML
    private TableColumn<Document, String> relationColumn;

    @FXML
    private TableColumn<Document, String> object2Column;

    @FXML
    private Label idLabel;

    @FXML
    private Label objectName1Label;

    @FXML
    private Label objectName2Label;

    @FXML
    private TextField nodeField;

    public String filePath;
    public String catalogPath;
    public String jsonContent;
    private TreeMap<Integer, Document> documentObjects = new TreeMap<>();

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
                setTableView();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }


    public void setTableView() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        object1Column.setCellValueFactory(new PropertyValueFactory<>("EdgeJoinName"));
        relationColumn.setCellValueFactory(new PropertyValueFactory<>("NodeName"));
        object2Column.setCellValueFactory(new PropertyValueFactory<>("EdgeName"));
        tableRelations.setItems(observableList());
    }

    ObservableList<Document> observableList() {
        ObservableList<Document> documents = FXCollections.observableArrayList();
        for (Map.Entry<Integer, Document> documentEntry: documentObjects.entrySet()) {
            Document document = documentEntry.getValue();
            if (document.getJoinId() != null && document.getType().equals("Object")){
                documents.add(new Document(document.getId(), document.getEdgeJoinName(), document.getNodeName(), document.getEdgeName()));
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

    @FXML
    public void editRelation(ActionEvent event) {
        String nodeName = nodeField.getText();
        Integer idEdge = Integer.parseInt(idLabel.getText());
        Document document;
        document = documentObjects.get(idEdge);
        document.setNodeName(nodeName);
        documentObjects.put(idEdge, document);
        System.out.println("");
        setTableView();
    }

    @FXML
    public void getSelectedRow(MouseEvent mouseEvent) {
        Document document = tableRelations.getSelectionModel().getSelectedItem();
        if (document == null){
            idLabel.setText("-");
            objectName1Label.setText("-");
            objectName2Label.setText("-");
        } else {
            idLabel.setText(document.getId().toString());
            objectName1Label.setText(document.getEdgeJoinName());
            objectName2Label.setText(document.getEdgeName());
        }
    }
}