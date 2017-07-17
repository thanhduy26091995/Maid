package com.hbbsolution.maid.maid_profile.model.comment_maid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tantr on 5/22/2017.
 */

public class FromId {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("info")
    @Expose
    private InfoOwner info;


    public InfoOwner getInfo() {
        return info;
    }

    public void setInfo(InfoOwner info) {
        this.info = info;
    }
}
