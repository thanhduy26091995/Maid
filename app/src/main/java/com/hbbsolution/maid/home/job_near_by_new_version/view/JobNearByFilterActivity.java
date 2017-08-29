package com.hbbsolution.maid.home.job_near_by_new_version.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.hbbsolution.maid.R;
import com.hbbsolution.maid.base.BaseActivity;
import com.hbbsolution.maid.base.ConfigSingleton;
import com.hbbsolution.maid.home.job_near_by_new_version.model.FilterModel;
import com.hbbsolution.maid.home.job_near_by_new_version.presenter.ListJobPresenter;
import com.hbbsolution.maid.model.Item;
import com.hbbsolution.maid.model.job.TypeJob;
import com.hbbsolution.maid.model.task.TaskData;
import com.hbbsolution.maid.model.task.TaskResponse;
import com.hbbsolution.maid.utils.messagedialog.DialogResulltItem;
import com.hbbsolution.maid.utils.messagedialog.MessageDialogHelper;
import com.hbbsolution.maid.utils.messagedialog.MessageDialogManger;
import com.hbbsolution.maid.utils.messagedialog.OnClickDialogListener;
import com.hbbsolution.maid.utils.messagedialog.TypeMessageDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 25/08/2017.
 */

public class JobNearByFilterActivity extends BaseActivity implements View.OnClickListener, OnClickDialogListener, ListJobView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.relative_location)
    RelativeLayout mRelativeLocation;
    @BindView(R.id.relative_distance)
    RelativeLayout mRelativeDistance;
    @BindView(R.id.relative_type_job)
    RelativeLayout mRelativeTypeJob;
    @BindView(R.id.textView_distance)
    TextView mTextViewDistance;
    @BindView(R.id.textView_location)
    TextView mTextViewLocation;
    @BindView(R.id.textView_type_job)
    TextView mTextViewTypeJob;
    @BindView(R.id.btn_update)
    Button mButtonUpdate;

    private MessageDialogManger mMessageDialogManger;
    private List<Item> mItemDistance, mItemTypeJob;
    private static final int dialogDistance = 100, PLACE_PICKER_REQUEST = 101, dialogTypeJob = 102;
    private Integer mDistance = null;
    private Double mLat = 0.0, mLng = 0.0;
    private String mAddress = null, mTypeJobId, mTypeJobName;
    private ListJobPresenter mListJobPresenter;
    private FilterModel mFilterModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_job);
        ButterKnife.bind(this);
        setupComponents();
    }

    private void setupComponents() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        //init
        mMessageDialogManger = new MessageDialogManger();
        mItemDistance = new ArrayList<>();
        mItemTypeJob = new ArrayList<>();
        mListJobPresenter = new ListJobPresenter(this);
        //init data
        initDataList();
        initData();
        //evemt click
        mRelativeDistance.setOnClickListener(this);
        mRelativeLocation.setOnClickListener(this);
        mRelativeTypeJob.setOnClickListener(this);
        mButtonUpdate.setOnClickListener(this);
    }

    private void initData() {
        mFilterModel = (FilterModel) getIntent().getSerializableExtra("FilterModel");
        mTextViewLocation.setText(mFilterModel.getAddressName());
        mTextViewTypeJob.setText(mFilterModel.getWorkName());
        mTextViewDistance.setText(String.format("%d km", mFilterModel.getDistance()));

        //get data
        mLat = mFilterModel.getLat();
        mLng = mFilterModel.getLng();
        mDistance = mFilterModel.getDistance();
        mTypeJobId = mFilterModel.getWorkId();
        mTypeJobName = mFilterModel.getWorkName();
        mAddress = mFilterModel.getAddressName();
    }

    private void initDataList() {
        for (int i = 0; i <= 99; i++) {
            mItemDistance.add(new Item(String.valueOf(i), String.valueOf(i)));
        }
        //save data type job
        for (TypeJob typeJob : ConfigSingleton.getInstance().getTypeJob()) {
            mItemTypeJob.add(new Item(typeJob.getId(), typeJob.getName()));
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
    public void onClick(View v) {
        if (v == mRelativeDistance) {
            showDialogChooseItem(dialogDistance, getResources().getString(R.string.near_by_filter_distance), mItemDistance);
        } else if (v == mRelativeLocation) {
            mRelativeLocation.setEnabled(false);
            showGooglePlaces();
        } else if (v == mRelativeTypeJob) {
            showDialogChooseItem(dialogTypeJob, getResources().getString(R.string.near_by_filter_type_job), mItemTypeJob);
        } else if (v == mButtonUpdate) {
            mListJobPresenter.getTaskByWork(mLat, mLng, mDistance, mTypeJobId, 1, 10);
        }
    }

    private void showDialogChooseItem(int dialogId, String header, List<Item> mItemList) {
        mMessageDialogManger.onCreate(new MessageDialogHelper.MessageDialogBuilder()
                .setTypeMessageDialog(TypeMessageDialog.MESSAGE_DIALOG_TYPE_CHOOSE_ITEM)
                .setDialogId(dialogId)
                .setTitleDialog(header)
                .setItemList(mItemList)
                .setOnClickDialogListener(this)
                .build(this));
        mMessageDialogManger.onShow();
    }

    private void showGooglePlaces() {
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent myIntent;
            myIntent = builder.build(JobNearByFilterActivity.this);
            startActivityForResult(myIntent, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                mAddress = String.format("%s", place.getAddress());
                mTextViewLocation.setText(mAddress);
                mLat = place.getLatLng().latitude;
                mLng = place.getLatLng().longitude;
            }
            //un-block linear address
            mRelativeLocation.setEnabled(true);
        }

    }

    @Override
    public void onClickDialog(DialogResulltItem dialogResulltItem) {
        switch (dialogResulltItem.getDialogId()) {
            case dialogDistance: {
                if (dialogResulltItem.getObject() != null) {
                    Item item = (Item) dialogResulltItem.getObject();
                    mDistance = Integer.parseInt(item.getId());
                    //show data
                    mTextViewDistance.setText(String.format("%s km", item.getId()));
                }
                mMessageDialogManger.onDimiss();
                break;
            }

            case dialogTypeJob: {
                if (dialogResulltItem.getObject() != null) {
                    Item item = (Item) dialogResulltItem.getObject();
                    mTypeJobId = item.getId();
                    mTypeJobName = item.getTitle();
                    //show data
                    mTextViewTypeJob.setText(item.getTitle());
                }
                mMessageDialogManger.onDimiss();
                break;
            }
        }
    }

    @Override
    public void getTaskByWork(TaskResponse taskResponse) {
        if (taskResponse.getStatus()) {
            //save data
            Intent resultIntent = new Intent();
            resultIntent.putExtra("TaskList", (Serializable) taskResponse.getData().getTaskDatas());
            FilterModel filterModel = new FilterModel(mLat, mLng, mAddress, mTypeJobId, mTypeJobName, mDistance);
            resultIntent.putExtra("FilterModelResult", filterModel);
            setResult(RESULT_OK, resultIntent);
        }
        finish();
    }

    @Override
    public void getMoreTaskByWork(TaskResponse taskResponse) {

    }

    @Override
    public void getError(String error) {
        List<TaskData> taskDatas = new ArrayList<>();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("TaskList", (Serializable) taskDatas);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void connectServerFail() {
        List<TaskData> taskDatas = new ArrayList<>();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("TaskList", (Serializable) taskDatas);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
