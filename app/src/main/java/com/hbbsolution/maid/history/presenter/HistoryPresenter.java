package com.hbbsolution.maid.history.presenter;

import com.hbbsolution.maid.api.ApiClient;
import com.hbbsolution.maid.api.ApiInterface;
import com.hbbsolution.maid.history.activity.HistoryView;
import com.hbbsolution.maid.model.choose_work.ChooseWorkResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by buivu on 26/06/2017.
 */

public class HistoryPresenter {
    private ApiInterface apiService;
    private HistoryView view;

    public HistoryPresenter(HistoryView view) {
        this.view = view;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void directConfirm(String billId) {
        Call<ChooseWorkResponse> chooseWorkResponseCall = apiService.directConfirm(billId);
        chooseWorkResponseCall.enqueue(new Callback<ChooseWorkResponse>() {
            @Override
            public void onResponse(Call<ChooseWorkResponse> call, Response<ChooseWorkResponse> response) {
                if (response.isSuccessful()) {
                    view.directConfirm(response.body());
                } else {
                    view.getError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ChooseWorkResponse> call, Throwable t) {
                view.getError(t.getMessage());
            }
        });
    }

    public void cancelDirectConfirm(String billId) {
        Call<ChooseWorkResponse> chooseWorkResponseCall = apiService.cancelDirectConfirm(billId);
        chooseWorkResponseCall.enqueue(new Callback<ChooseWorkResponse>() {
            @Override
            public void onResponse(Call<ChooseWorkResponse> call, Response<ChooseWorkResponse> response) {
                if (response.isSuccessful()) {
                    view.cancelDirectConfirm(response.body());
                } else {
                    view.getError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ChooseWorkResponse> call, Throwable t) {
                view.getError(t.getMessage());
            }
        });
    }
}
