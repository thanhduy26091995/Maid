package com.hbbsolution.maid.more.viet_pham.model.signin_signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 5/22/2017.
 */

public class User {
    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("info")
    @Expose
    private Info info;
    @SerializedName("work_info")
    @Expose
    private Work_info work_info;


    public User() {

    }

    public User(String _id, Info info, Work_info work_info) {
        this._id = _id;
        this.info = info;
        this.work_info = work_info;
    }

    public Work_info getWork_info() {
        return work_info;
    }

    public void setWork_info(Work_info work_info) {
        this.work_info = work_info;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
