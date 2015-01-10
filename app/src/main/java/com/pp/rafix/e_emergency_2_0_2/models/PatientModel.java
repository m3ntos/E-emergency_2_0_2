package com.pp.rafix.e_emergency_2_0_2.models;

import java.util.ArrayList;

/**
 * Created by Rafal on 2014-12-13.
 */
public class PatientModel {

    private static transient PatientModel instance;

    String firstName;
    String lastName;
    boolean noName;
    String age;
    String sex;
    String phoneNr;
    String PESEL;
    String insuranceNumber;
    boolean agreementOnAsistance;
    String helpDate;
    String helpTime;
    SolrModel DestinationSor;
    boolean arrival = false;
    boolean noTransport = false;

    int teamId;
    String teamName;

    ArrayList<InjuryModel> injuries = new ArrayList<>();

    private PatientModel(){}

    public static PatientModel getInstance(){

        if(instance == null){
            instance = new PatientModel();
        }
        return instance;
    }

    public void addInjury(int injuryId, String injuryName, int treatmentId, String treatmentName){

        InjuryTreatmentModel injuryTreatment = new InjuryTreatmentModel(treatmentId, treatmentName);

        for(InjuryModel injury: injuries){

            if(injury.getId() == injuryId){

                if(!injury.getInjuryTreatments().contains(injuryTreatment)){
                    injury.getInjuryTreatments().add(injuryTreatment);
                    return;
                }
            }
        }

        InjuryModel injury = new InjuryModel(injuryId, injuryName);
        injury.getInjuryTreatments().add(injuryTreatment);
        injuries.add(injury);
    }

    public void removeInjury(int injuryId, String injuryName, int treatmentId, String treatmentName){

        InjuryTreatmentModel injuryTreatment = new InjuryTreatmentModel(treatmentId, treatmentName);

        int index = 0;
        for(int i=0; i<injuries.size(); i++){

            if(injuries.get(i).getId() == injuryId){

                if(injuries.get(i).getInjuryTreatments().contains(injuryTreatment)){
                    injuries.get(i).getInjuryTreatments().remove(injuryTreatment);
                    index = i;
                }
            }
        }

        if (injuries.get(index).getInjuryTreatments().size() == 0 ){
            injuries.remove(index);
        }
    }

    public boolean isInjury(int injuryId, String injuryName, int treatmentId, String treatmentName) {

        InjuryModel injury = new InjuryModel(injuryId, injuryName);
        InjuryTreatmentModel injuryTreatment = new InjuryTreatmentModel(treatmentId, treatmentName);

        return injuries.contains(injury) && injuries.get(injuries.indexOf(injury)).getInjuryTreatments().contains(injuryTreatment);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNoName(boolean noName) {
        this.noName = noName;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    public void setPESEL(String PESEL) {
        this.PESEL = PESEL;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public void setAgreementOnAsistance(boolean agreementOnAsistance) {
        this.agreementOnAsistance = agreementOnAsistance;
    }

    public void setHelpDate(String helpDate) {
        this.helpDate = helpDate;
    }

    public void setHelpTime(String helpTime) {
        this.helpTime = helpTime;
    }

    public void setDestinationSor(SolrModel destinationSor) {
        DestinationSor = destinationSor;
    }

    public void setArrival(boolean arrival) {
        this.arrival = arrival;
    }

    public void setNoTransport(boolean noTransport) {
        this.noTransport = noTransport;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public String getPhoneNr() {
        return phoneNr;
    }

    public String getPESEL() {
        return PESEL;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

}
