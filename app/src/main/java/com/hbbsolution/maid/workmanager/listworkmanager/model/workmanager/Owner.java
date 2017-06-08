package com.hbbsolution.maid.workmanager.listworkmanager.model.workmanager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tantr on 6/7/2017.
 */

public class Owner implements Serializable{

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("evaluation_point")
    @Expose
    private Double evaluationPoint;
    @SerializedName("info")
    @Expose
    private InfoOwner infoOwner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getEvaluationPoint() {
        return evaluationPoint;
    }

    public void setEvaluationPoint(Double evaluationPoint) {
        this.evaluationPoint = evaluationPoint;
    }

    public InfoOwner getInfoOwner() {
        return infoOwner;
    }

    public void setInfoOwner(InfoOwner infoOwner) {
        this.infoOwner = infoOwner;
    }
}
