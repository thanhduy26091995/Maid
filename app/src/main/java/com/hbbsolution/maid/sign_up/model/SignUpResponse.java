package com.hbbsolution.maid.sign_up.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by buivu on 21/07/2017.
 */

public class SignUpResponse {
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;

    public boolean isStatus() {
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
}
