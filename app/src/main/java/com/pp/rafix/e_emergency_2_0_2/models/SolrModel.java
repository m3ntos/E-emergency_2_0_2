package com.pp.rafix.e_emergency_2_0_2.models;

/**
 * Created by Rafal on 2015-01-10.
 */
public class SolrModel {

    int id;
    String name;

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

    @Override
    public String toString() {
        return name;
    }
}
