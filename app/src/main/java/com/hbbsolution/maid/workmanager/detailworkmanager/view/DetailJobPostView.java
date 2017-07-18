package com.hbbsolution.maid.workmanager.detailworkmanager.view;


import com.hbbsolution.maid.base.ConnectionInterface;
import com.hbbsolution.maid.workmanager.detailworkmanager.model.JobPendingResponse;

/**
 * Created by tantr on 5/26/2017.
 */

public interface DetailJobPostView extends ConnectionInterface{
    void displayNotifyJobPost(JobPendingResponse isJobPost);
    void displayError(String error);
    void displayNotifyAccceptJobRequested(JobPendingResponse isJobPost);
    void displayErrorAccceptJobRequested(String error);
    void displayNotifyRefuseJobRequested(JobPendingResponse isJobPost);
    void displayErrorRefuseJobRequested(String error);
//    void checkIn(CheckInResponse checkInResponse);

//    void checkInFail(String error);
}
