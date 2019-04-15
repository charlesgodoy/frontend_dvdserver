package com.yodo.caz.myapplication;

import org.json.JSONException;
import org.json.JSONObject;

public class Dvd {

    private long dvdid;

    private String dvdtitle;

    private String description;

    private double price;


    public Dvd() {
    }

    public Dvd(JSONObject json) {
        try {
            this.dvdid = json.getLong("dvdid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.dvdtitle = json.getString("dvdtitle");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.description = json.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.price = json.getDouble("price");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public long getDvdid() {
        return dvdid;
    }

    public void setDvdid(long dvdid) {
        this.dvdid = dvdid;
    }

    public String getDvdtitle() {
        return dvdtitle;
    }

    public void setDvdtitle(String dvdtitle) {
        this.dvdtitle = dvdtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
