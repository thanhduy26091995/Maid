package com.hbbsolution.maid.more.viet_pham.model.signin_signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 6/23/2017.
 */

public class Work_info {
//    @SerializedName("ability")
//    @Expose
//    private List<String> ability;
    @SerializedName("evaluation_point")
    @Expose
    private int evaluation_point;
    @SerializedName("price")
    @Expose
    private int price;

    public Work_info()
    {}
    public Work_info(List<String> ability, int evaluation_point, int price) {
//        this.ability = ability;
        this.evaluation_point = evaluation_point;
        this.price = price;
    }


//    public List<String> getAbility() {
//        return ability;
//    }
//
//    public void setAbility(List<String> ability) {
//        this.ability = ability;
//    }

    public int getEvaluation_point() {
        return evaluation_point;
    }

    public void setEvaluation_point(int evaluation_point) {
        this.evaluation_point = evaluation_point;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
