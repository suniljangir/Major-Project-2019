package com.example.siddharth.grainprotection.Model;

import java.util.List;

import javax.xml.transform.Result;

/**
 * Created by Siddharth on 24-12-2017.
 */

public class MyResponse {

    public long multicast_id;
    public int success;
    public int failure;
    public int cannonical_ids;
    public List<Result> results;


}
