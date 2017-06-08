package com.hbbsolution.maid.home.list_job.view;

import com.hbbsolution.maid.model.task.TaskResponse;

/**
 * Created by buivu on 08/06/2017.
 */

public interface ListJobView {
    void getTaskByWork(TaskResponse taskResponse);

    void getMoreTaskByWork(TaskResponse taskResponse);

    void getError(String error);
}
