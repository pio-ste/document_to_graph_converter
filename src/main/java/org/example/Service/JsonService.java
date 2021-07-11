package org.example.Service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
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
    private List<Integer> listOfID = new ArrayList<>();
    private List<String> listOfObjectName = new ArrayList<>();
    private TreeMultimap<Integer, String> mapOfObjectID1 = TreeMultimap.create();
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
            Multimap<String, String> mapOfKeyValuesDocument = ArrayListMultimap.create();
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            Iterator<String> keys = jsonObject.keys();
            System.out.println("/////////////////Nowy obiekt////////////////////");
            counter ++;
            isArray = true;
            mapOfObjectID.put(counter, fileName);
            while(keys.hasNext()) {
                String key = keys.next();
                jsonConditions(key, jsonObject, mapOfKeyValuesDocument);
            }

            addElementsIntoMap(mapOfKeyValuesDocument, "Object");
            //documentObjects.add(new Document(mapOfObjectID.lastKey(), "", mapOfObjectID.lastEntry().getValue(), 0, "", "Object", mapOfKeyValuesDocument));
            mapOfObjectID.clear();
        }
        System.out.println(documentObjects);
    }

    public void jsonConditions(String key, JSONObject jsonObject, Multimap<String, String> mapOfKeyValuesDocument){
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
        Multimap<String, String> mapOfKeyValuesDocument = ArrayListMultimap.create();
        JSONObject jsonObjectOfObject = jsonObject.getJSONObject(key);
        for (int j=0; j<jsonObjectOfObject.names().length(); j++){
            if((jsonObjectOfObject.get(jsonObjectOfObject.names().getString(j)) instanceof JSONObject) || (jsonObjectOfObject.get(jsonObjectOfObject.names().getString(j)) instanceof JSONArray)) {
                jsonConditions(jsonObjectOfObject.names().getString(j), jsonObjectOfObject, mapOfKeyValuesDocument);
            } else {
                System.out.println("object key is = "+jsonObjectOfObject.names().getString(j)+" ==> value is = "+jsonObjectOfObject.get(jsonObjectOfObject.names().getString(j)));
                mapOfKeyValuesDocument.put(jsonObjectOfObject.names().getString(j), jsonObjectOfObject.get(jsonObjectOfObject.names().getString(j)).toString());
            }
        }
        addElementsIntoMap(mapOfKeyValuesDocument, "Object");
        System.out.println("Obiekt koniec");
    }



    public void getJsonArray(String key, JSONObject jsonObject){
        System.out.println("Tablica");
        JSONArray jsonArrayOfArray = jsonObject.getJSONArray(key);
        Multimap<String, String> mapOfKeyValuesDocument = ArrayListMultimap.create();
        for (int k=0; k<jsonArrayOfArray.length(); k++){
            if (!(jsonArrayOfArray.get(k) instanceof JSONObject)){
                System.out.println("KEY TABLICA " + key + " WARTOSC  " + jsonArrayOfArray.get(k));
                mapOfKeyValuesDocument.put(key, jsonArrayOfArray.get(k).toString());
            } else {
                counter ++;
                mapOfObjectID.put(counter, key);
                isArray = false;
                Multimap<String, String> mapOfKeyValuesDocumentArray = ArrayListMultimap.create();
                JSONObject jsonObjectOfArray = jsonArrayOfArray.getJSONObject(k);
                Iterator<String> arrayKeys = jsonObjectOfArray.keys();
                System.out.println("Nowy obiekt tablicy");
                while (arrayKeys.hasNext()){
                    String keyArray = arrayKeys.next();
                    if((jsonObjectOfArray.get(keyArray) instanceof JSONArray) || (jsonObjectOfArray.get(keyArray) instanceof JSONObject)) {
                        isArray = true;
                        jsonConditions(keyArray, jsonObjectOfArray, mapOfKeyValuesDocumentArray);
                    } else {
                        System.out.println("key Obiekt tablicy = "+keyArray+" ==> value is = "+jsonObjectOfArray.get(keyArray));
                        mapOfKeyValuesDocumentArray.put(keyArray, jsonObjectOfArray.get(keyArray).toString());
                    }
                }
                addElementsIntoMap(mapOfKeyValuesDocumentArray, "ObjectArray");
            }
        }
        if (isArray){
            counter ++;
            mapOfObjectID.put(counter, key);
            addElementsIntoMap(mapOfKeyValuesDocument, "Array");
        }
        System.out.println("Tablica koniec");
        isArray = true;
    }

    public void addElementsIntoMap(Multimap<String, String> mapOfKeyValuesDocument, String type) {
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
        documentObjects.add(new Document(edgeID, null, edgeName, joinEdgeID, joinEdgeName, type, mapOfKeyValuesDocument));
    }

    public String readFileAsString(String filePath) throws Exception
    {
        Path path = Paths.get(filePath);
        Path file = path.getFileName();
        fileName = file.toString().replaceFirst("[.][^.]+$", "");
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

}
