
package com.hbbsolution.maid.model.task;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Owner {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("info")
    @Expose
    private Info info;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Owner() {
    }

    /**
     * 
     * @param id
     * @param info
     */
    public Owner(String id, Info info) {
        super();
        this.id = id;
        this.info = info;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

}
