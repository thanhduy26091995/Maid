package com.hbbsolution.maid.base;

import com.hbbsolution.maid.model.job.TypeJob;
import com.hbbsolution.maid.model.job.TypeJobResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        mTypeJobList.addAll(compareValueInModel(configResponse.getData()));
    }

    private List<TypeJob> compareValueInModel(List<TypeJob> list) {
        Collections.sort(list, new Comparator<TypeJob>() {
            public int compare(TypeJob obj1, TypeJob obj2) {
                return Integer.valueOf((int) obj1.getWeight()).compareTo((int) obj2.getWeight()); // To compare integer values
            }
        });
        return list;
    }

    public List<TypeJob> getTypeJob() {
        return mTypeJobList;
    }

}
