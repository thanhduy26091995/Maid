package com.hbbsolution.maid.home.job_detail.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.base.AuthenticationBaseActivity;
import com.hbbsolution.maid.base.ImageLoader;
import com.hbbsolution.maid.base.InternetConnection;
import com.hbbsolution.maid.home.job_detail.presenter.JobDetailPresenter;
import com.hbbsolution.maid.home.list_job.view.ListJobActivity;
import com.hbbsolution.maid.home.owner_profile.view.OwnerProfileActivity;
import com.hbbsolution.maid.model.choose_work.ChooseWorkResponse;
import com.hbbsolution.maid.model.task.TaskData;
import com.hbbsolution.maid.utils.ShowAlertDialog;
import com.hbbsolution.maid.utils.WorkTimeValidate;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 05/06/2017.
 */

public class JobDetailActivity extends AuthenticationBaseActivity implements View.OnClickListener, JobDetailView {


    private TaskData taskData;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_title)
    TextView txtTitle;
    @BindView(R.id.txt_owner_name)
    TextView txtOwnerName;
    @BindView(R.id.txt_owner_address)
    TextView txtOwnerAddress;
    @BindView(R.id.img_avatar)
    ImageView imgAvatar;
    @BindView(R.id.img_job_type)
    ImageView imgJobType;
    @BindView(R.id.txt_job_name)
    TextView txtJobName;
    @BindView(R.id.txt_job_type)
    TextView txtJobType;
    @BindView(R.id.txt_job_description)
    TextView txtJobDescription;
    @BindView(R.id.txt_job_price)
    TextView txtJobPrice;
    @BindView(R.id.txt_date)
    TextView txtDate;
    @BindView(R.id.txt_detail_time)
    TextView txtDetailTime;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.rela_owner_profile)
    RelativeLayout relaOwnerProfile;
    @BindView(R.id.rela_choose_work)
    RelativeLayout relaChooseWork;

    private String date;
    private String startTime, endTime;
    private JobDetailPresenter presenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        ButterKnife.bind(this);
        presenter = new JobDetailPresenter(this);
        //get intent
        taskData = (TaskData) getIntent().getSerializableExtra("TaskData");
        //setup toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        //load data
        loadData();
        //event click
        relaOwnerProfile.setOnClickListener(this);
        relaChooseWork.setOnClickListener(this);
        if (!InternetConnection.getInstance().isOnline(JobDetailActivity.this)) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.activity), getResources().getString(R.string.noInternet), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private void loadData() {
        txtTitle.setText(taskData.getInfo().getWork().getName());
        txtOwnerName.setText(taskData.getStakeholders().getOwner().getInfo().getName());
        txtOwnerAddress.setText(taskData.getStakeholders().getOwner().getInfo().getAddress().getName());
        //load avatar
        ImageLoader.getInstance().loadImageAvatar(JobDetailActivity.this, taskData.getStakeholders().getOwner().getInfo().getImage(),
                imgAvatar);
        ImageLoader.getInstance().loadImageOther(JobDetailActivity.this, taskData.getInfo().getWork().getImage(), imgJobType);
        txtJobName.setText(taskData.getInfo().getTitle());
        txtJobType.setText(taskData.getInfo().getWork().getName());
        txtJobDescription.setText(taskData.getInfo().getDescription());
        txtJobPrice.setText(String.format("%d VND", taskData.getInfo().getPrice()));
        txtAddress.setText(taskData.getInfo().getAddress().getName());

        txtDate.setText(WorkTimeValidate.getDatePostHistory(taskData.getInfo().getTime().getStartAt()));
        txtDetailTime.setText(WorkTimeValidate.getTimeWorkLanguage(JobDetailActivity.this,taskData.getInfo().getTime().getStartAt()) + " - " + WorkTimeValidate.getTimeWorkLanguage(JobDetailActivity.this,taskData.getInfo().getTime().getEndAt()));
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
        if (v == relaOwnerProfile) {
            Intent intent = new Intent(JobDetailActivity.this, OwnerProfileActivity.class);
            intent.putExtra("InfoOwner", taskData.getStakeholders().getOwner());
            intent.putExtra("IsInJobDetail", true);
            startActivity(intent);
        } else if (v == relaChooseWork) {
            if (InternetConnection.getInstance().isOnline(JobDetailActivity.this)) {
                showProgress();
                presenter.chooseWork(taskData.getId());
            } else {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.activity), getResources().getString(R.string.noInternet), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }

    @Override
    public void chooseWork(ChooseWorkResponse chooseWorkResponse) {
        hideProgress();
        boolean status = chooseWorkResponse.isStatus();
        if (status) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.choose_work_success));
            builder.setPositiveButton(getResources().getString(R.string.okAlert), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    builder.create().dismiss();
                    finish();
                    if (ListJobActivity.listJobActivity != null) {
                        ListJobActivity.listJobActivity.finish();
                    }
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            ShowAlertDialog.showAlert(getResources().getString(R.string.choose_work_failed), JobDetailActivity.this);
        }
    }

    @Override
    public void getError(String error) {
        hideProgress();
    }

    @Override
    public void connectServerFail() {
        hideProgress();
        ShowAlertDialog.showAlert(getResources().getString(R.string.connection_error), this);
    }
}
