package com.hbbsolution.maid.home.list_job.presenter;

import com.hbbsolution.maid.api.ApiClient;
import com.hbbsolution.maid.api.ApiInterface;
import com.hbbsolution.maid.home.list_job.view.ListJobView;
import com.hbbsolution.maid.model.task.TaskResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by buivu on 08/06/2017.
 */

public class ListJobPresenter {
    private ApiInterface apiInterface;
    private ListJobView view;

    public ListJobPresenter(ListJobView view) {
        this.view = view;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getTaskByWork(Double lat, Double lng, Integer maxDistance, String workId, Integer page, Integer limit) {
        Call<TaskResponse> responseCall = apiInterface.getTaskByWork(lat, lng, maxDistance, workId, page, limit,1);
        responseCall.enqueue(new Callback<TaskResponse>() {
            @Override
            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                if (response.isSuccessful()) {
                    view.getTaskByWork(response.body());
                } else {
                    view.getError(response.message());
                }
            }

            @Override
            public void onFailure(Call<TaskResponse> call, Throwable t) {
                view.connectServerFail();
            }
        });
    }

    public void getMoreTaskByWork(Double lat, Double lng, Integer maxDistance, String workId, Integer page, Integer limit) {
        Call<TaskResponse> responseCall = apiInterface.getTaskByWork(lat, lng, maxDistance, workId, page, limit,1);
        responseCall.enqueue(new Callback<TaskResponse>() {
            @Override
            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                if (response.isSuccessful()) {
                    view.getTaskByWork(response.body());
                } else {
                    view.getError(response.message());
                }
            }

            @Override
            public void onFailure(Call<TaskResponse> call, Throwable t) {
                view.connectServerFail();
            }
        });
    }
}
