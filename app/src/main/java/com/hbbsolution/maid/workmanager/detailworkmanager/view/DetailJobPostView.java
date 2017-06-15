package com.hbbsolution.maid.workmanager.detailworkmanager.view;


/**
 * Created by tantr on 5/26/2017.
 */

public interface DetailJobPostView {
    void displayNotifyJobPost(boolean isJobPost);
    void displayError(String error);
    void displayNotifyAccceptJobRequested(boolean isJobPost);
    void displayErrorAccceptJobRequested(String error);
    void displayNotifyRefuseJobRequested(boolean isJobPost);
    void displayErrorRefuseJobRequested(String error);
//    void checkIn(CheckInResponse checkInResponse);

//    void checkInFail(String error);
}
