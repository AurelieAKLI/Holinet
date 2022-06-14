package io.github.aurelieakli;

public class relationship {
    private float id;
    private int jdmType;
    private int weight;


    public relationship(float id, int jdmType, int weight) {
        this.id = id;
        this.jdmType = jdmType;
        this.weight = weight;
    }

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public int getJdmType() {
        return jdmType;
    }

    public void setJdmType(int jdmType) {
        this.jdmType = jdmType;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
