
package com.hbbsolution.maid.model.task;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Dist implements Serializable{

    @SerializedName("calculated")
    @Expose
    private Double calculated;

    public Double getCalculated() {
        return calculated;
    }

    public void setCalculated(Double calculated) {
        this.calculated = calculated;
    }

    public Dist(Double calculated) {
        this.calculated = calculated;
    }
}
