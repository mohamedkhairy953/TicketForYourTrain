package com.example.moham.ticketyourtrain;

/**
 * Created by moham on 5/14/2016.
 */
public class CitiesModel {
    private int id;
    private String name;
    private int value;

    public CitiesModel(String name,int value){
        this.name=name;
        this.value=value;
    }
    public CitiesModel(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
