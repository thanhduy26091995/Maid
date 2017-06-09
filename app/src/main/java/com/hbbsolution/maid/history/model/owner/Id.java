
package com.hbbsolution.maid.history.model.owner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hbbsolution.maid.history.model.work.InfoOwner;

import java.io.Serializable;

public class Id implements Serializable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("info")
    @Expose
    private InfoOwner info;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InfoOwner getInfo() {
        return info;
    }

    public void setInfo(InfoOwner info) {
        this.info = info;
    }

}
