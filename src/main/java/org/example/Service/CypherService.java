package org.example.Service;

import org.example.DTO.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class CypherService {

    public void convert(TreeMap<Integer, Document> documentObjects, String path){
        createFile(path);
        writeIntoFile(documentObjects, path);
    }

    private void writeIntoFile(TreeMap<Integer, Document> documentObjects, String path){
        try {
            FileWriter writer = new FileWriter(path, true);
            createNodes(documentObjects, writer);
            createRelations(documentObjects, writer);
            writer.write(";");
            writer.close();
        } catch (IOException e) {
            System.out.println("Błąd podczas zapisywania do pliku.");
            e.printStackTrace();
        }
    }
    private void createNodes(TreeMap<Integer, Document> documentObjects, FileWriter writer) throws IOException {
        for (Map.Entry<Integer, Document> documentEntry : documentObjects.entrySet()) {
            Document document = documentEntry.getValue();
            boolean hasValues = false;
            if(document.getType().equals("Object")){
                StringBuilder queryNode = new StringBuilder("CREATE (" + document.getNodeName() + "_" + document.getId().toString() + ":" + document.getNodeName() + " {");
                for (Map.Entry<Integer, Document> documentArrayEntry : documentObjects.entrySet()){
                    Document documentArray = documentArrayEntry.getValue();
                    if (documentArray.getType().equals("Array")  && documentArray.getJoinId().equals(document.getId())){
                        hasValues = true;
                        queryNode.append(documentArray.getNodeName()).append(":[");
                        for (Map.Entry<String, String> valuesArrayEntry : documentArray.getMapOfNodeValues().entrySet()){
                            queryNode.append(writeNumberOrString(valuesArrayEntry.getValue())).append(", ");
                        }
                        queryNode = new StringBuilder(queryNode.substring(0, queryNode.length() - 2));
                        queryNode.append("], ");
                    }
                }
                if (!document.getMapOfNodeValues().isEmpty()){
                    hasValues = true;
                    for (Map.Entry<String, String> valuesEntry : document.getMapOfNodeValues().entrySet()){
                        queryNode.append(valuesEntry.getKey()).append(":").append(writeNumberOrString(valuesEntry.getValue())).append(", ");
                    }
                }
                queryNode = new StringBuilder(queryNode.substring(0, queryNode.length() - 2));
                if (hasValues){
                    queryNode.append("})");
                } else {
                    queryNode.append(")");
                }
                writer.write(String.valueOf(queryNode));
                writer.append("\n");
            }
        }
    }

    private void createRelations(TreeMap<Integer, Document> documentObjects, FileWriter writer) throws IOException {
        for (Map.Entry<Integer, Document> documentEntry : documentObjects.entrySet()) {
            boolean hasPair = false;
            Document document = documentEntry.getValue();
            if(document.getType().equals("Object")){
                StringBuilder queryRelation = new StringBuilder("CREATE");
                for (Map.Entry<Integer, Document> documentObjectEntry : documentObjects.entrySet()){
                    Document documentRelation = documentObjectEntry.getValue();
                    if(documentRelation.getType().equals("Object") && documentRelation.getJoinId() != null && documentRelation.getRelationName() != null && documentRelation.getJoinId().equals(document.getId())){
                        hasPair = true;
                        queryRelation.append(" (").append(documentRelation.getNodeName()).append("_").append(documentRelation.getId().toString())
                                .append(")-[:").append(documentRelation.getRelationName().toUpperCase(Locale.ROOT));
                        if (documentRelation.getMapOfRelationValues().isEmpty()){
                            queryRelation.append("]->(").append(document.getNodeName()).append("_").append(document.getId().toString()).append("),");
                        } else {
                            queryRelation.append(" {");
                            for (Map.Entry<String, String> valuesEntry : documentRelation.getMapOfRelationValues().entrySet()){
                                queryRelation.append(valuesEntry.getKey()).append(":[").append(writeNumberOrString(valuesEntry.getValue())).append("], ");
                            }
                            queryRelation = new StringBuilder(queryRelation.substring(0, queryRelation.length() - 2));
                            queryRelation.append("}").append("]->(").append(document.getNodeName()).append("_").append(document.getId().toString()).append("),");;
                        }
                    }
                }
                if (hasPair){
                    queryRelation = new StringBuilder(queryRelation.substring(0, queryRelation.length() - 1));
                    writer.append("\n");
                    writer.write(String.valueOf(queryRelation));
                }
            }
        }
    }

    private String writeNumberOrString(String value){
        String partOfQuery = "";
        if (isNumeric(value)){
            partOfQuery = value;
        } else if (!isNumeric(value)){
            partOfQuery = "'"+value+"'";
        }
        return partOfQuery;
    }

    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private void createFile(String path){
        try {
            File file = new File(path);
            if (file.createNewFile()) {
                System.out.println(file.getName());
            } else {
                FileWriter writer = new FileWriter(path);
                writer.write("");
                writer.close();
                System.out.println("Plik już istnieje.");
            }
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas tworzenia pliku.");
            e.printStackTrace();
        }
    }

    public String changePath(String directory, String fileName){
        directory = directory.replace("\\", "/");
        return directory+"/create_"+fileName+".cypher";
    }

    public String readFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}
