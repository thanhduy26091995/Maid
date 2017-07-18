package com.hbbsolution.maid.history.presenter;

import android.util.Log;

import com.hbbsolution.maid.api.ApiClient;
import com.hbbsolution.maid.api.ApiInterface;
import com.hbbsolution.maid.history.inteface.OwnerHistoryView;
import com.hbbsolution.maid.history.model.owner.OwnerHistoryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 07/06/2017.
 */

public class OwnerHistoryPresenter {
    private OwnerHistoryView mOwnerHistoryView;
    private ApiInterface apiService;

    public OwnerHistoryPresenter(OwnerHistoryView mOwnerHistoryView) {
        this.mOwnerHistoryView = mOwnerHistoryView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getInfoOwnerHistory(String endTime) {
        Call<OwnerHistoryResponse> call = apiService.getInfoOwnerHistory("", endTime);
        call.enqueue(new Callback<OwnerHistoryResponse>() {
            @Override
            public void onResponse(Call<OwnerHistoryResponse> call, Response<OwnerHistoryResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        OwnerHistoryResponse historyOwnerResponse = response.body();
                        mOwnerHistoryView.getInfoOwnerHistory(historyOwnerResponse.getData());
                    } catch (Exception e) {
                        Log.e("exception", e.toString());
                        mOwnerHistoryView.getInfoOwnerHistoryFail();
                    }
                }
            }

            @Override
            public void onFailure(Call<OwnerHistoryResponse> call, Throwable t) {
                mOwnerHistoryView.connectServerFail();
                Log.e("error", t.toString());
            }
        });
    }

    public void getInfoOwnerHistoryTime(String startTime, String endTime) {
        Call<OwnerHistoryResponse> call = apiService.getInfoOwnerHistory(startTime, endTime);
        call.enqueue(new Callback<OwnerHistoryResponse>() {
            @Override
            public void onResponse(Call<OwnerHistoryResponse> call, Response<OwnerHistoryResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        OwnerHistoryResponse historyOwnerResponse = response.body();
                        mOwnerHistoryView.getInfoOwnerHistory(historyOwnerResponse.getData());
                    } catch (Exception e) {
                        Log.e("exception", e.toString());
                        mOwnerHistoryView.getInfoOwnerHistoryFail();
                    }
                }
            }

            @Override
            public void onFailure(Call<OwnerHistoryResponse> call, Throwable t) {
                Log.e("error", t.toString());
                mOwnerHistoryView.connectServerFail();
            }
        });
    }
}
