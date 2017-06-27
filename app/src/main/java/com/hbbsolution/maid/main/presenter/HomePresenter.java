package com.hbbsolution.maid.main.presenter;

import android.util.Log;


import com.hbbsolution.maid.api.ApiClient;
import com.hbbsolution.maid.api.ApiInterface;
import com.hbbsolution.maid.main.model.ResponseRequest;
import com.hbbsolution.maid.main.view.HomeView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tantr on 6/1/2017.
 */

public class HomePresenter {
    private HomeView mHomeView;
    private ApiInterface apiService;

    public HomePresenter(HomeView mHomeView) {
        this.mHomeView = mHomeView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void requestCheckToken() {
        Call<ResponseRequest> responseCall = apiService.checkToken();
        responseCall.enqueue(new Callback<ResponseRequest>() {
            @Override
            public void onResponse(Call<ResponseRequest> call, Response<ResponseRequest> response) {
                Log.d("onResponse", response.code() + "");
                    if(response.code() == 401){
                        mHomeView.responseCheckToken();
                    }
            }

            @Override
            public void onFailure(Call<ResponseRequest> call, Throwable t) {
                Log.d("onFailure", t.toString());
                mHomeView.errorConnectService();
            }
        });
    }
}
