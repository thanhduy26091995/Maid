package com.hbbsolution.maid.history.presenter;

import com.hbbsolution.maid.api.ApiClient;
import com.hbbsolution.maid.api.ApiInterface;
import com.hbbsolution.maid.history.activity.DetailWorkView;
import com.hbbsolution.maid.history.model.direct_bill.DirectBillResponse;
import com.hbbsolution.maid.model.choose_work.ChooseWorkResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hbbsolution.maid.R.id.view;

/**
 * Created by buivu on 04/07/2017.
 */

public class DetailWorkPresenter {
    private ApiInterface mApiService;
    private DetailWorkView mView;

    public DetailWorkPresenter(DetailWorkView mView) {
        this.mView = mView;
        mApiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getDirectBill(String taskId) {
        Call<DirectBillResponse> directBillResponseCall = mApiService.getDirectBill(taskId);
        directBillResponseCall.enqueue(new Callback<DirectBillResponse>() {
            @Override
            public void onResponse(Call<DirectBillResponse> call, Response<DirectBillResponse> response) {
                if (response.isSuccessful()) {
                    mView.getDirectBill(response.body());
                } else {
                    mView.getError(response.message());
                }
            }

            @Override
            public void onFailure(Call<DirectBillResponse> call, Throwable t) {
                mView.getError(t.getMessage());
            }
        });
    }

    public void directConfirm(String billId) {
        Call<ChooseWorkResponse> chooseWorkResponseCall = mApiService.directConfirm(billId);
        chooseWorkResponseCall.enqueue(new Callback<ChooseWorkResponse>() {
            @Override
            public void onResponse(Call<ChooseWorkResponse> call, Response<ChooseWorkResponse> response) {
                if (response.isSuccessful()) {
                    mView.directConfirm(response.body());
                } else {
                    mView.getError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ChooseWorkResponse> call, Throwable t) {
                mView.getError(t.getMessage());
            }
        });
    }

    public void cancelDirectConfirm(String billId) {
        Call<ChooseWorkResponse> chooseWorkResponseCall = mApiService.cancelDirectConfirm(billId);
        chooseWorkResponseCall.enqueue(new Callback<ChooseWorkResponse>() {
            @Override
            public void onResponse(Call<ChooseWorkResponse> call, Response<ChooseWorkResponse> response) {
                if (response.isSuccessful()) {
                    mView.cancelDirectConfirm(response.body());
                } else {
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
