
package com.hbbsolution.maid.model.task;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stakeholders {

    @SerializedName("owner")
    @Expose
    private Owner owner;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Stakeholders() {
    }

    /**
     * 
     * @param owner
     */
    public Stakeholders(Owner owner) {
        super();
        this.owner = owner;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

}
