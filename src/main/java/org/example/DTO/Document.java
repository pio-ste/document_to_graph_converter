package org.example.DTO;

import java.util.LinkedHashMap;

public class Document {

    private Integer id;
    private String relationName;
    private LinkedHashMap<String, String> mapOfRelationValues = new LinkedHashMap <>();
    private String nodeName;
    private Integer joinId;
    private String nodeJoinName;
    private String type;
    private LinkedHashMap<String, String> mapOfNodeValues = new LinkedHashMap <>();

    public Document(Integer id, String relationName, LinkedHashMap<String, String> mapOfRelationValues,  String nodeName, Integer joinId, String nodeJoinName, String type, LinkedHashMap<String, String> mapOfNodeValues) {
        this.id = id;
        this.relationName = relationName;
        this.mapOfRelationValues = mapOfRelationValues;
        this.nodeName = nodeName;
        this.joinId = joinId;
        this.nodeJoinName = nodeJoinName;
        this.type = type;
        this.mapOfNodeValues = mapOfNodeValues;
    }

    public Document(Integer id, String edgeJoinName, String nodeName, String edgeName ) {
        this.id = id;
        this.nodeJoinName = edgeJoinName;
        this.relationName = nodeName;
        this.nodeName = edgeName;
    }

    public Document() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public LinkedHashMap<String, String> getMapOfRelationValues() {
        return mapOfRelationValues;
    }

    public void setMapOfRelationValues(LinkedHashMap<String, String> mapOfRelationValues) {
        this.mapOfRelationValues = mapOfRelationValues;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Integer getJoinId() {
        return joinId;
    }

    public void setJoinId(Integer joinId) {
        this.joinId = joinId;
    }

    public String getNodeJoinName() {
        return nodeJoinName;
    }

    public void setNodeJoinName(String nodeJoinName) {
        this.nodeJoinName = nodeJoinName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LinkedHashMap<String, String> getMapOfNodeValues() {
        return mapOfNodeValues;
    }

    public void setMapOfNodeValues(LinkedHashMap<String, String> mapOfNodeValues) {
        this.mapOfNodeValues = mapOfNodeValues;
    }
}
