
package com.hbbsolution.maid.model.task;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Time {

    @SerializedName("startAt")
    @Expose
    private String startAt;
    @SerializedName("endAt")
    @Expose
    private String endAt;
    @SerializedName("hour")
    @Expose
    private Integer hour;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Time() {
    }

    /**
     * 
     * @param startAt
     * @param hour
     * @param endAt
     */
    public Time(String startAt, String endAt, Integer hour) {
        super();
        this.startAt = startAt;
        this.endAt = endAt;
        this.hour = hour;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

}
