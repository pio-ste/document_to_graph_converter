package org.example.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.Service.DbService;

import java.io.File;
import java.io.IOException;


public class FirstStageController {

    @FXML
    private TextField directoryFileField;

    @FXML
    private TextField directoryCatalogField;

    @FXML
    private Button selectJsonFileBtn;

    @FXML
    private Button getDataFromDBBtn;

    @FXML
    private TextField uriMongoField;

    @FXML
    private TextField dbNameMongoField;

    @FXML
    private TextField collectionMongoField;

    @FXML
    private TextField uriNeo4jField;

    @FXML
    private TextField userNameNeo4jField;

    @FXML
    private TextField passwordNoe4jField;

    @FXML
    private RadioButton dataFromMongoRadio;

    @FXML
    private RadioButton dataFromJsonRadio;

    @FXML
    private Label statusMongoLabel;

    @FXML
    private Label statusNeo4jLabel;

    private String dataContent;

    @FXML
    public void initialize(){
        statusMongoLabel.setVisible(false);
        statusNeo4jLabel.setVisible(false);
        dataFromMongoRadio.setSelected(true);
        directoryFileField.setDisable(true);
        directoryFileField.setText("");
        selectJsonFileBtn.setDisable(true);
        uriMongoField.setDisable(false);
        dbNameMongoField.setDisable(false);
        collectionMongoField.setDisable(false);
        getDataFromDBBtn.setDisable(false);
    }

    @FXML
    private void nextStep(ActionEvent actionEvent) throws IOException {
        ErrorWindowController errorWindowController = new ErrorWindowController();
        if(dataFromJsonRadio.isSelected() && (directoryFileField.getText().equals("") || directoryCatalogField.getText().equals(""))){
            errorWindowController.errorWindow("Aby przejść dalej upewnij się, że wybrany jest plik oraz katalog!!!");
        } else if (dataFromMongoRadio.isSelected() && dataContent == null){
            errorWindowController.errorWindow("Aby przejść dalej upewnij się, że pobrałeś dane z bazy MongoDB!!!");
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/FXML/secondStage.fxml"));
            Parent root = loader.load();
            SecondStageController secondController = loader.getController();
            secondController.setPath(directoryFileField.getText(), directoryCatalogField.getText(), collectionMongoField.getText(), dataContent,
                    dataFromJsonRadio.isSelected(), uriNeo4jField.getText(), userNameNeo4jField.getText(), passwordNoe4jField.getText());
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
            ErrorWindowController errorWindowController = new ErrorWindowController();
            errorWindowController.errorWindow("Wystapił błąd w wyborze pliku. Spróbuj ponownie.");
        }
    }

    public void selectDirectory(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);

        if(selectedDirectory != null){
            directoryCatalogField.setText(selectedDirectory.getAbsolutePath());
        }else{
            ErrorWindowController errorWindowController = new ErrorWindowController();
            errorWindowController.errorWindow("Wystapił błąd w wyborze ściezki. Spróbuj ponownie.");
        }
    }

    public void getDataMongo(ActionEvent event) {
        //String clientURI = "mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&ssl=false";
        ErrorWindowController errorWindowController = new ErrorWindowController();
        if (uriMongoField.getText().equals("") || dbNameMongoField.getText().equals("") || collectionMongoField.getText().equals("")){
            errorWindowController.errorWindow("Aby przejść dalej upewnij się, że wpełnione są pola wymagane do połączenia z bazą MongoDB!!!");
        } else {
            DbService dbService = new DbService();
            long startTime = System.currentTimeMillis();
            dataContent = dbService.getDataFromMongo(uriMongoField.getText(), dbNameMongoField.getText(), collectionMongoField.getText(), errorWindowController, statusMongoLabel);
            long endTime = System.currentTimeMillis();
            System.out.println("Czas pobierania danych z MongoDB w milisekundach  "+(endTime - startTime));

        }
    }

    public void changeAccessData(ActionEvent event) {
        if (dataFromMongoRadio.isSelected()){
            statusMongoLabel.setVisible(false);
            directoryFileField.setDisable(true);
            directoryFileField.setText("");
            selectJsonFileBtn.setDisable(true);
            uriMongoField.setDisable(false);
            dbNameMongoField.setDisable(false);
            collectionMongoField.setDisable(false);
            getDataFromDBBtn.setDisable(false);
        } else if (dataFromJsonRadio.isSelected()){
            statusMongoLabel.setVisible(false);
            directoryFileField.setDisable(false);
            selectJsonFileBtn.setDisable(false);
            uriMongoField.setDisable(true);
            dbNameMongoField.setDisable(true);
            collectionMongoField.setDisable(true);
            uriMongoField.setText("");
            dbNameMongoField.setText("");
            collectionMongoField.setText("");
            getDataFromDBBtn.setDisable(true);
        }
    }

    public void testConnectionNeo4j(ActionEvent event) {
        ErrorWindowController errorWindowController = new ErrorWindowController();
        DbService dbService = new DbService();
        dbService.executeQueryNeo4j(uriNeo4jField.getText(), userNameNeo4jField.getText(), passwordNoe4jField.getText(), "Match () Return 1 Limit 1", errorWindowController, statusNeo4jLabel);
    }
}
