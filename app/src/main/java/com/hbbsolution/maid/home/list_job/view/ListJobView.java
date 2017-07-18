package com.hbbsolution.maid.home.list_job.view;

import com.hbbsolution.maid.base.ConnectionInterface;
import com.hbbsolution.maid.model.task.TaskResponse;

/**
 * Created by buivu on 08/06/2017.
 */

public interface ListJobView extends ConnectionInterface{
    void getTaskByWork(TaskResponse taskResponse);

    void getMoreTaskByWork(TaskResponse taskResponse);

    void getError(String error);
}
