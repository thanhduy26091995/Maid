package com.hbbsolution.maid.workmanager.listworkmanager.presenter;

import android.util.Log;

import com.hbbsolution.maid.api.ApiClient;
import com.hbbsolution.maid.api.ApiInterface;
import com.hbbsolution.maid.workmanager.detailworkmanager.model.JobPendingResponse;
import com.hbbsolution.maid.workmanager.listworkmanager.model.workmanager.WorkManagerResponse;
import com.hbbsolution.maid.workmanager.listworkmanager.view.WorkManagerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tantr on 5/10/2017.
 */

public class WorkManagerPresenter {
    private WorkManagerView mWorkManagerView;
    private ApiInterface apiService;

    public WorkManagerPresenter(WorkManagerView mWorkManagerView){
        this.mWorkManagerView = mWorkManagerView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getInfoWorkList(String process){
        Call<WorkManagerResponse> call = apiService.getInfo(process, true);
        call.enqueue(new Callback<WorkManagerResponse>() {
            @Override
            public void onResponse(Call<WorkManagerResponse> call, Response<WorkManagerResponse> response) {
                Log.d("onResponse",response.code() + "");
                if(response.isSuccessful()){
                    try{
                        WorkManagerResponse workManagerResponse = response.body();
                            mWorkManagerView.getInfoJob(workManagerResponse);
                    }catch (Exception e){
                        mWorkManagerView.getError();
                    }
                }
            }

            @Override
            public void onFailure(Call<WorkManagerResponse> call, Throwable t) {
                mWorkManagerView.getError();
                Log.d("onFailure", t.toString());
            }
        });
    }

//    public void getInfoJobPending(String process){
//        Call<JobPendingResponse> call = apiService.getJobPendingResponse(process);
//        call.enqueue(new Callback<JobPendingResponse>() {
//            @Override
//            public void onResponse(Call<JobPendingResponse> call, Response<JobPendingResponse> response) {
//                Log.e("onResponses", "isonResponse");
//                if(response.isSuccessful()){
//                    try{
//                        JobPendingResponse jobPendingResponse = response.body();
//                        mWorkManagerView.getInfoJobPending(jobPendingResponse);
//                    }catch (Exception e){
//                        Log.e("exception", e.toString());
//                        mWorkManagerView.getError();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JobPendingResponse> call, Throwable t) {
//                Log.e("error", t.toString());
//            }
//        });
//    }
//
    public void deleteJob(String idTask, String ownerId) {

        Call<JobPendingResponse> responseCall = apiService.deleteJob(idTask, ownerId);
        responseCall.enqueue(new Callback<JobPendingResponse>() {
            @Override
            public void onResponse(Call<JobPendingResponse> call, Response<JobPendingResponse> response) {
                try{
                    if (response.isSuccessful()) {

//                        Boolean isJbPost = response.body().getStatus();
                        mWorkManagerView.displayNotifyJobPost(response.body());
                    }
                }catch (Exception e){
                    mWorkManagerView.getError();
                }
            }

            @Override
            public void onFailure(Call<JobPendingResponse> call, Throwable t) {
                mWorkManagerView.getError();
                Log.d("onFailure", t.toString());
            }
        });
    }

}
