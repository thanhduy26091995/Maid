
package com.hbbsolution.maid.history.model.work;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Owner implements Serializable {

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
