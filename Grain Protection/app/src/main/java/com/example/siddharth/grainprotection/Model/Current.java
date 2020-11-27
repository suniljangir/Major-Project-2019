package com.example.siddharth.grainprotection.Model;

import java.util.List;

/**
 * Created by Siddharth on 10-01-2018.
 */

public class Current {
    private String cityname;
    private String crop;
    private List<Current>  lo;


    public Current() {
    }

    public Current(String cityname,String crop, List<Current> lo) {
        this.cityname = cityname;
        this.lo = lo;
        this.crop=crop;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getCityname() {
        return cityname;

    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public List<Current> getLo() {
        return lo;
    }

    public void setLo(List<Current> lo) {
        this.lo = lo;
    }
}
