package org.example.Controller;

import java.io.File;
import java.io.IOException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.bson.Document;

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
    private TextField urlField;

    @FXML
    private TextField databaseNameField;

    @FXML
    private TextField collectionField;

    @FXML
    private RadioButton dataFromMongoRadio;

    @FXML
    private RadioButton dataFromJsonRadio;

    private String dataContent;

    @FXML
    public void initialize(){
        dataFromMongoRadio.setSelected(true);
        directoryFileField.setDisable(true);
        directoryFileField.setText("");
        selectJsonFileBtn.setDisable(true);
        urlField.setDisable(false);
        databaseNameField.setDisable(false);
        collectionField.setDisable(false);
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
            secondController.setPath(directoryFileField.getText(), directoryCatalogField.getText(), collectionField.getText(), dataContent, dataFromJsonRadio.isSelected());
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

    public void getData(ActionEvent event) {
        //String clientURI = "mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&ssl=false";
        ErrorWindowController errorWindowController = new ErrorWindowController();
        if (urlField.getText().equals("") || databaseNameField.getText().equals("") || collectionField.getText().equals("")){
            errorWindowController.errorWindow("Aby przejść dalej upewnij się, że wpełnione są pola wymagane do połączenia z bazą MongoDB!!!");
        } else {
            try{
                MongoClient mongoClient = new MongoClient(new MongoClientURI(urlField.getText()));
                MongoDatabase db = mongoClient.getDatabase(databaseNameField.getText());
                MongoCollection<Document> mongoCollection = db.getCollection(collectionField.getText());


                StringBuilder collectionContent = new StringBuilder("[");
                for (Document document : mongoCollection.find()){
                    collectionContent.append(document.toJson()).append(", ").append("\n");
                    System.out.println(document.toJson());
                }
                collectionContent = new StringBuilder(collectionContent.substring(0, collectionContent.length() - 2));
                collectionContent.append("]");
                dataContent = String.valueOf(collectionContent);
                System.out.println(collectionContent);
            } catch (Exception e){
                errorWindowController.errorWindow("Błąd pobierania danych z MongoDB !! \n" + e);
            }

        }


    }

    public void changeAccessData(ActionEvent event) {
        if (dataFromMongoRadio.isSelected()){
            directoryFileField.setDisable(true);
            directoryFileField.setText("");
            selectJsonFileBtn.setDisable(true);
            urlField.setDisable(false);
            databaseNameField.setDisable(false);
            collectionField.setDisable(false);
            getDataFromDBBtn.setDisable(false);
        } else if (dataFromJsonRadio.isSelected()){
            directoryFileField.setDisable(false);
            selectJsonFileBtn.setDisable(false);
            urlField.setDisable(true);
            databaseNameField.setDisable(true);
            collectionField.setDisable(true);
            urlField.setText("");
            databaseNameField.setText("");
            collectionField.setText("");
            getDataFromDBBtn.setDisable(true);
        }
    }
}
