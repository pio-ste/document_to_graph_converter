package org.example.Service;

import org.example.DTO.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.TreeMap;


public class JsonService {

    private String fileName;
    private String edgeName;
    private String joinEdgeName;
    private Integer edgeID;
    private Integer joinEdgeID;
    private boolean isArray;

    private Integer counter = 0;
    private TreeMap<Integer, String> mapOfObjectID = new TreeMap<>();
    private TreeMap<Integer, Document> mapOfDocuments = new TreeMap<>();

    public TreeMap<Integer, Document> iterateOverJson(String json_str) {
        JSONArray jsonArray = new JSONArray(json_str);
        int length = jsonArray.length();
        for(int i=0; i<length; i++) {
            LinkedHashMap<String, String> mapOfKeyValuesDocument = new LinkedHashMap<>();
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            Iterator<String> keys = jsonObject.keys();
            counter ++;
            isArray = true;
            mapOfObjectID.put(counter, fileName);
            while(keys.hasNext()) {
                String key = keys.next();
                jsonConditions(key, jsonObject, mapOfKeyValuesDocument);
            }

            addElementsIntoMap(mapOfKeyValuesDocument, "Object");
            mapOfObjectID.clear();
        }
        return mapOfDocuments;
    }

    public void jsonConditions(String key, JSONObject jsonObject, LinkedHashMap<String, String> mapOfKeyValuesDocument){
        if (jsonObject.get(key) instanceof JSONObject) {
            getJsonObject(key, jsonObject);
        } else if (jsonObject.get(key) instanceof JSONArray) {
            getJsonArray(key, jsonObject);
        } else {
            mapOfKeyValuesDocument.put(key, jsonObject.get(key).toString());
        }
    }

    public void getJsonObject(String key, JSONObject jsonObject){
        counter ++;
        mapOfObjectID.put(counter, key);
        LinkedHashMap<String, String> mapOfKeyValuesDocument = new LinkedHashMap<>();
        JSONObject jsonObjectOfObject = jsonObject.getJSONObject(key);
        for (int j=0; j<jsonObjectOfObject.names().length(); j++){
            if((jsonObjectOfObject.get(jsonObjectOfObject.names().getString(j)) instanceof JSONObject) || (jsonObjectOfObject.get(jsonObjectOfObject.names().getString(j)) instanceof JSONArray)) {
                jsonConditions(jsonObjectOfObject.names().getString(j), jsonObjectOfObject, mapOfKeyValuesDocument);
            } else {
                mapOfKeyValuesDocument.put(jsonObjectOfObject.names().getString(j), jsonObjectOfObject.get(jsonObjectOfObject.names().getString(j)).toString());
            }
        }
        addElementsIntoMap(mapOfKeyValuesDocument, "Object");
    }

    public void getJsonArray(String key, JSONObject jsonObject){
        JSONArray jsonArrayOfArray = jsonObject.getJSONArray(key);
        LinkedHashMap<String, String> mapOfKeyValuesDocument = new LinkedHashMap<>();
        for (int k=0; k<jsonArrayOfArray.length(); k++){
            if (!(jsonArrayOfArray.get(k) instanceof JSONObject)){
                mapOfKeyValuesDocument.put(String.valueOf(k), jsonArrayOfArray.get(k).toString());
            } else {
                counter ++;
                mapOfObjectID.put(counter, key);
                isArray = false;
                LinkedHashMap<String, String> mapOfKeyValuesDocumentArray = new LinkedHashMap<>();
                JSONObject jsonObjectOfArray = jsonArrayOfArray.getJSONObject(k);
                Iterator<String> arrayKeys = jsonObjectOfArray.keys();
                while (arrayKeys.hasNext()){
                    String keyArray = arrayKeys.next();
                    if((jsonObjectOfArray.get(keyArray) instanceof JSONArray) || (jsonObjectOfArray.get(keyArray) instanceof JSONObject)) {
                        isArray = true;
                        jsonConditions(keyArray, jsonObjectOfArray, mapOfKeyValuesDocumentArray);
                    } else {
                        mapOfKeyValuesDocumentArray.put(keyArray, jsonObjectOfArray.get(keyArray).toString());
                    }
                }
                addElementsIntoMap(mapOfKeyValuesDocumentArray, "Object");
            }
        }
        if (isArray){
            counter ++;
            mapOfObjectID.put(counter, key);
            addElementsIntoMap(mapOfKeyValuesDocument, "Array");
        }
        isArray = true;
    }

    public void addElementsIntoMap(LinkedHashMap<String, String> mapOfKeyValuesDocument, String type) {
        LinkedHashMap<String, String> mapOfRelationValues = new LinkedHashMap<>();
        edgeID = mapOfObjectID.lastKey();
        edgeName = mapOfObjectID.lastEntry().getValue();
        mapOfObjectID.remove(edgeID);
        if(mapOfObjectID.isEmpty()){
            joinEdgeID = null;
            joinEdgeName = null;
        } else {
            joinEdgeID = mapOfObjectID.lastKey();
            joinEdgeName = mapOfObjectID.lastEntry().getValue();

        }
        mapOfDocuments.put(edgeID, new Document(edgeID, null, mapOfRelationValues, edgeName, joinEdgeID, joinEdgeName, type, mapOfKeyValuesDocument));
    }

    public String readFileAsString(String filePath, String file) throws Exception {
        fileName = file;
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

}
