package com.hbbsolution.maid.workmanager.detailworkmanager.presenter;

import android.util.Log;

import com.hbbsolution.maid.api.ApiClient;
import com.hbbsolution.maid.api.ApiInterface;
import com.hbbsolution.maid.workmanager.detailworkmanager.model.JobPendingResponse;
import com.hbbsolution.maid.workmanager.detailworkmanager.view.DetailJobPostView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tantr on 5/26/2017.
 */

public class DetailJobPendingPresenter {

    private DetailJobPostView mDetailJobPostView;
    private ApiInterface apiService;

    public DetailJobPendingPresenter(DetailJobPostView mDetailJobPostView) {
        this.mDetailJobPostView = mDetailJobPostView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void accceptJobRequested(String idTask, String ownerId) {
        Call<JobPendingResponse> responseCall = apiService.accceptJobRequested(idTask, ownerId);
        responseCall.enqueue(new Callback<JobPendingResponse>() {
            @Override
            public void onResponse(Call<JobPendingResponse> call, Response<JobPendingResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Boolean isJbPost = response.body().getStatus();
                        mDetailJobPostView.displayNotifyAccceptJobRequested(isJbPost);
                        Log.d("MESSAGE", response.body().getMessage());
                    }
                    Log.d("ERROR", response.message());
                } catch (Exception e) {
                }

            }

            @Override
            public void onFailure(Call<JobPendingResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
            }
        });
    }

    public void refuseJobRequested(String idTask, String ownerId) {
        Call<JobPendingResponse> responseCall = apiService.refuseJobRequested(idTask, ownerId);
        responseCall.enqueue(new Callback<JobPendingResponse>() {
            @Override
            public void onResponse(Call<JobPendingResponse> call, Response<JobPendingResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Boolean isJbPost = response.body().getStatus();
                        mDetailJobPostView.displayNotifyRefuseJobRequested(isJbPost);
                    }
                } catch (Exception e) {
                }

            }

            @Override
            public void onFailure(Call<JobPendingResponse> call, Throwable t) {
            }
        });
    }

    public void deleteJob(String idTask, String ownerId) {

        Call<JobPendingResponse> responseCall = apiService.deleteJob(idTask, ownerId);
        responseCall.enqueue(new Callback<JobPendingResponse>() {
            @Override
            public void onResponse(Call<JobPendingResponse> call, Response<JobPendingResponse> response) {
                try {
                    if (response.isSuccessful()) {

                        Boolean isJbPost = response.body().getStatus();
                        mDetailJobPostView.displayNotifyJobPost(isJbPost);
                    }
                } catch (Exception e) {
                }

            }

            @Override
            public void onFailure(Call<JobPendingResponse> call, Throwable t) {
            }
        });
    }

//    public void checkIn(String filePath, String ownerId, String taskId) {
//        MultipartBody.Part fileToUpload;
//        if (filePath.equals("")) {
//            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "");
//            fileToUpload = MultipartBody.Part.create(requestBody);
//        } else {
//            File file = new File(filePath);
//            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//            fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
//        }
//
//        RequestBody requestOwnerId = RequestBody.create(MediaType.parse("multipart/form-data"), ownerId);
//        RequestBody requestTaskId = RequestBody.create(MediaType.parse("multipart/form-data"), taskId);
//
//        Call<CheckInResponse> checkInResponseCall = apiService.checkIn(fileToUpload, requestOwnerId, requestTaskId);
//        checkInResponseCall.enqueue(new Callback<CheckInResponse>() {
//            @Override
//            public void onResponse(Call<CheckInResponse> call, Response<CheckInResponse> response) {
//                if (response.isSuccessful()) {
//                    mDetailJobPostView.checkIn(response.body());
//                } else {
//                    mDetailJobPostView.checkInFail(response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CheckInResponse> call, Throwable t) {
//                mDetailJobPostView.checkInFail(t.getMessage());
//            }
//        });
//    }
}
