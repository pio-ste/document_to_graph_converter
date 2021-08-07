package org.example.Service;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.scene.control.Label;
import org.bson.Document;
import org.example.Controller.ErrorWindowController;

public class DbService {

    public String getDataFromMongo(String uri, String dbName, String collectionName, ErrorWindowController errorWindowController, Label statusMongoLabel){
        try{
            statusMongoLabel.setVisible(false);
            MongoClient mongoClient = new MongoClient(new MongoClientURI(uri));
            MongoDatabase db = mongoClient.getDatabase(dbName);
            MongoCollection<Document> mongoCollection = db.getCollection(collectionName);

            StringBuilder collectionContent = new StringBuilder("[");
            for (Document document : mongoCollection.find()){
                collectionContent.append(document.toJson()).append(", ").append("\n");
                System.out.println(document.toJson());
            }
            collectionContent = new StringBuilder(collectionContent.substring(0, collectionContent.length() - 2));
            collectionContent.append("]");
            statusMongoLabel.setVisible(true);
            return String.valueOf(collectionContent);
        } catch (Exception e){
            errorWindowController.errorWindow("Błąd pobierania danych z MongoDB !! \n" + e);
        }
        return "[{}]";
    }
}
