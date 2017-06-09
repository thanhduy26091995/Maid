
package com.hbbsolution.maid.history.model.work;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Stakeholders implements Serializable {

    @SerializedName("owner")
    @Expose
    private Owner owner;
    @SerializedName("received")
    @Expose
    private String received;
    @SerializedName("request")
    @Expose
    private List<Object> request = null;

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public List<Object> getRequest() {
        return request;
    }

    public void setRequest(List<Object> request) {
        this.request = request;
    }

}
