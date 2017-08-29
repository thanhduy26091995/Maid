package com.hbbsolution.maid.home.job_near_by_new_version.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hbbsolution.maid.R;
import com.hbbsolution.maid.home.job_near_by_new_version.adapter.MarkerInfoWindowAdapter;
import com.hbbsolution.maid.model.task.TaskData;

import java.util.HashMap;
import java.util.List;

/**
 * Created by buivu on 25/08/2017.
 */

public class ListJobMapFragment extends Fragment implements OnMapReadyCallback {

    private View rootView;
    private GoogleMap googleMap;
    private MapView mMapView;
    // private List<TaskData> mTaskDataList = new ArrayList<>();
    private HashMap<Marker, TaskData> myMarkerHashMap = new HashMap<>();
    private HashMap<String, Boolean> markerLoadImage = new HashMap<>();

    public static ListJobMapFragment listJobMapFragment = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list_job_map, container, false);
        listJobMapFragment = this;
        return rootView;
    }

    public void updateMap(List<TaskData> taskDatas) {
        updateMap(googleMap, taskDatas);
    }

    public static ListJobMapFragment getInstance() {
        return listJobMapFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mMapView = (MapView) rootView.findViewById(R.id.map);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (googleMap != null) {
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    marker.showInfoWindow();
                    marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                    return true;
                }
            });
        }

        updateMap(googleMap, ListJobFragment.getInstance().getCurrentTaskData());
    }

    private void updateMap(GoogleMap googleMap, List<TaskData> mTaskData) {
        googleMap.clear();
        int countLengthMaid = 0;
        for (TaskData taskData : mTaskData) {
            if (countLengthMaid == 0) {
                double lat = taskData.getInfo().getAddress().getCoordinates().getLat();
                Double lng = taskData.getInfo().getAddress().getCoordinates().getLng();
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15));
            }
            countLengthMaid++;
            double lat = taskData.getInfo().getAddress().getCoordinates().getLat();
            Double lng = taskData.getInfo().getAddress().getCoordinates().getLng();
            MarkerOptions markerOptions = new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).position(new LatLng(lat, lng));
            Marker currentMarker = googleMap.addMarker(markerOptions);
            myMarkerHashMap.put(currentMarker, taskData);
            markerLoadImage.put(currentMarker.getId(), false);
            googleMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter(getActivity(), myMarkerHashMap, markerLoadImage, googleMap));
        }
    }
}
