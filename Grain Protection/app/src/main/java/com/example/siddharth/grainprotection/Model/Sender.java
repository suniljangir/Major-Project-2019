package com.example.siddharth.grainprotection.Model;

import android.app.Notification;

/**
 * Created by Siddharth on 24-12-2017.
 */

public class Sender
{
    public String to;
    public Notification notification;


    public Sender(String to, Notification notification) {
        this.to = to;
        this.notification = notification;
    }
}
