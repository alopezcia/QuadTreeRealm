package com.alopezcia.quadtreerealm.models;

import com.alopezcia.quadtreerealm.app.QuadTreeTestApp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class QuadTreeExtent extends RealmObject {
    @PrimaryKey
    private int id;
    private double minLat;
    private double minLng;
    private double maxLat;
    private double maxLng;

    public QuadTreeExtent()
    {
    }

    public QuadTreeExtent(double minlat, double minlng, double maxlat, double maxlng)
    {
        this.id = QuadTreeTestApp.QuadTreeExtentID.incrementAndGet();
        this.maxLat = maxlat;
        this.maxLng = maxlng;
        this.minLat = minlat;
        this.minLng = minlng;
    }

    public double getMinLat() {
        return minLat;
    }

    public double getMinLng() {
        return minLng;
    }

    public double getMaxLat() {
        return maxLat;
    }

    public double getMaxLng() {
        return maxLng;
    }

    public int getId() {
        return id;
    }

    public boolean contains(QuadTreeEntity entity)
    {
        double lat = entity.getLatitude();
        double lng = entity.getLongitude();
        return (this.minLat >= lat  ) && (this.minLng >= lng ) &&
                (this.maxLat <= lat ) && (this.maxLng <= lng );
    }

    public double getHeight()
    {
        return Math.abs(this.maxLat-this.minLat);
    }

    public double getWidth()
    {
        return Math.abs(this.maxLng-this.minLng);
    }

    private double getDistance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit == "K") {
                dist = dist * 1.609344;
            } else if (unit == "N") {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }

    public double getAreaKm2()
    {
        double d = getDistance(this.minLat, this.minLng,this.maxLat, this.getMaxLng(), "K");
        return d*d/2;
    }

    public double getAreaMiles2()
    {
        double d = getDistance(this.minLat, this.minLng,this.maxLat, this.getMaxLng(), "M");
        return d*d/2;
    }

    public double getAreaNauticalMiles2()
    {
        double d = getDistance(this.minLat, this.minLng,this.maxLat, this.getMaxLng(), "N");
        return d*d/2;
    }

}
