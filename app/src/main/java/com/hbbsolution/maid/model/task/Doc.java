
package com.hbbsolution.maid.model.task;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Doc {

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
    private Info info;
    @SerializedName("dist")
    @Expose
    private Dist dist;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Doc() {
    }

    /**
     * 
     * @param history
     * @param process
     * @param id
     * @param stakeholders
     * @param dist
     * @param info
     */
    public Doc(String id, Process process, History history, Stakeholders stakeholders, Info info, Dist dist) {
        super();
        this.id = id;
        this.process = process;
        this.history = history;
        this.stakeholders = stakeholders;
        this.info = info;
        this.dist = dist;
    }

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

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public Dist getDist() {
        return dist;
    }

    public void setDist(Dist dist) {
        this.dist = dist;
    }

}
