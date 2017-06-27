package com.hbbsolution.maid.more.viet_pham.presenter;


import android.util.Log;

import com.hbbsolution.maid.api.ApiClient;
import com.hbbsolution.maid.api.ApiInterface;
import com.hbbsolution.maid.more.viet_pham.MoreView;
import com.hbbsolution.maid.more.viet_pham.model.signin_signup.BodyResponse;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 5/25/2017.
 */

public class SignInPresenter {
    private MoreView mMoreView;
    private ApiInterface mApiService;

    public SignInPresenter(MoreView mMoreView) {
        this.mMoreView = mMoreView;
        mApiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void signIn(String username, String password, String deviceToken) {
        RequestBody requestBodyUserName = RequestBody.create(MediaType.parse("text"), username);
        RequestBody requestBodyPassword = RequestBody.create(MediaType.parse("text"), password);
        RequestBody requestBodyDeviceToken = RequestBody.create(MediaType.parse("text"), deviceToken);
        mApiService.signInAccount(requestBodyUserName, requestBodyPassword, requestBodyDeviceToken).enqueue(new Callback<BodyResponse>() {
            @Override
            public void onResponse(Call<BodyResponse> call, Response<BodyResponse> response) {
                try {
                    BodyResponse bodyResponse = response.body();
                    Log.e("Check", String.valueOf(bodyResponse.getStatus()));
                    mMoreView.displaySignUpAndSignIn(bodyResponse);

                } catch (Exception e) {
                    Log.e("Check","error");
                    mMoreView.displayError();
                }
            }

            @Override
            public void onFailure(Call<BodyResponse> call, Throwable t) {
                Log.e("Check","failed");
            }
        });
    }
}
