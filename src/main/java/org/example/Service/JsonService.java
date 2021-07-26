package org.example.Service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.example.DTO.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class JsonService {

    private String fileName;
    private String edgeName;
    private String joinEdgeName;
    private Integer edgeID;
    private Integer joinEdgeID;
    private boolean isArray;

    private Integer counter = 0;
    private TreeMap<Integer, String> mapOfObjectID = new TreeMap<>();
    private List<Document> documentObjects = new ArrayList<>();
    private TreeMap<Integer, Document> mapOfDocuments = new TreeMap<>();


    public void parseJson(String filePath) throws Exception {
        String json = readFileAsString(filePath);
        System.out.println(json);
        iterateOverJson(json);
    }

    public TreeMap<Integer, Document> iterateOverJson(String json_str) {
        JSONArray jsonArray = new JSONArray(json_str);
        int length = jsonArray.length();
        for(int i=0; i<length; i++) {
            LinkedHashMap<String, String> mapOfKeyValuesDocument = new LinkedHashMap<>();
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            Iterator<String> keys = jsonObject.keys();
            //System.out.println("/////////////////Nowy obiekt////////////////////");
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
        System.out.println(mapOfDocuments);
        return mapOfDocuments;
    }

    public void jsonConditions(String key, JSONObject jsonObject, LinkedHashMap<String, String> mapOfKeyValuesDocument){
        if (jsonObject.get(key) instanceof JSONObject) {
            getJsonObject(key, jsonObject);
        } else if (jsonObject.get(key) instanceof JSONArray) {
            getJsonArray(key, jsonObject);
        } else {
            //System.out.println("key is = "+key+" ==> value is = "+jsonObject.get(key));
            mapOfKeyValuesDocument.put(key, jsonObject.get(key).toString());
        }
    }

    public void getJsonObject(String key, JSONObject jsonObject){
        //System.out.println("Obiekt " + key);
        counter ++;
        mapOfObjectID.put(counter, key);
        LinkedHashMap<String, String> mapOfKeyValuesDocument = new LinkedHashMap<>();
        JSONObject jsonObjectOfObject = jsonObject.getJSONObject(key);
        for (int j=0; j<jsonObjectOfObject.names().length(); j++){
            if((jsonObjectOfObject.get(jsonObjectOfObject.names().getString(j)) instanceof JSONObject) || (jsonObjectOfObject.get(jsonObjectOfObject.names().getString(j)) instanceof JSONArray)) {
                jsonConditions(jsonObjectOfObject.names().getString(j), jsonObjectOfObject, mapOfKeyValuesDocument);
            } else {
                //System.out.println("object key is = "+jsonObjectOfObject.names().getString(j)+" ==> value is = "+jsonObjectOfObject.get(jsonObjectOfObject.names().getString(j)));
                mapOfKeyValuesDocument.put(jsonObjectOfObject.names().getString(j), jsonObjectOfObject.get(jsonObjectOfObject.names().getString(j)).toString());
            }
        }
        addElementsIntoMap(mapOfKeyValuesDocument, "Object");
        //System.out.println("Obiekt koniec");
    }



    public void getJsonArray(String key, JSONObject jsonObject){
        //System.out.println("Tablica");
        JSONArray jsonArrayOfArray = jsonObject.getJSONArray(key);
        LinkedHashMap<String, String> mapOfKeyValuesDocument = new LinkedHashMap<>();
        for (int k=0; k<jsonArrayOfArray.length(); k++){
            if (!(jsonArrayOfArray.get(k) instanceof JSONObject)){
                //System.out.println("KEY TABLICA " + key + " WARTOSC  " + jsonArrayOfArray.get(k));
                mapOfKeyValuesDocument.put(String.valueOf(k), jsonArrayOfArray.get(k).toString());
            } else {
                counter ++;
                mapOfObjectID.put(counter, key);
                isArray = false;
                LinkedHashMap<String, String> mapOfKeyValuesDocumentArray = new LinkedHashMap<>();
                //Multimap<String, String> mapOfKeyValuesDocumentArray = ArrayListMultimap.create();
                JSONObject jsonObjectOfArray = jsonArrayOfArray.getJSONObject(k);
                Iterator<String> arrayKeys = jsonObjectOfArray.keys();
                //System.out.println("Nowy obiekt tablicy");
                while (arrayKeys.hasNext()){
                    String keyArray = arrayKeys.next();
                    if((jsonObjectOfArray.get(keyArray) instanceof JSONArray) || (jsonObjectOfArray.get(keyArray) instanceof JSONObject)) {
                        isArray = true;
                        jsonConditions(keyArray, jsonObjectOfArray, mapOfKeyValuesDocumentArray);
                    } else {
                        //System.out.println("key Obiekt tablicy = "+keyArray+" ==> value is = "+jsonObjectOfArray.get(keyArray));
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
        //System.out.println("Tablica koniec");
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

    public String readFileAsString(String filePath) throws Exception
    {
        Path path = Paths.get(filePath);
        Path file = path.getFileName();
        fileName = file.toString().replaceFirst("[.][^.]+$", "");
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

}
