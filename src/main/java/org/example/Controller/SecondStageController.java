package org.example.Controller;

import java.io.IOException;

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
import org.example.Service.JsonService;
import org.example.Service.Sample;

public class SecondStageController {

    @FXML
    private TextArea jsonContentTextField;

    @FXML
    private TableView<Sample> tableRelations;

    @FXML
    private TableColumn<Sample, String> object1Column;

    @FXML
    private TableColumn<Sample, String> relationColumn;

    @FXML
    private TableColumn<Sample, String> object2Column;

    public String filePath;
    public String catalogPath;
    public String jsonContent;

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
                jsonService.printJsonObject(jsonContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            object1Column.setCellValueFactory(new PropertyValueFactory<>("ObjectNameFirst"));
            relationColumn.setCellValueFactory(new PropertyValueFactory<>("RelationName"));
            object2Column.setCellValueFactory(new PropertyValueFactory<>("ObjectNameSecond"));
            tableRelations.setItems(observableList());
        });

    }

    ObservableList<Sample> observableList() {
        ObservableList<Sample> samples = FXCollections.observableArrayList();
        samples.add(new Sample("Osoba", "Posiada", "Oceny"));
        samples.add(new Sample("Osoba", "", "Adres"));
        return samples;
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