package com.hbbsolution.maid.home.job_near_by.view;

import com.hbbsolution.maid.model.task_around.TaskAroundResponse;

/**
 * Created by buivu on 06/06/2017.
 */

public interface JobNearByView {
    void getAllTask(TaskAroundResponse taskAroundResponse);

    void getError(String error);
}
