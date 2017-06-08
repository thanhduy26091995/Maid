package com.hbbsolution.maid.model.task_around;

import com.google.gson.annotations.SerializedName;

/**
 * Created by buivu on 06/06/2017.
 */

public class TaskData {
    @SerializedName("_id")
    private JobTypeInfo jobTypeInfo;
    @SerializedName("count")
    private int count;

    public TaskData(JobTypeInfo jobTypeInfo, int count) {
        this.jobTypeInfo = jobTypeInfo;
        this.count = count;
    }

    public JobTypeInfo getJobTypeInfo() {
        return jobTypeInfo;
    }

    public void setJobTypeInfo(JobTypeInfo jobTypeInfo) {
        this.jobTypeInfo = jobTypeInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
