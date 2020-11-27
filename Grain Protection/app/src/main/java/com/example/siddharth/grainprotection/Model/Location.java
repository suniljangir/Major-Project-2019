package com.example.siddharth.grainprotection.Model;

import java.util.List;

/**
 * Created by Siddharth on 10-01-2018.
 */

public class Location {
    private String date;
    private String city;
    private List<Location> location;



    public Location(List<Location> l) {
        this.date = date;
        this.city = city;
        this.location=l;
    }

    public Location(String s,List<Location> lo) {
    }


    public List<Location> getLocation() {
        return location;
    }

    public void setLocation(List<Location> location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
