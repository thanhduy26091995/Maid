package com.hbbsolution.maid.workmanager.listworkmanager.view;

import com.hbbsolution.maid.workmanager.listworkmanager.model.workmanager.WorkManagerResponse;

/**
 * Created by tantr on 5/10/2017.
 */

public interface WorkManagerView {
    void getInfoJob(WorkManagerResponse mExample);
//    void getInfoJobPending(JobPendingResponse mJobPendingResponse);
    void displayNotifyJobPost(boolean isJobPost);
//    void authenticationFailed();
    void getError();
}
