package com.hbbsolution.maid.home.job_near_by_new_version.presenter;

import com.hbbsolution.maid.api.ApiClient;
import com.hbbsolution.maid.api.ApiInterface;
import com.hbbsolution.maid.home.job_near_by_new_version.view.JobNearByNewView;
import com.hbbsolution.maid.model.job.TypeJobResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by buivu on 28/08/2017.
 */

public class JobNearByPresenter {

    private JobNearByNewView mView;
    private ApiInterface mApiInterface;

    public JobNearByPresenter(JobNearByNewView mView) {
        this.mView = mView;
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getAllTypeJob() {
        Call<TypeJobResponse> jobResponseCall = mApiInterface.getAllTypeJob();
        jobResponseCall.enqueue(new Callback<TypeJobResponse>() {
            @Override
            public void onResponse(Call<TypeJobResponse> call, Response<TypeJobResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        mView.getAllTypeJob(response.body());
                    } else {
                        mView.getError(response.body().getMessage());
                    }
                } else {
                    mView.getError(response.message());
                }
            }

            @Override
            public void onFailure(Call<TypeJobResponse> call, Throwable t) {
                mView.getError(t.getMessage());
            }
        });
    }
}
