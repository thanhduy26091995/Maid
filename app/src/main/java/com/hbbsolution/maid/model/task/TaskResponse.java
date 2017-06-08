
package com.hbbsolution.maid.model.task;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaskResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("taskData")
    @Expose
    private TaskData taskData;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TaskResponse() {
    }

    /**
     * 
     * @param message
     * @param status
     * @param taskData
     */
    public TaskResponse(Boolean status, String message, TaskData taskData) {
        super();
        this.status = status;
        this.message = message;
        this.taskData = taskData;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TaskData getTaskData() {
        return taskData;
    }

    public void setTaskData(TaskData taskData) {
        this.taskData = taskData;
    }

}
