package com.hbbsolution.maid.maid_profile.model.abilities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by buivu on 27/06/2017.
 */

public class AbilitiResponse {
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Ability> abilityList;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Ability> getAbilityList() {
        return abilityList;
    }

    public void setAbilityList(List<Ability> abilityList) {
        this.abilityList = abilityList;
    }
}
