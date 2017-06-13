package com.hbbsolution.maid.home.job_detail.presenter;

import com.hbbsolution.maid.api.ApiClient;
import com.hbbsolution.maid.api.ApiInterface;
import com.hbbsolution.maid.home.job_detail.view.JobDetailView;
import com.hbbsolution.maid.model.choose_work.ChooseWorkResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by buivu on 13/06/2017.
 */

public class JobDetailPresenter {
    private ApiInterface mApiService;
    private JobDetailView mView;

    public JobDetailPresenter(JobDetailView mView) {
        this.mView = mView;
        mApiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void chooseWork(String taskId){
        Call<ChooseWorkResponse> chooseWorkResponseCall = mApiService.chooseWork(taskId);
        chooseWorkResponseCall.enqueue(new Callback<ChooseWorkResponse>() {
            @Override
            public void onResponse(Call<ChooseWorkResponse> call, Response<ChooseWorkResponse> response) {
                if (response.isSuccessful()) {
                    mView.chooseWork(response.body());
                }
                else {
                    mView.getError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ChooseWorkResponse> call, Throwable t) {
                mView.getError(t.getMessage());
            }
        });
    }
}
