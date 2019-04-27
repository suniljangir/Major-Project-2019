package com.example.siddharth.grainprotection.Model;

import java.util.List;

/**
 * Created by Siddharth on 06-01-2018.
 */

public class Request {

    private String phone;
    private String comment;
    private String location;
    private List<Request> foods;

    public Request(String name, String phone, String s, String toString, List<Request> cart) {
    }

    public Request(String phone, String comment, String location, List<Request> foods) {
        this.phone = phone;
        this.comment = comment;
        this.location = location;
        this.foods = foods;
    }

    public List<Request> getFoods() {
        return foods;
    }

    public void setFoods(List<Request> foods) {
        this.foods = foods;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
