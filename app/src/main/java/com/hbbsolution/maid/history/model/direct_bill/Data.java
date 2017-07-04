package com.hbbsolution.maid.history.model.direct_bill;

import com.google.gson.annotations.SerializedName;

/**
 * Created by buivu on 04/07/2017.
 */

public class Data {
    @SerializedName("_id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
