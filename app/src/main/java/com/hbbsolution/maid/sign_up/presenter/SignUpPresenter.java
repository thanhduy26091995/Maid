package com.hbbsolution.maid.sign_up.presenter;

import com.hbbsolution.maid.api.ApiClient;
import com.hbbsolution.maid.api.ApiInterface;
import com.hbbsolution.maid.sign_up.model.SignUpResponse;
import com.hbbsolution.maid.sign_up.view.SignUpView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by buivu on 21/07/2017.
 */

public class SignUpPresenter {
    private ApiInterface mApiInterface;
    private SignUpView mView;

    public SignUpPresenter(SignUpView mView) {
        this.mView = mView;
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void signUp(String name, String email, String phone, String note) {
        Call<SignUpResponse> signUpResponseCall = mApiInterface.maidRegister(name, email, phone, note);
        signUpResponseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.isSuccessful()) {
                    mView.getDataResponse(response.body());
                } else {
                    mView.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                mView.onError(t.getMessage());
            }
        });
    }
}
