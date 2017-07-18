package com.hbbsolution.maid.maid_profile.presenter;

import android.util.Log;

import com.hbbsolution.maid.api.ApiClient;
import com.hbbsolution.maid.api.ApiInterface;
import com.hbbsolution.maid.maid_profile.model.abilities.AbilitiResponse;
import com.hbbsolution.maid.maid_profile.model.comment_maid.CommentMaidResponse;
import com.hbbsolution.maid.maid_profile.view.MaidProfileView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by buivu on 27/06/2017.
 */

public class MaidProfilePresenter {
    private MaidProfileView mView;
    private ApiInterface apiService;

    public MaidProfilePresenter(MaidProfileView mView) {
        this.mView = mView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getListComment(String maidId, int page) {
        Call<CommentMaidResponse> call = apiService.getListCommentMaid(maidId, page);
        call.enqueue(new Callback<CommentMaidResponse>() {
            @Override
            public void onResponse(Call<CommentMaidResponse> call, Response<CommentMaidResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        CommentMaidResponse mCommentMaidResponse = response.body();
                        mView.getListCommentMaid(mCommentMaidResponse);
                    } catch (Exception e) {
                        Log.e("exception", e.toString());
                        mView.getMessager(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<CommentMaidResponse> call, Throwable t) {
                Log.e("errors", t.toString());
                mView.connectServerFail();
            }
        });
    }

    public void getMoreListComment(String maidId, int page) {
        Call<CommentMaidResponse> call = apiService.getListCommentMaid(maidId, page);
        call.enqueue(new Callback<CommentMaidResponse>() {
            @Override
            public void onResponse(Call<CommentMaidResponse> call, Response<CommentMaidResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        CommentMaidResponse mCommentMaidResponse = response.body();
                        mView.getMoreListCommentMaid(mCommentMaidResponse);
                    } catch (Exception e) {
                        Log.e("exception", e.toString());
                        mView.getMessager(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<CommentMaidResponse> call, Throwable t) {
                Log.e("errors", t.toString());
                mView.connectServerFail();
            }
        });
    }

    public void getAbilities() {
        Call<AbilitiResponse> abilitiResponseCall = apiService.getAbilities();
        abilitiResponseCall.enqueue(new Callback<AbilitiResponse>() {
            @Override
            public void onResponse(Call<AbilitiResponse> call, Response<AbilitiResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        mView.getAbilities(response.body());
                    } catch (Exception e) {
                        mView.getMessager(e.getMessage());
                    }
                } else {
                    mView.getMessager(response.message());
                }
            }

            @Override
            public void onFailure(Call<AbilitiResponse> call, Throwable t) {
                mView.getMessager(t.getMessage());
            }
        });
    }
}
