package com.hbbsolution.maid.home.job_near_by_new_version.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.hbbsolution.maid.R;
import com.hbbsolution.maid.home.job_detail.view.JobDetailActivity;
import com.hbbsolution.maid.home.job_near_by_new_version.InfoWindowRefresh;
import com.hbbsolution.maid.home.job_near_by_new_version.OnInfoWindowElemTouchListener;
import com.hbbsolution.maid.model.task.TaskData;
import com.hbbsolution.maid.utils.SessionManagerUser;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by buivu on 18/05/2017.
 */

public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener {
    private Activity mActivity;
    private HashMap<Marker, TaskData> mMarkerHashMap = new HashMap<>();
    private HashMap<String, Boolean> mMarkerLoadImage = new HashMap<>();
    private OnInfoWindowElemTouchListener chooseMaidListener;
    private GoogleMap googleMap;
    private SessionManagerUser sessionManagerUser;

    public MarkerInfoWindowAdapter(Activity mActivity, HashMap<Marker, TaskData> mMarkerHashMap, HashMap<String, Boolean> mMarkerLoadImage) {
        this.mActivity = mActivity;
        this.mMarkerHashMap = mMarkerHashMap;
        this.mMarkerLoadImage = mMarkerLoadImage;
    }

    public MarkerInfoWindowAdapter(Activity mActivity, HashMap<Marker, TaskData> mMarkerHashMap, HashMap<String, Boolean> mMarkerLoadImage, GoogleMap googleMap) {
        this.mActivity = mActivity;
        this.mMarkerHashMap = mMarkerHashMap;
        this.mMarkerLoadImage = mMarkerLoadImage;
        this.googleMap = googleMap;
        googleMap.setOnInfoWindowClickListener(this);
        sessionManagerUser = new SessionManagerUser(mActivity);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(final Marker marker) {
        final View view = mActivity.getLayoutInflater().inflate(R.layout.custom_marker_layout, null);

        ImageView imgAvatar = (ImageView) view.findViewById(R.id.image_avatar);
        TextView txtName = (TextView) view.findViewById(R.id.text_name);
        // RelativeLayout relaChooseMaid = (RelativeLayout) view.findViewById(R.id.linear_maid_profile);
        //load data
        TaskData mTaskData = mMarkerHashMap.get(marker);
        //kiểm tra, nếu ảnh chưa load kịp thì refresh lại InfoWindow
        boolean isLoadImage = mMarkerLoadImage.get(marker.getId());
        if (isLoadImage) {
            if (!TextUtils.isEmpty(mTaskData.getInfo().getWork().getImage())) {
                Picasso.with(mActivity)
                        .load(mTaskData.getInfo().getWork().getImage())
                        .placeholder(R.drawable.avatar)
                        .error(R.drawable.avatar)
                        .into(imgAvatar);
            }

        } else {
            isLoadImage = true;
            mMarkerLoadImage.put(marker.getId(), isLoadImage);
            if (!TextUtils.isEmpty(mTaskData.getInfo().getWork().getImage())) {
                Picasso.with(mActivity)
                        .load(mTaskData.getInfo().getWork().getImage())
                        .placeholder(R.drawable.avatar)
                        .error(R.drawable.avatar)
                        .into(imgAvatar, new InfoWindowRefresh(marker));
            }
        }
        txtName.setText(mTaskData.getInfo().getTitle());
        // Setting up the infoWindow with current's marker info
        return view;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (sessionManagerUser.isLoggedIn()) {
            TaskData mTaskData = mMarkerHashMap.get(marker);
            Intent intent = new Intent(mActivity, JobDetailActivity.class);
            intent.putExtra("TaskData", mTaskData);
            mActivity.startActivity(intent);
        } else {
            Snackbar snackbar = Snackbar.make(mActivity.findViewById(R.id.activity), mActivity.getResources().getString(R.string.vuilongdangnhap), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
}
