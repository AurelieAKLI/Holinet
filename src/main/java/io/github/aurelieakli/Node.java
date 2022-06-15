package io.github.aurelieakli;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private float id;
    private int jdmID;
    private int jdmType;
    private String name;
    private List<Relationship> relationshipProperties;



    public Node(float id, int jdmID, int jdmType, String name){
        this.id=id;
        this.jdmID=jdmID;
        this.jdmType=jdmType;
        this.name=name;
        relationshipProperties= new ArrayList<>();


    }


    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public int getJdmID() {
        return jdmID;
    }

    public void setJdmID(int jdmID) {
        this.jdmID = jdmID;
    }

    public int getJdmType() {
        return jdmType;
    }

    public void setJdmType(int jdmType) {
        this.jdmType = jdmType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Relationship> getRelationshipProperties() {
        return relationshipProperties;
    }

    public void setRelationshipProperties(List<Relationship> relationshipProperties) {
        this.relationshipProperties = relationshipProperties;
    }



}
