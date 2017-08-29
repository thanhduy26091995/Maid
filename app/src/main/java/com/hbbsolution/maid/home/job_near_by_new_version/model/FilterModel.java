package com.hbbsolution.maid.home.job_near_by_new_version.model;

import java.io.Serializable;

/**
 * Created by buivu on 29/08/2017.
 */

public class FilterModel implements Serializable {
    private Double lat;
    private Double lng;
    private String addressName;
    private String workId;
    private String workName;
    private int distance;

    public FilterModel() {
    }

    public FilterModel(Double lat, Double lng, String addressName, String workId, String workName, int distance) {
        this.lat = lat;
        this.lng = lng;
        this.addressName = addressName;
        this.workId = workId;
        this.workName = workName;
        this.distance = distance;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
