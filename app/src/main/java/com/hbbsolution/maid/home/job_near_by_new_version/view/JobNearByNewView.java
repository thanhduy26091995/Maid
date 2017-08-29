package com.hbbsolution.maid.home.job_near_by_new_version.view;

import com.hbbsolution.maid.model.job.TypeJobResponse;

/**
 * Created by buivu on 28/08/2017.
 */

public interface JobNearByNewView {
    void getAllTypeJob(TypeJobResponse typeJobResponse);

    void getError(String error);
}
