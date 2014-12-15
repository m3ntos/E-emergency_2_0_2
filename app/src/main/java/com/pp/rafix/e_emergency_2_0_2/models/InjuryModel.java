package com.pp.rafix.e_emergency_2_0_2.models;

import java.util.ArrayList;

/**
 * Created by Rafal on 2014-12-13.
 */
public class InjuryModel {

    int id;
    String name;

    ArrayList<InjuryTreatmentModel> injuryTreatments = new ArrayList<>();

    public InjuryModel(int id, String name) {
        this.id = id;
        this.name = name;
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

    public ArrayList<InjuryTreatmentModel> getInjuryTreatments() {
        return injuryTreatments;
    }

    public void setInjuryTreatments(ArrayList<InjuryTreatmentModel> injuryTreatments) {
        this.injuryTreatments = injuryTreatments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InjuryModel that = (InjuryModel) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
