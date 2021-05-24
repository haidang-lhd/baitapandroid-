package com.example.countryapp.Model;

import java.util.ArrayList;

public class Geonames {
    private ArrayList<CountryInfo> geonames;

    public Geonames(ArrayList<CountryInfo> geonames) {
        this.geonames = geonames;
    }

    public ArrayList<CountryInfo> getGeonames() {
        return geonames;
    }

    public void setGeonames(ArrayList<CountryInfo> geonames) {
        this.geonames = geonames;
    }
}
