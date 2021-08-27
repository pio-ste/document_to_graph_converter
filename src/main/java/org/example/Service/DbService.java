package org.example.Service;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.scene.control.Label;
import org.bson.Document;
import org.example.Controller.ErrorWindowController;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

public class DbService {

    public String getDataFromMongo(String uri, String dbName, String collectionName,
                                   ErrorWindowController errorWindowController, Label statusMongoLabel){
        try{
            statusMongoLabel.setVisible(false);
            MongoClient mongoClient = new MongoClient(new MongoClientURI(uri));
            MongoDatabase db = mongoClient.getDatabase(dbName);
            MongoCollection<Document> mongoCollection = db.getCollection(collectionName);

            StringBuilder collectionContent = new StringBuilder("[");
            for (Document document : mongoCollection.find()){
                collectionContent.append(document.toJson()).append(", ").append("\n");
                //System.out.println(document.toJson());
            }
            collectionContent = new StringBuilder(collectionContent.substring(0, collectionContent.length() - 3));
            collectionContent.append("]");
            statusMongoLabel.setVisible(true);
            return String.valueOf(collectionContent);
        } catch (Exception e){
            errorWindowController.errorWindow("Błąd pobierania danych z MongoDB !! \n" + e);
        }
        return "[{}]";
    }

    public void executeQueryNeo4j(String uri, String userName, String password, String query,
                                  ErrorWindowController errorWindowController, Label statusNeo4jLabel){
        try{
            statusNeo4jLabel.setVisible(false);
            Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(userName, password));
            Session session = driver.session();
            session.writeTransaction(transaction -> transaction.run(query));
            session.close();
            statusNeo4jLabel.setVisible(true);
        } catch (Exception e) {
            errorWindowController.errorWindow("Błąd przy połączeniu z Neo4j !!" + e);
        }
    }
}
