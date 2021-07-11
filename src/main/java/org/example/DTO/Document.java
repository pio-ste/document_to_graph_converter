package org.example.DTO;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.HashMap;
import java.util.Map;

public class Document {

    private Integer id;
    private String nodeName;
    private String edgeName;
    private Integer joinId;
    private String edgeJoinName;
    private String type;
    private Multimap<String, String> mapOfValues = ArrayListMultimap.create();

    public Document(Integer id, String nodeName, String edgeName, Integer joinId, String edgeJoinName, String type, Multimap<String, String> mapOfValues) {
        this.id = id;
        this.nodeName = nodeName;
        this.edgeName = edgeName;
        this.joinId = joinId;
        this.edgeJoinName = edgeJoinName;
        this.type = type;
        this.mapOfValues = mapOfValues;
    }


    public Document() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getEdgeName() {
        return edgeName;
    }

    public void setEdgeName(String edgeName) {
        this.edgeName = edgeName;
    }

    public Integer getJoinId() {
        return joinId;
    }

    public void setJoinId(Integer joinId) {
        this.joinId = joinId;
    }

    public String getEdgeJoinName() {
        return edgeJoinName;
    }

    public void setEdgeJoinName(String edgeJoinName) {
        this.edgeJoinName = edgeJoinName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Multimap<String, String> getMapOfValues() {
        return mapOfValues;
    }

    public void setMapOfValues(Multimap<String, String> mapOfValues) {
        this.mapOfValues = mapOfValues;
    }
}
