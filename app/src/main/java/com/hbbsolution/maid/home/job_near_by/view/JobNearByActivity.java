package com.hbbsolution.maid.home.job_near_by.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.home.job_near_by.JobNearByAdapter;
import com.hbbsolution.maid.home.job_near_by.presenter.JobNearByPresenter;
import com.hbbsolution.maid.model.task_around.TaskAroundResponse;
import com.hbbsolution.maid.model.task_around.TaskData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 05/06/2017.
 */

public class JobNearByActivity extends AppCompatActivity implements JobNearByView, View.OnClickListener {

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

    private Integer maxDistance;
    private JobNearByPresenter presenter;
    private JobNearByAdapter jobNearByAdapter;
    private List<TaskData> taskArounds = new ArrayList<>();
    private ProgressDialog progressDialog;

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
        showProgress();
        presenter.getAllTask(10.76721, 106.6855493, maxDistance);
        //event click
        txtSearch.setOnClickListener(this);


    }

    private void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang tải...");
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
        ButterKnife.bind(this).unbind();
        super.onDestroy();
    }


    @Override
    public void getAllTask(TaskAroundResponse taskAroundResponse) {
        taskArounds.clear();
        hideProgress();
        taskArounds = taskAroundResponse.getTaskDatas();
        //set up recyclerview
        jobNearByAdapter = new JobNearByAdapter(JobNearByActivity.this, taskArounds, 10.76721, 106.6855493, maxDistance);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(jobNearByAdapter);
        //jobNearByAdapter.notifyDataSetChanged();
    }

    @Override
    public void getError(String error) {
        hideProgress();
        Log.d("ERROR_NEAR_BY", error);
    }

    @Override
    public void onClick(View v) {
        if (v == txtSearch) {
            showProgress();
            presenter.getAllTask(10.76721, 106.6855493, maxDistance);
        }
    }
}
