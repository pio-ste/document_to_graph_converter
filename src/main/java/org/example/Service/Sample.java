package org.example.Service;

import javafx.beans.property.SimpleStringProperty;

public class Sample {

    public SimpleStringProperty objectNameFirst;
    public SimpleStringProperty relationName;
    public SimpleStringProperty objectNameSecond;

    public Sample(String objectNameFirst, String relationName, String objectNameSecond) {
        this.objectNameFirst = new SimpleStringProperty(objectNameFirst);
        this.relationName = new SimpleStringProperty(relationName);
        this.objectNameSecond = new SimpleStringProperty(objectNameSecond);
    }

    public String getObjectNameFirst() {
        return objectNameFirst.get();
    }

    public void setObjectNameFirst(String objectNameFirst) {
        this.objectNameFirst = new SimpleStringProperty(objectNameFirst);
    }

    public String getRelationName() {
        return relationName.get();
    }

    public void setRelationName(String relationName) {
        this.relationName = new SimpleStringProperty(relationName);
    }

    public String getObjectNameSecond() {
        return objectNameSecond.get();
    }

    public void setObjectNameSecond(String objectNameSecond) {
        this.objectNameSecond = new SimpleStringProperty(objectNameSecond);
    }
}
