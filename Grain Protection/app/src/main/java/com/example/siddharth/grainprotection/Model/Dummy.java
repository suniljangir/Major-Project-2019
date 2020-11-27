package com.example.siddharth.grainprotection.Model;

import java.util.List;

/**
 * Created by Siddharth on 11-01-2018.
 */

public class Dummy {
    private String  near;
    private String store;
    List<Dummy> l;

    public Dummy() {
    }

    public Dummy(String near, String store, List<Dummy> l) {
        this.near = near;
        this.store = store;
        this.l = l;
    }

    public String getNear() {
        return near;
    }

    public void setNear(String near) {
        this.near = near;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public List<Dummy> getL() {
        return l;
    }

    public void setL(List<Dummy> l) {
        this.l = l;
    }
}
