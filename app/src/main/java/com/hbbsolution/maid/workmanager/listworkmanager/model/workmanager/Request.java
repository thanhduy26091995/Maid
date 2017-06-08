package com.hbbsolution.maid.workmanager.listworkmanager.model.workmanager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tantr on 6/7/2017.
 */

public class Request implements Serializable {
    @SerializedName("maid")
    @Expose
    private String maid;
    @SerializedName("time")
    @Expose
    private String time;

    public String getMaid() {
        return maid;
    }

    public void setMaid(String maid) {
        this.maid = maid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
