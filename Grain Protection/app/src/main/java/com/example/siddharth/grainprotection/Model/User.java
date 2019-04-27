package com.example.siddharth.grainprotection.Model;

/**
 * Created by Siddharth on 16-12-2017.
 */

public class User {
    private String Name;
    private String Password;
    private String Location;
    private String Phone;

    public User() {
    }

    public User(String name, String password,String location1) {
        Name = name;
        Password = password;
        Location =location1;
    }


    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
