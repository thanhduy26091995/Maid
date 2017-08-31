package com.hbbsolution.maid.home.job_near_by_new_version.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.home.job_near_by_new_version.model.FilterModel;
import com.hbbsolution.maid.home.job_near_by_new_version.presenter.ListJobPresenter;
import com.hbbsolution.maid.home.list_job.ListJobAdapter;
import com.hbbsolution.maid.model.task.TaskData;
import com.hbbsolution.maid.model.task.TaskResponse;
import com.hbbsolution.maid.utils.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

import static android.content.Context.LOCATION_SERVICE;
import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * Created by buivu on 25/08/2017.
 */

public class ListJobFragment extends Fragment implements LocationListener, ListJobView {

    private View rootView;
    private RecyclerView mRecycler;
    public static final int REQUEST_ID_ACCESS_COARSE_FINE_LOCATION = 101;
    private static final String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1; // 1 minute
    private Location location; // location
    private Double latitude, longitude;
    private LocationManager locationManager;
    private ListJobPresenter listJobPresenter;
    private ListJobAdapter listJobAdapter;
    private List<TaskData> mTaskDatas = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    private int currentPage = 1;
    private String workId = null;
    private Integer maxDistance = 5;
    private ProgressDialog progressDialog;

    public static ListJobFragment listJobFragment = null;
    private FilterModel filterModel = new FilterModel();
    private int sortType = 1;
    private LinearLayout lnNoData;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list_job, container, false);
        listJobFragment = this;
        mRecycler = (RecyclerView) rootView.findViewById(R.id.recycler_list_job);
        lnNoData = (LinearLayout)rootView.findViewById(R.id.lnNoData) ;
        listJobPresenter = new ListJobPresenter(this);
        listJobAdapter = new ListJobAdapter(getActivity(), mTaskDatas);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        loadData();
        initList();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().registerSticky(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(Integer sortType) {
        this.sortType = sortType;
        showProgressDialog();
        currentPage = 1;
        listJobAdapter = new ListJobAdapter(getActivity(), mTaskDatas);
        listJobPresenter.getTaskByWork(latitude, longitude, maxDistance, workId, currentPage, 10, sortType);
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getActivity().getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideProgessDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public static ListJobFragment getInstance() {
        return listJobFragment;
    }

    private void loadData() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkLocationPermission()) {
                getLocation();
            }
        } else {
            getLocation();
        }
    }

    private void getLocation() {
        try {
            boolean isGPSEnabled = false;
            // flag for network status
            boolean isNetworkEnabled = false;
            boolean canGetLocation = false;

            LocationManager locationManager;
            locationManager = (LocationManager) getActivity()
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    if (checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        //return TODO;
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    }

                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Log.d("LATLNG", "" + location.getLatitude() + "/" + location.getLongitude());
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                Log.d("LATLNG", "" + location.getLatitude() + "/" + location.getLongitude());
                            }
                        }
                    }
                }
                if (location != null) {
                    showProgressDialog();
                    Log.d("LATLNG", "" + location.getLatitude() + "/" + location.getLongitude());
                    listJobPresenter.getTaskByWork(latitude, longitude, maxDistance, workId, 1, 10, sortType);
                } else {
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.activity), getResources().getString(R.string.location_not_found), Snackbar.LENGTH_LONG);
                    snackbar.show();
                    //Toast.makeText(JobNearByActivity.this, "Location not found!", Toast.LENGTH_LONG).show();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // return location;
    }

    private boolean checkLocationPermission() {
        int location = checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (location != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ID_ACCESS_COARSE_FINE_LOCATION);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case REQUEST_ID_ACCESS_COARSE_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadData();
                } else {

                }
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void connectServerFail() {
        hideProgessDialog();
        lnNoData.setVisibility(View.VISIBLE);
//        ShowAlertDialog.showAlert(getResources().getString(R.string.connection_error), getActivity());
    }

    @Override
    public void getTaskByWork(final TaskResponse taskResponse) {
        hideProgessDialog();
        if (taskResponse.getStatus()) {
            mTaskDatas.clear();
            //update google map
            //  ListJobMapFragment.getInstance().updateMap(taskResponse.getData().getTaskDatas());
            if (taskResponse.getData().getTaskDatas().size() == 0) {
                lnNoData.setVisibility(View.VISIBLE);
            } else {
                mTaskDatas.addAll(taskResponse.getData().getTaskDatas());
                lnNoData.setVisibility(View.GONE);
            }
            mRecycler.setLayoutManager(linearLayoutManager);
            mRecycler.setAdapter(listJobAdapter);
            listJobAdapter.notifyDataSetChanged();
            //add scrool listener
            endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    if (currentPage < taskResponse.getData().getPages()) {
                        listJobPresenter.getMoreTaskByWork(latitude, longitude, maxDistance, workId, currentPage + 1, 10, sortType);
                    }
                }
            };
            mRecycler.addOnScrollListener(endlessRecyclerViewScrollListener);
        }
    }

    @Override
    public void getMoreTaskByWork(TaskResponse taskResponse) {
        currentPage++;
        mTaskDatas.addAll(taskResponse.getData().getTaskDatas());
        //update google map
        //ListJobMapFragment.getInstance().updateMap(taskResponse.getData().getTaskDatas());
        listJobAdapter.notifyDataSetChanged();
        mRecycler.post(new Runnable() {
            @Override
            public void run() {
                listJobAdapter.notifyItemRangeInserted(listJobAdapter.getItemCount(), mTaskDatas.size() - 1);
            }
        });
    }

    @Override
    public void getError(String error) {
        hideProgessDialog();
        Log.d("ERROR", error);
    }

    public void updateList(FilterModel filterModel) {
        currentPage = 1;
        latitude = filterModel.getLat();
        longitude = filterModel.getLng();
        workId = filterModel.getWorkId();
        maxDistance = filterModel.getDistance();
        listJobPresenter.getTaskByWork(latitude, longitude, maxDistance, workId, currentPage, 10, sortType);
    }


    private void initList() {
        filterModel.setLat(latitude);
        filterModel.setLng(longitude);
        filterModel.setWorkId(workId);
        filterModel.setWorkName(getResources().getString(R.string.near_by_filter_type_job_all));
        filterModel.setDistance(maxDistance);
        filterModel.setAddressName(getResources().getString(R.string.near_by_filter_current_location));
    }

    public FilterModel getFilterModel() {
        return filterModel;
    }

    public void saveFiterData(FilterModel mFilterModel) {
        filterModel.setLat(mFilterModel.getLat());
        filterModel.setLng(mFilterModel.getLng());
        filterModel.setAddressName(mFilterModel.getAddressName());
        filterModel.setDistance(mFilterModel.getDistance());
        filterModel.setWorkName(mFilterModel.getWorkName());
        filterModel.setWorkId(mFilterModel.getWorkId());
    }

    public List<TaskData> getCurrentTaskData() {
        return mTaskDatas;
    }
}
