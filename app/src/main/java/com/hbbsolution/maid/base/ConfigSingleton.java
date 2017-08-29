package com.hbbsolution.maid.base;

import com.hbbsolution.maid.model.job.TypeJob;
import com.hbbsolution.maid.model.job.TypeJobResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by buivu on 24/08/2017.
 */

public class ConfigSingleton {
    //instance of class
    private List<TypeJob> mTypeJobList;
    private static ConfigSingleton instance;

    private ConfigSingleton() {
        mTypeJobList = new ArrayList<>();
    }

    public static ConfigSingleton getInstance() {
        if (instance == null) {
            synchronized (ConfigSingleton.class) {
                if (instance == null) {
                    instance = new ConfigSingleton();
                }
            }
        }
        return instance;
    }

    public void saveData(TypeJobResponse configResponse) {
        //clear all data
        mTypeJobList.clear();
        //sort data

        //add new data
        mTypeJobList.addAll(configResponse.getData());
    }

    public List<TypeJob> getTypeJob() {
        return mTypeJobList;
    }

}
