package com.example.countryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.countryapp.Model.CountryInfo;
import com.example.countryapp.Model.Geonames;
import com.example.countryapp.Service.IGeonameService;
import com.example.countryapp.Service.LoadImageTask;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ArrayList <CountryInfo> listCountryInfo;
    String [] listNameCountry;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadDataToSpinner();

    }

    private void loadDataToSpinner(){
        IGeonameService.iGeonameService.listCountryInfo("evel2903").enqueue(new Callback<Geonames>() {
            @Override
            public void onResponse(Call<Geonames> call, Response<Geonames> response) {
                listCountryInfo = ( response.body().getGeonames());
                listNameCountry = new String[listCountryInfo.size()];

                for (int idx = 0; idx < listCountryInfo.size(); idx++) {
                    listNameCountry[idx] = listCountryInfo.get(idx).getCountryName();
                }
                Spinner selectCountry = (Spinner) findViewById(R.id.select_country);
                selectCountry.setOnItemSelectedListener(MainActivity.this);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.dropdown_country_item, listNameCountry);
                selectCountry.setAdapter(arrayAdapter);
            }
            @Override
            public void onFailure(Call<Geonames> call, Throwable t) {
            }
        });
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) view).setTextColor(Color.parseColor("#FF018786"));

        TextView countryName = (TextView) findViewById(R.id.countryName);
        TextView population = (TextView) findViewById(R.id.population);
        TextView areaInSqKm = (TextView) findViewById(R.id.areaInSqKm);
        ImageView ensign = (ImageView) findViewById(R.id.ensign);


        countryName.setText(listCountryInfo.get(position).getCountryName());
        population.setText(listCountryInfo.get(position).getPopulation());
        areaInSqKm.setText(listCountryInfo.get(position).getAreaInSqKm());
        https://img.geonames.org/flags/m/vn.png
        new LoadImageTask(ensign).execute("https://img.geonames.org/flags/x/" + listCountryInfo.get(position).getCountryCode().toLowerCase() + ".gif");

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}