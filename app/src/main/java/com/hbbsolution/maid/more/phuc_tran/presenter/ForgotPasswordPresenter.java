package com.hbbsolution.maid.more.phuc_tran.presenter;

import com.hbbsolution.maid.api.ApiClient;
import com.hbbsolution.maid.api.ApiInterface;
import com.hbbsolution.maid.more.phuc_tran.model.ForgotPassResponse;
import com.hbbsolution.maid.more.phuc_tran.view.ForgotPassView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Tư Lầu on 6/26/17.
 */

public class ForgotPasswordPresenter {
    private ForgotPassView mForgotPassView;
    private ApiInterface apiService;

    public ForgotPasswordPresenter(ForgotPassView mForgotPassView) {
        this.mForgotPassView = mForgotPassView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void forgotPassword(String email,String username) {
        Call<ForgotPassResponse> responseCall = apiService.forgotPassword(email,username);
        responseCall.enqueue(new Callback<ForgotPassResponse>() {
            @Override
            public void onResponse(Call<ForgotPassResponse> call, Response<ForgotPassResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        mForgotPassView.getForgotPass(response.body());
                    }
                } catch (Exception e) {
                    mForgotPassView.getErrorForgotPass(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ForgotPassResponse> call, Throwable t) {

                mForgotPassView.getErrorForgotPass(t.toString());
            }
        });
    }
}
