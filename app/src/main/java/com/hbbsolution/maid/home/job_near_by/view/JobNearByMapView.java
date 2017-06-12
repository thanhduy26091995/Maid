package com.hbbsolution.maid.home.job_near_by.view;

import com.hbbsolution.maid.model.geocodemap.GeoCodeMapResponse;

/**
 * Created by buivu on 12/06/2017.
 */

public interface JobNearByMapView {
    void getLocationAddress(GeoCodeMapResponse geoCodeMapResponse);

    void displayNotFoundLocation(String error);
}
