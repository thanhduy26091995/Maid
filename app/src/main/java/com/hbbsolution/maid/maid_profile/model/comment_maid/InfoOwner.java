package com.hbbsolution.maid.maid_profile.model.comment_maid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hbbsolution.maid.history.model.work.Address;

/**
 * Created by tantr on 5/22/2017.
 */

public class InfoOwner {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("gender")
    @Expose
    private Integer gender;
    @SerializedName("address")
    @Expose
    private Address address;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @SerializedName("image")
    @Expose
    private String image;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
