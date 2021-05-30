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
        /*InputStream inputStream = new FileInputStream(filePath);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readValue(inputStream, JsonNode.class);
        String jsonString = objectMapper.writeValueAsString(jsonNode);
        System.out.println(jsonString);*/

        printJsonObject(json);

    }

    public static void printJsonObject(String json_str) {

        JSONArray jsonArray = new JSONArray(json_str);
        int length = jsonArray.length();

        for(int i=0; i<length; i++) {

            JSONObject jsonObject = jsonArray.getJSONObject(i);

            Iterator<String> keys = jsonObject.keys();
            System.out.println("Nowy przedmiot");
            while(keys.hasNext()) {
                String key = keys.next();
                if (jsonObject.get(key) instanceof JSONObject) {
                    System.out.println("Obiekt");
                    JSONObject jsonObject2 = jsonObject.getJSONObject(key);
                    System.out.println("Obiekt nowy");
                    for (int j=0; j<jsonObject2.names().length(); j++){
                        /*JSONObject jsonObjectOfObject = jsonObject2.getJSONObject(j);
                        Iterator<String> objectKeys = jsonObjectOfObject.keys();*/

                        System.out.println("key is = "+jsonObject2.names().getString(j)+" ==> value is = "+jsonObject2.get(jsonObject2.names().getString(j)));
                        /*while(objectKeys.hasNext()) {
                            String keyObject = objectKeys.next();
                            System.out.println("key is = "+keyObject+" ==> value is = "+jsonObject2.get(keyObject));
                            //System.out.println("-------------------------");
                            //System.out.println("key is = "+jsonObject2.names().getString(k)+" ==> value is = "+jsonObject2.get(jsonObject2.names().getString(k)));
                        }*/

                    }
                    System.out.println("Obiekt koniec");
                } else if (jsonObject.get(key) instanceof JSONArray) {
                    System.out.println("Tablica");
                    JSONArray jsonArrayOfArray = jsonObject.getJSONArray(key);
                    for (int k=0; k<jsonArrayOfArray.length(); k++){
                        JSONObject jsonObjectOfArray = jsonArrayOfArray.getJSONObject(k);
                        Iterator<String> arrayKeys = jsonObjectOfArray.keys();
                        System.out.println("Tablica nowa");
                        while (arrayKeys.hasNext()){
                            String keyArray = arrayKeys.next();
                            System.out.println("key is = "+keyArray+" ==> value is = "+jsonObjectOfArray.get(keyArray));
                        }
                    }
                    System.out.println("Tablica koniec");
                } else {
                    System.out.println("key is = "+key+" ==> value is = "+jsonObject.get(key));
                }

            }

        }

    }

    public static String readFileAsString(String file)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

}
