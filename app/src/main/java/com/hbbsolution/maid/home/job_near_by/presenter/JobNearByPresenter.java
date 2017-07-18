package com.hbbsolution.maid.home.job_near_by.presenter;

import com.hbbsolution.maid.api.ApiClient;
import com.hbbsolution.maid.api.ApiInterface;
import com.hbbsolution.maid.home.job_near_by.view.JobNearByView;
import com.hbbsolution.maid.model.geocodemap.GeoCodeMapResponse;
import com.hbbsolution.maid.model.task_around.TaskAroundResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by buivu on 05/06/2017.
 */

public class JobNearByPresenter {
    private ApiInterface apiInterface;
    private JobNearByView view;

    public JobNearByPresenter(JobNearByView view) {
        this.view = view;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getAllTask(Double lat, Double lng, Integer maxDistance) {
        Call<TaskAroundResponse> responseCall = apiInterface.getAllTask(lat, lng, maxDistance);
        responseCall.enqueue(new Callback<TaskAroundResponse>() {
            @Override
            public void onResponse(Call<TaskAroundResponse> call, Response<TaskAroundResponse> response) {
                if (response.isSuccessful()) {
                    view.getAllTask(response.body());
                } else {
                    view.getError(response.message());
                }
            }

            @Override
            public void onFailure(Call<TaskAroundResponse> call, Throwable t) {
                view.connectServerFail();
            }
        });
    }

    public void getLocationAddress(String addressOfMaid) {
        Call<GeoCodeMapResponse> responseCall = apiInterface.getLocaltionAddress("https://maps.googleapis.com/maps/api/geocode/json", addressOfMaid);
        responseCall.enqueue(new Callback<GeoCodeMapResponse>() {
            @Override
            public void onResponse(Call<GeoCodeMapResponse> call, Response<GeoCodeMapResponse> response) {
                if (response.isSuccessful()) {
                    GeoCodeMapResponse mGeoCodeMapResponse = response.body();
                    if (mGeoCodeMapResponse.getResults().size() > 0) {
                        double lat = mGeoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLat();
                        double lng = mGeoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLng();
                        if (lat != 0 || lng != 0) {
                            view.getLocationAddress(mGeoCodeMapResponse);
                        } else {
                            view.displayNotFoundLocation("Không tìm thấy vị trí");
                        }
                    } else {
                        view.displayNotFoundLocation("Không tìm thấy vị trí");
                    }
                }
            }

            @Override
            public void onFailure(Call<GeoCodeMapResponse> call, Throwable t) {
                view.displayNotFoundLocation("Không tìm thấy vị trí");
            }
        });
    }
}
