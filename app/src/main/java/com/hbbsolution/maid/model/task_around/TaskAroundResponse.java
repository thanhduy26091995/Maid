package com.hbbsolution.maid.model.task_around;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by buivu on 06/06/2017.
 */

public class TaskAroundResponse {
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<TaskData> taskDatas;

    public TaskAroundResponse(boolean status, String message, List<TaskData> taskDatas) {
        this.status = status;
        this.message = message;
        this.taskDatas = taskDatas;
    }

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

    public List<TaskData> getTaskDatas() {
        return taskDatas;
    }

    public void setTaskDatas(List<TaskData> taskDatas) {
        this.taskDatas = taskDatas;
    }
}
