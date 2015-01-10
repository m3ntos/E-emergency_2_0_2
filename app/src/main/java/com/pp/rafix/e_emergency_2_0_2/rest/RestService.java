package com.pp.rafix.e_emergency_2_0_2.rest;

import com.pp.rafix.e_emergency_2_0_2.models.PatientModel;
import com.pp.rafix.e_emergency_2_0_2.models.SolrModel;
import com.pp.rafix.e_emergency_2_0_2.models.TeamModel;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Rafix on 2014-12-18.
 */
public interface RestService {

    @POST("/patientdata")
    public void getPatientData( @Body PatientModel patient, Callback<PatientModel> cb);

    @POST("/teams")
    public ArrayList<TeamModel> getTeams();

    @POST("/sors")
    public ArrayList<SolrModel> getSors();

    @POST("/sendpatientdata")
    public void sandPatientData( @Body PatientModel patient, Callback<PatientModel> cb);

}