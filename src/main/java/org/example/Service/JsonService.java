package org.example.Service;

import org.example.DTO.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class JsonService {

    private String edgeName;
    private String joinEdgeName;
    private Integer edgeID;
    private Integer joinEdgeID;

    private Integer counter = 0;
    private TreeMap<Integer, String> mapOfObjectID = new TreeMap<>();
    //private Map<String, String> mapOfKeyValues = new HashMap<>();
    private List<Document> documentObjects = new ArrayList<>();


    public void parseJson(String filePath) throws Exception {
        String json = readFileAsString(filePath);
        System.out.println(json);
        printJsonObject(json);
    }

    public void printJsonObject(String json_str) {
        JSONArray jsonArray = new JSONArray(json_str);
        int length = jsonArray.length();
        for(int i=0; i<length; i++) {
            Map<String, String> mapOfKeyValuesDocument = new HashMap<>();
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            Iterator<String> keys = jsonObject.keys();
            System.out.println("/////////////////Nowy obiekt////////////////////");

            counter ++;
            while(keys.hasNext()) {
                String key = keys.next();
                jsonConditions(key, jsonObject, mapOfKeyValuesDocument);
            }
            documentObjects.add(new Document(counter, "", "students", 0, "", "Object", mapOfKeyValuesDocument));
        }
        System.out.println(documentObjects);
    }

    public void jsonConditions(String key, JSONObject jsonObject, Map<String, String> mapOfKeyValuesDocument){
        if (jsonObject.get(key) instanceof JSONObject) {
            getJsonObject(key, jsonObject);
        } else if (jsonObject.get(key) instanceof JSONArray) {
            getJsonArray(key, jsonObject);
        } else {
            System.out.println("key is = "+key+" ==> value is = "+jsonObject.get(key));
            mapOfKeyValuesDocument.put(key, jsonObject.get(key).toString());
        }
    }

    public void getJsonObject(String key, JSONObject jsonObject){
        System.out.println("Obiekt " + key);
        counter ++;
        mapOfObjectID.put(counter, key);
        Map<String, String> mapOfKeyValuesDocument = new HashMap<>();
        JSONObject jsonObjectOfObject = jsonObject.getJSONObject(key);
        for (int j=0; j<jsonObjectOfObject.names().length(); j++){
            if((jsonObjectOfObject.get(jsonObjectOfObject.names().getString(j)) instanceof JSONObject) || (jsonObjectOfObject.get(jsonObjectOfObject.names().getString(j)) instanceof JSONArray)) {
                jsonConditions(jsonObjectOfObject.names().getString(j), jsonObjectOfObject, mapOfKeyValuesDocument);
            } else {
                System.out.println("object key is = "+jsonObjectOfObject.names().getString(j)+" ==> value is = "+jsonObjectOfObject.get(jsonObjectOfObject.names().getString(j)));
                mapOfKeyValuesDocument.put(jsonObjectOfObject.names().getString(j), jsonObjectOfObject.get(jsonObjectOfObject.names().getString(j)).toString());
            }
        }
        edgeID = mapOfObjectID.lastKey();
        edgeName = mapOfObjectID.lastEntry().getValue();
        mapOfObjectID.remove(edgeID);
        if(mapOfObjectID.isEmpty()){
            joinEdgeID = 0;
            joinEdgeName = "";
        } else {
            joinEdgeID = mapOfObjectID.lastKey();
            joinEdgeName = mapOfObjectID.lastEntry().getValue();
        }
        documentObjects.add(new Document(edgeID, "", edgeName, joinEdgeID, joinEdgeName, "Object", mapOfKeyValuesDocument));
        System.out.println("Obiekt koniec");
    }

    public void getJsonArray(String key, JSONObject jsonObject){
        System.out.println("Tablica");
        JSONArray jsonArrayOfArray = jsonObject.getJSONArray(key);
        Map<String, String> mapOfKeyValuesDocument = new HashMap<>();
        for (int k=0; k<jsonArrayOfArray.length(); k++){
            if (!(jsonArrayOfArray.get(k) instanceof JSONObject)){
                System.out.println("KEY TABLICA " + key + " WARTOSC  " + jsonArrayOfArray.get(k));
            } else {
                JSONObject jsonObjectOfArray = jsonArrayOfArray.getJSONObject(k);
                Iterator<String> arrayKeys = jsonObjectOfArray.keys();
                while (arrayKeys.hasNext()){
                    String keyArray = arrayKeys.next();
                    if((jsonObjectOfArray.get(keyArray) instanceof JSONArray) || (jsonObjectOfArray.get(keyArray) instanceof JSONObject)) {
                        jsonConditions(keyArray, jsonObjectOfArray, mapOfKeyValuesDocument);
                    } else {
                        System.out.println("key Obiekt tablicy = "+keyArray+" ==> value is = "+jsonObjectOfArray.get(keyArray));
                    }
                }
            }
        }
        System.out.println("Tablica koniec");
    }

    public String readFileAsString(String file) throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

}
