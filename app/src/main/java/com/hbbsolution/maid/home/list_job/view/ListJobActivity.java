package com.hbbsolution.maid.home.list_job.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.home.list_job.ListJobAdapter;
import com.hbbsolution.maid.home.list_job.presenter.ListJobPresenter;
import com.hbbsolution.maid.model.task.TaskData;
import com.hbbsolution.maid.model.task.TaskResponse;
import com.hbbsolution.maid.utils.Constants;
import com.hbbsolution.maid.utils.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 05/06/2017.
 */

public class ListJobActivity extends AppCompatActivity implements ListJobView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_title)
    TextView txtTitle;
    @BindView(R.id.recycler_list_job)
    RecyclerView mRecycler;

    private Double lat, lng;
    private String workId, workName;
    private Integer maxDistance;
    private ListJobPresenter presenter;
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    private ProgressDialog progressDialog;
    private ListJobAdapter listJobAdapter;
    private List<TaskData> mTaskDatas = new ArrayList<>();
    private int currentPage = 1;
    private LinearLayoutManager linearLayoutManager;
    public static ListJobActivity listJobActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_job);
        listJobActivity = this;
        ButterKnife.bind(this);
        //init
        linearLayoutManager = new LinearLayoutManager(this);
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
        txtTitle.setText(workName);
        presenter = new ListJobPresenter(this);
        Log.d("TEST", "" + lat + "-" + lng + "/" + workId + workName + "/" + maxDistance);
        showProgress();
        presenter.getTaskByWork(lat, lng, maxDistance, workId, 1);
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(ListJobActivity.this);
        progressDialog.setMessage("Đang tải...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideProgress() {
        if (progressDialog.isShowing() && progressDialog != null) {
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
    public void getTaskByWork(final TaskResponse taskResponse) {
        hideProgress();
        mTaskDatas = taskResponse.getData().getTaskDatas();
        listJobAdapter = new ListJobAdapter(ListJobActivity.this, mTaskDatas);
        mRecycler.setLayoutManager(linearLayoutManager);
        mRecycler.setAdapter(listJobAdapter);
        //add scrool listener
        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (currentPage < taskResponse.getData().getPages()) {
                    presenter.getMoreTaskByWork(lat, lng, maxDistance, workId, currentPage + 1);
                }
            }
        };
        mRecycler.addOnScrollListener(endlessRecyclerViewScrollListener);
    }

    @Override
    public void getMoreTaskByWork(TaskResponse taskResponse) {
        currentPage++;
        mTaskDatas.addAll(taskResponse.getData().getTaskDatas());
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
        Log.d("ERROR", error);
    }
}
