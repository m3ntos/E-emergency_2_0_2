package com.pp.rafix.e_emergency_2_0_2.models;

/**
 * Created by Rafal on 2014-12-13.
 */
public class InjuryTreatmentModel {

    int id;
    String name;

    public InjuryTreatmentModel(int id, String name ) {
        this.name = name;
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InjuryTreatmentModel that = (InjuryTreatmentModel) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
