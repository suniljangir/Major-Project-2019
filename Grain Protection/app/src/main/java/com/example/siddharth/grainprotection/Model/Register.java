package com.example.siddharth.grainprotection.Model;

import java.util.List;

/**
 * Created by Siddharth on 09-01-2018.
 */

public class Register {
    private String phone;
    private String name;
    private String location;
    private List<Register> food;

    public Register() {
    }

    public Register(String phone, String name, String location, List<Register> food) {
        this.phone = phone;
        this.name = name;
        this.location = location;
        this.food = food;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Register> getFood() {
        return food;
    }

    public void setFood(List<Register> food) {
        this.food = food;
    }
}
