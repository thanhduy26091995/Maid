package com.hbbsolution.maid.home.job_near_by_new_version.view;

import com.hbbsolution.maid.model.task.TaskResponse;

/**
 * Created by buivu on 28/08/2017.
 */

public interface ListJobView {
    void getTaskByWork(TaskResponse taskResponse);

    void getMoreTaskByWork(TaskResponse taskResponse);

    void getError(String error);

    void connectServerFail();
}
