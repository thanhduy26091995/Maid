package com.hbbsolution.maid.more.viet_pham.presenter;

import com.hbbsolution.maid.api.ApiClient;
import com.hbbsolution.maid.api.ApiInterface;
import com.hbbsolution.maid.model.announcement.AnnouncementResponse;
import com.hbbsolution.maid.more.viet_pham.MoreForAnnouncementView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by buivu on 29/06/2017.
 */

public class MorePresenter {
    private ApiInterface mApiService;
    private MoreForAnnouncementView mView;

    public MorePresenter(MoreForAnnouncementView mView) {
        this.mView = mView;
        mApiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void onAnnouncement(String deviceToken) {
        Call<AnnouncementResponse> announcementResponseCall = mApiService.onAnnouncement(deviceToken);
        announcementResponseCall.enqueue(new Callback<AnnouncementResponse>() {
            @Override
            public void onResponse(Call<AnnouncementResponse> call, Response<AnnouncementResponse> response) {
                if (response.isSuccessful()) {
                    mView.onAnnouncement(response.body());
                } else {
                    mView.getError(response.message());
                }
            }

            @Override
            public void onFailure(Call<AnnouncementResponse> call, Throwable t) {
                mView.getError(t.getMessage());
            }
        });
    }

    public void offAnnouncement() {
        Call<AnnouncementResponse> announcementResponseCall = mApiService.offAnnouncement();
        announcementResponseCall.enqueue(new Callback<AnnouncementResponse>() {
            @Override
            public void onResponse(Call<AnnouncementResponse> call, Response<AnnouncementResponse> response) {
                if (response.isSuccessful()) {
                    mView.offAnnouncement(response.body());
                } else {
                    mView.getError(response.message());
                }
            }

            @Override
            public void onFailure(Call<AnnouncementResponse> call, Throwable t) {
                mView.getError(t.getMessage());
            }
        });
    }
}
