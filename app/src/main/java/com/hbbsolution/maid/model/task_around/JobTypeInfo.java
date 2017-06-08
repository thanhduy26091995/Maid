package com.hbbsolution.maid.model.task_around;

import com.google.gson.annotations.SerializedName;

/**
 * Created by buivu on 06/06/2017.
 */

public class JobTypeInfo {
    @SerializedName("_id")
    private String id;
    @SerializedName("image")
    private String image;
    @SerializedName("name")
    private String name;

    public JobTypeInfo(String id, String image, String name) {
        this.id = id;
        this.image = image;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
