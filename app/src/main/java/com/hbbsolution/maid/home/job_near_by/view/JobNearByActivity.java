package com.hbbsolution.maid.home.job_near_by.view;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.base.InternetConnection;
import com.hbbsolution.maid.home.job_near_by.JobNearByAdapter;
import com.hbbsolution.maid.home.job_near_by.presenter.JobNearByPresenter;
import com.hbbsolution.maid.model.geocodemap.GeoCodeMapResponse;
import com.hbbsolution.maid.model.task_around.TaskAroundResponse;
import com.hbbsolution.maid.model.task_around.TaskData;
import com.hbbsolution.maid.utils.Constants;
import com.hbbsolution.maid.utils.ShowAlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 05/06/2017.
 */

public class JobNearByActivity extends AppCompatActivity implements JobNearByView, View.OnClickListener, LocationListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.seekBar_distance)
    SeekBar seekBarDistance;
    @BindView(R.id.text_distance)
    TextView txtDistance;
    @BindView(R.id.recycler_job)
    RecyclerView mRecycler;
    @BindView(R.id.txt_search)
    TextView txtSearch;
    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(R.id.view)
    View view;

    private Integer maxDistance = 5;
    private JobNearByPresenter presenter;
    private JobNearByAdapter jobNearByAdapter;
    private List<TaskData> taskArounds = new ArrayList<>();
    private ProgressDialog progressDialog;
    private LocationManager locationManager;
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
    private boolean isSearch = false, isFromSignIn = false;
    private String searchText = "", locationName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_near_by);
        ButterKnife.bind(this);
        presenter = new JobNearByPresenter(this);
        //setup toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        //event searchview
        searchView.setQueryHint(getResources().getString(R.string.search_location));
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        //implement searchview
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //hide keyboard
                searchText = query;
                //call apk to search

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchText = newText;
                return false;
            }
        });
        //event change seekbar
        seekBarDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                maxDistance = progress;
                txtDistance.setText(String.format("%d km", progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //load data
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //get intent
        isSearch = getIntent().getBooleanExtra(Constants.IS_SEARCH, false);
        isFromSignIn = getIntent().getBooleanExtra("fromSignIn", false);
        // if (isSearch) {
        latitude = getIntent().getDoubleExtra(Constants.LAT, 0);
        longitude = getIntent().getDoubleExtra(Constants.LNG, 0);
        // Log.d("CLICK2", "" + latitude + "/" + longitude);
        if (isSearch) {
            searchView.setIconified(false);
            searchView.setQuery(getIntent().getStringExtra(Constants.LOCATION_NAME), false);
            searchView.clearFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
        }
        //event click
        txtSearch.setOnClickListener(this);
//check internet
        if (isFromSignIn) {
            loadData();
        } else {
            initData();
        }
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
            locationManager = (LocationManager) this
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
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                    Log.d("LATLNG", "" + location.getLatitude() + "/" + location.getLongitude());
                    showProgress();
                    presenter.getAllTask(location.getLatitude(), location.getLongitude(), maxDistance);
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.activity), getResources().getString(R.string.location_not_found), Snackbar.LENGTH_LONG);
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
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                //TODO:
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                //(just doing it here for now, note that with this code, no explanation is shown)
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_ID_ACCESS_COARSE_FINE_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_ID_ACCESS_COARSE_FINE_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case REQUEST_ID_ACCESS_COARSE_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadData();
                }
                break;
        }

    }

    private void showSettingLocationAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //set title
        builder.setTitle(getResources().getString(R.string.GPSTitle));
        //set message
        builder.setMessage(getResources().getString(R.string.GPSContent));
        //on press
        builder.setPositiveButton(getResources().getString(R.string.setting), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent settingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(settingIntent);
            }
        });
        //on cancel
        builder.setNegativeButton(getResources().getString(R.string.huy), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mGpsSwitchStateReceiver);
        ButterKnife.bind(this).unbind();
        super.onDestroy();
    }


    @Override
    public void getAllTask(TaskAroundResponse taskAroundResponse) {
        taskArounds.clear();
        hideProgress();
        taskArounds = taskAroundResponse.getTaskDatas();
        if (taskArounds.size() > 0) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
        //set up recyclerview
        jobNearByAdapter = new JobNearByAdapter(JobNearByActivity.this, taskArounds, latitude, longitude, maxDistance);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(jobNearByAdapter);
        //jobNearByAdapter.notifyDataSetChanged();
    }

    @Override
    public void getError(String error) {
        view.setVisibility(View.GONE);
        hideProgress();
        Log.d("ERROR_NEAR_BY", error);
    }

    @Override
    public void getLocationAddress(GeoCodeMapResponse geoCodeMapResponse) {
        Double lat = geoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLat();
        Double lng = geoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLng();
        latitude = lat;
        longitude = lng;
        Log.d("LATLNG", "" + lat + "/" + lng + "/" + maxDistance);
        //search
        presenter.getAllTask(lat, lng, maxDistance);
    }

    @Override
    public void displayNotFoundLocation(String error) {
        hideProgress();
        ShowAlertDialog.showAlert(getResources().getString(R.string.address_not_found), JobNearByActivity.this);
    }

    @Override
    public void onClick(View v) {
        if (v == txtSearch) {
            //hide keyboard
            searchView.clearFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
            if (InternetConnection.getInstance().isOnline(JobNearByActivity.this)) {
                showProgress();
                if (searchText.equals("")) {
                    Log.d("LOCATION_NEAR_BY", "" + latitude + "/" + longitude + "/" + maxDistance);
                    presenter.getAllTask(latitude, longitude, maxDistance);
                } else {
                    presenter.getLocationAddress(searchText);
                }
            } else {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.activity), getResources().getString(R.string.location_not_found), Snackbar.LENGTH_LONG);
                snackbar.show();
            }

        }
    }

    private BroadcastReceiver mGpsSwitchStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
                LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (statusOfGPS) {
                    final ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage(getResources().getString(R.string.loading));
                    progressDialog.show();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            loadData();
                            progressDialog.hide();
                        }
                    }, 6000);
                }

            }
        }
    };

    private void initData() {
        //call api
        if (!InternetConnection.getInstance().isOnline(JobNearByActivity.this)) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.activity), getResources().getString(R.string.noInternet), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getResources().getString(R.string.retry), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            initData();
                        }
                    });
            snackbar.show();
        } else {
            showProgress();
            presenter.getAllTask(latitude, longitude, maxDistance);
        }
    }

    @Override
    protected void onResume() {

        registerReceiver(mGpsSwitchStateReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
        super.onResume();
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
        view.setVisibility(View.GONE);
        hideProgress();
        ShowAlertDialog.showAlert(getResources().getString(R.string.connection_error), this);
    }
}
