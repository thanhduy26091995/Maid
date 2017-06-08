
package com.hbbsolution.maid.model.task;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dist {

    @SerializedName("calculated")
    @Expose
    private Double calculated;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Dist() {
    }

    /**
     * 
     * @param calculated
     */
    public Dist(Double calculated) {
        super();
        this.calculated = calculated;
    }

    public Double getCalculated() {
        return calculated;
    }

    public void setCalculated(Double calculated) {
        this.calculated = calculated;
    }

}
