package com.pp.rafix.e_emergency_2_0_2.rest;

/**
 * Created by Rafal on 2015-01-06.
 */

import retrofit.RestAdapter;

/**
 * Created by Rafal on 2015-01-06.
 */
public class RestClient {

    private static final String BASE_URL = "http://dev.noblesoft.pl:8080";
    private RestService apiService;

    public RestClient()
    {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .build();

        apiService = restAdapter.create(RestService.class);
    }

    public RestService getRestService()
    {
        return apiService;
    }
}
