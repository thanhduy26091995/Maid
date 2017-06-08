package com.hbbsolution.maid.home.list_job.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 05/06/2017.
 */

public class ListJobActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_title)
    TextView txtTitle;

    private Double lat, lng;
    private String workId, workName;
    private Integer maxDistance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_job);
        ButterKnife.bind(this);
        //setup toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        //get intent
        lat = (Double) getIntent().getDoubleExtra(Constants.LAT, 0);
        lng = getIntent().getDoubleExtra(Constants.LNG, 0);
        workId = getIntent().getStringExtra(Constants.WORK_ID);
        workName = getIntent().getStringExtra(Constants.WORK_NAME);
        maxDistance = getIntent().getIntExtra(Constants.MAX_DISTANCE, 0);
        Log.d("TEST", "" + lat + "-" + lng + workId + workName + "/" + maxDistance);

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
        ButterKnife.bind(this).unbind();
        super.onDestroy();
    }
}
