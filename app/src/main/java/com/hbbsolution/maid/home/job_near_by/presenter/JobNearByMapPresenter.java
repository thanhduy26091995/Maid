package com.hbbsolution.maid.home.job_near_by.presenter;

import com.hbbsolution.maid.api.ApiClient;
import com.hbbsolution.maid.api.ApiInterface;
import com.hbbsolution.maid.home.job_near_by.view.JobNearByMapView;
import com.hbbsolution.maid.model.geocodemap.GeoCodeMapResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by buivu on 12/06/2017.
 */

public class JobNearByMapPresenter {
    private ApiInterface mApiInterface;
    private JobNearByMapView mView;

    public JobNearByMapPresenter(JobNearByMapView mView) {
        this.mView = mView;
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getLocationAddress(String addressOfMaid) {
        Call<GeoCodeMapResponse> responseCall = mApiInterface.getLocaltionAddress("https://maps.googleapis.com/maps/api/geocode/json", addressOfMaid);
        responseCall.enqueue(new Callback<GeoCodeMapResponse>() {
            @Override
            public void onResponse(Call<GeoCodeMapResponse> call, Response<GeoCodeMapResponse> response) {
                if (response.isSuccessful()) {
                    GeoCodeMapResponse mGeoCodeMapResponse = response.body();
                    if (mGeoCodeMapResponse.getResults().size() > 0) {
                        double lat = mGeoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLat();
                        double lng = mGeoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLng();
                        if (lat != 0 || lng != 0) {
                            mView.getLocationAddress(mGeoCodeMapResponse);
                        } else {
                            mView.displayNotFoundLocation("Không tìm thấy vị trí");
                        }
                    } else {
                        mView.displayNotFoundLocation("Không tìm thấy vị trí");
                    }
                }
            }

            @Override
            public void onFailure(Call<GeoCodeMapResponse> call, Throwable t) {
                mView.displayNotFoundLocation(t.getMessage());
            }
        });
    }
}
