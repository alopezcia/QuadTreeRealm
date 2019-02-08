package com.alopezcia.quadtreerealm.models;

import com.alopezcia.quadtreerealm.app.QuadTreeTestApp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class QuadTreeEntity extends RealmObject {
    @PrimaryKey
    private int id;
    private double latitude;
    private double longitude;
    private int objectid;

    public QuadTreeEntity()
    {

    }

    public QuadTreeEntity(double latitude, double longiude, int objectid)
    {
        this.id = QuadTreeTestApp.QuadTreeEntityID.incrementAndGet();
        this.latitude = latitude;
        this.longitude = longiude;
        this.objectid = objectid;
    }

    public int getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getObjectid() {
        return objectid;
    }

}
