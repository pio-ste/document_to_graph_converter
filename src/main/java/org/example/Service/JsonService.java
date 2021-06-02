package org.example.Service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;


public class JsonService {

    public void parseJson(String filePath) throws Exception {
        String json = readFileAsString(filePath);
        System.out.println(json);
        printJsonObject(json);
    }

    public void printJsonObject(String json_str) {

        JSONArray jsonArray = new JSONArray(json_str);
        int length = jsonArray.length();
        for(int i=0; i<length; i++) {

            JSONObject jsonObject = jsonArray.getJSONObject(i);

            Iterator<String> keys = jsonObject.keys();
            System.out.println("Nowy przedmiot");
            while(keys.hasNext()) {
                String key = keys.next();
                jsonConditions(key, jsonObject);
            }
        }
    }

    public void jsonConditions(String key, JSONObject jsonObject){
        if (jsonObject.get(key) instanceof JSONObject) {
            getJsonObject(key, jsonObject);
        } else if (jsonObject.get(key) instanceof JSONArray) {
            getJsonArray(key, jsonObject);
        } else {
            System.out.println("key is = "+key+" ==> value is = "+jsonObject.get(key));
        }
    }

    public void getJsonObject(String key, JSONObject jsonObject){
        System.out.println("Obiekt");
        JSONObject jsonObjectOfObject = jsonObject.getJSONObject(key);
        for (int j=0; j<jsonObjectOfObject.names().length(); j++){
            if((jsonObjectOfObject.get(jsonObjectOfObject.names().getString(j)) instanceof JSONObject) || (jsonObjectOfObject.get(jsonObjectOfObject.names().getString(j)) instanceof JSONArray)) {
                jsonConditions(jsonObjectOfObject.names().getString(j), jsonObjectOfObject);
            } else {
                System.out.println("key is = "+jsonObjectOfObject.names().getString(j)+" ==> value is = "+jsonObjectOfObject.get(jsonObjectOfObject.names().getString(j)));
            }
        }
        System.out.println("Obiekt koniec");
    }

    public void getJsonArray(String key, JSONObject jsonObject){
        System.out.println("Tablica");
        JSONArray jsonArrayOfArray = jsonObject.getJSONArray(key);
        for (int k=0; k<jsonArrayOfArray.length(); k++){
            if (!(jsonArrayOfArray.get(k) instanceof JSONObject)){
                System.out.println("KEY " + key + " WARTOSC  " + jsonArrayOfArray.get(k));
            } else {
                JSONObject jsonObjectOfArray = jsonArrayOfArray.getJSONObject(k);
                Iterator<String> arrayKeys = jsonObjectOfArray.keys();
                System.out.println("Tablica tablicy");
                while (arrayKeys.hasNext()){
                    String keyArray = arrayKeys.next();
                    if((jsonObjectOfArray.get(keyArray) instanceof JSONArray) || (jsonObjectOfArray.get(keyArray) instanceof JSONObject)) {
                        jsonConditions(keyArray, jsonObjectOfArray);
                    } else {
                        System.out.println("key is = "+keyArray+" ==> value is = "+jsonObjectOfArray.get(keyArray));
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
