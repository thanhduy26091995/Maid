package com.hbbsolution.maid.workmanager.listworkmanager.model.workmanager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hbbsolution.maid.history.model.work.Owner;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tantr on 5/10/2017.
 */

public class Stakeholders implements Serializable{
    @SerializedName("owner")
    @Expose
    private Owner owner;
    @SerializedName("request")
    @Expose
    private List<Request> request = null;

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<Request> getRequest() {
        return request;
    }

    public void setRequest(List<Request> request) {
        this.request = request;
    }
}
