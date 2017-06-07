package com.hbbsolution.maid.workmanager.listworkmanager.model.workmanager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tantr on 6/7/2017.
 */

public class Owner {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("evaluation_point")
    @Expose
    private Double evaluationPoint;
    @SerializedName("info")
    @Expose
    private Info info;

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

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
