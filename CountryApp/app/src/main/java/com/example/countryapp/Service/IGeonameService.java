package com.example.countryapp.Service;

import com.example.countryapp.Model.CountryInfo;
import com.example.countryapp.Model.Geonames;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IGeonameService {
    String baseURL = "http://api.geonames.org/";

    IGeonameService iGeonameService = new RetrofitClient().getClient(baseURL).create(IGeonameService.class);

    @GET("countryInfoJSON")
    Call <Geonames> listCountryInfo(@Query("username") String username);

}
