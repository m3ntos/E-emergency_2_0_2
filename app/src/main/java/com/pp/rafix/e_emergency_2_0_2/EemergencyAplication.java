package com.pp.rafix.e_emergency_2_0_2;

import android.app.Application;

import com.pp.rafix.e_emergency_2_0_2.models.SolrModel;
import com.pp.rafix.e_emergency_2_0_2.rest.RestClient;

import java.util.ArrayList;

/**
 * Created by Rafal on 2015-01-06.
 */
public class EemergencyAplication extends Application {

    private static RestClient restClient;
    private static ArrayList<SolrModel> sorList;

    @Override
    public void onCreate() {
        super.onCreate();
        restClient = new RestClient();
    }

    public static RestClient getRestClient()
    {
        return restClient;
    }

    public static ArrayList<SolrModel> getSorList() {
        return sorList;
    }

    public static void setSorList(ArrayList<SolrModel> sorList) {
        EemergencyAplication.sorList = sorList;
    }
}
