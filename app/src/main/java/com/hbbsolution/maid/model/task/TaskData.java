
package com.hbbsolution.maid.model.task;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TaskData implements Serializable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("process")
    @Expose
    private Process process;
    @SerializedName("history")
    @Expose
    private History history;
    @SerializedName("stakeholders")
    @Expose
    private Stakeholders stakeholders;
    @SerializedName("info")
    @Expose
    private Info_ info;
    @SerializedName("dist")
    @Expose
    private Dist dist;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public Stakeholders getStakeholders() {
        return stakeholders;
    }

    public void setStakeholders(Stakeholders stakeholders) {
        this.stakeholders = stakeholders;
    }

    public Info_ getInfo() {
        return info;
    }

    public void setInfo(Info_ info) {
        this.info = info;
    }

    public Dist getDist() {
        if (this.dist == null) {
            dist = new Dist(0.0);
        }
        return dist;
    }

    public void setDist(Dist dist) {
        this.dist = dist;
    }

}
