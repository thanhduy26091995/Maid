package com.hbbsolution.maid.workmanager.detailworkmanager.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hbbsolution.maid.R;
import com.hbbsolution.maid.base.AuthenticationBaseActivity;
import com.hbbsolution.maid.base.BaseActivity;
import com.hbbsolution.maid.home.owner_profile.view.OwnerProfileActivity;
import com.hbbsolution.maid.utils.ShowAlertDialog;
import com.hbbsolution.maid.utils.WorkTimeValidate;
import com.hbbsolution.maid.workmanager.detailworkmanager.model.JobPendingResponse;
import com.hbbsolution.maid.workmanager.detailworkmanager.presenter.DetailJobPendingPresenter;
import com.hbbsolution.maid.workmanager.listworkmanager.model.workmanager.Datum;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by tantr on 6/6/2017.
 */

public class DetailJobPostActivity extends AuthenticationBaseActivity implements DetailJobPostView, View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lo_infoOwner)
    RelativeLayout lo_infoOwner;
    @BindView(R.id.img_avatarQwner)
    ImageView img_avatarQwner;
    @BindView(R.id.txtNameOwner)
    TextView txtNameOwner;
    @BindView(R.id.txtAddressOwner)
    TextView txtAddressOwner;
    @BindView(R.id.txtTitle_job_detail_post)
    TextView txtTitle_job_detail_post;
    @BindView(R.id.txtType_job_detail_post)
    TextView txtType_job_detail_post;
    @BindView(R.id.txtContent_job_detail_psot)
    TextView txtContent_job_detail_psot;
    @BindView(R.id.txtPrice_job_detail_post)
    TextView txtPrice_job_detail_post;
    @BindView(R.id.txtDate_job_detail_post)
    TextView txtDate_job_detail_post;
    @BindView(R.id.txtTime_work_doing_detail_post)
    TextView txtTime_work_doing_detail_post;
    @BindView(R.id.txtAddress_detail_post)
    TextView txtAddress_detail_post;
    @BindView(R.id.imgType_job_detail_post)
    ImageView imgType_job_detail_post;
    @BindView(R.id.lo_clear_job)
    LinearLayout lo_clear_job;
    @BindView(R.id.progressPostJob)
    ProgressBar progressBar;
    @BindView(R.id.txtIsTools)
    TextView txtIsTools;
    @BindView(R.id.txtExpired_request_detail_post)
    TextView txtExpired_request_detail_post;
    @BindView(R.id.rela_confirm_maid)
    RelativeLayout rela_confirm_maid;
    @BindView(R.id.txtClearJob)
    TextView txtClearJob;

    public static Activity mDetailJobPostActivity = null;

    private Datum mDatum;
    private DetailJobPendingPresenter mDetailJobPostPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job_post);

        mDetailJobPostActivity = this;
        ButterKnife.bind(this);

        checkConnectionInterner();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDetailJobPostPresenter = new DetailJobPendingPresenter(this);


        lo_clear_job.setOnClickListener(this);
        lo_infoOwner.setOnClickListener(this);
        rela_confirm_maid.setOnClickListener(this);

        final Intent intent = getIntent();
        mDatum = (Datum) intent.getSerializableExtra("mDatum");

        if (!WorkTimeValidate.compareDays(mDatum.getInfo().getTime().getEndAt())) {
            txtExpired_request_detail_post.setVisibility(View.VISIBLE);
        } else {
            txtExpired_request_detail_post.setVisibility(View.GONE);
            if (mDatum.getProcess().getId().equals("000000000000000000000006")) {
                txtClearJob.setText(getResources().getString(R.string.denied));
                rela_confirm_maid.setVisibility(View.VISIBLE);
            } else {
                txtClearJob.setText(getResources().getString(R.string.cancel_work));
                rela_confirm_maid.setVisibility(View.GONE);
            }
        }

        if (mDatum.getInfo().getTools()) {
            txtIsTools.setVisibility(View.VISIBLE);
        } else {
            txtIsTools.setVisibility(View.GONE);
        }


        txtNameOwner.setText(mDatum.getStakeholders().getOwner().getInfo().getName());
        txtAddressOwner.setText((mDatum.getStakeholders().getOwner().getInfo().getAddress().getName()));
        txtTitle_job_detail_post.setText(mDatum.getInfo().getTitle());
        txtType_job_detail_post.setText(mDatum.getInfo().getWork().getName());
        txtContent_job_detail_psot.setText(mDatum.getInfo().getDescription());
        txtPrice_job_detail_post.setText(formatPrice(mDatum.getInfo().getPrice()));
        txtAddress_detail_post.setText(mDatum.getInfo().getAddress().getName());
        txtDate_job_detail_post.setText(WorkTimeValidate.getDatePostHistory(mDatum.getInfo().getTime().getEndAt()));
        txtTime_work_doing_detail_post.setText(WorkTimeValidate.getTimeWorkLanguage(this, mDatum.getInfo().getTime().getStartAt()) + " - " + WorkTimeValidate.getTimeWorkLanguage(this, mDatum.getInfo().getTime().getEndAt()));
        Glide.with(this)
                .load(mDatum.getStakeholders().getOwner().getInfo().getImage())
                .error(R.drawable.avatar)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.drawable.avatar)
                .dontAnimate()
                .into(img_avatarQwner);

        Picasso.with(this).load(mDatum.getInfo().getWork().getImage())
                .error(R.drawable.no_image)
                .placeholder(R.drawable.no_image)
                .into(imgType_job_detail_post);
//        Picasso.with(this).load(mDatum.getStakeholders().getOwner().getInfo().getImage())
//                .error(R.drawable.avatar)
//                .placeholder(R.drawable.avatar)
//                .into(img_avatarQwner);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            EventBus.getDefault().postSticky(false);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.lo_infoOwner:
                Intent itInfoOwner = new Intent(DetailJobPostActivity.this, OwnerProfileActivity.class);
                itInfoOwner.putExtra("InfoOwner", mDatum.getStakeholders().getOwner());
                startActivity(itInfoOwner);
                break;
            case R.id.lo_clear_job:
                if (mDatum.getProcess().getId().equals("000000000000000000000006")) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                    alertDialog.setCancelable(false);
                    alertDialog.setTitle(getResources().getString(R.string.notification));
                    alertDialog.setMessage(getResources().getString(R.string.notification_refuse_job_post));
                    alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                            progressBar.setVisibility(View.VISIBLE);
                            showProgress();
                            mDetailJobPostPresenter.refuseJobRequested(mDatum.getId(), mDatum.getStakeholders().getOwner().getId());
                        }
                    });
                    alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                    alertDialog.setCancelable(false);
                    alertDialog.setTitle(getResources().getString(R.string.notification));
                    alertDialog.setMessage(getResources().getString(R.string.notification_del_job_post));
                    alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                            progressBar.setVisibility(View.VISIBLE);
                            showProgress();
                            mDetailJobPostPresenter.deleteJob(mDatum.getId(), mDatum.getStakeholders().getOwner().getId());
                        }
                    });
                    alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertDialog.show();
                }
                break;

            case R.id.rela_confirm_maid:
//                progressBar.setVisibility(View.VISIBLE);
                showProgress();
                mDetailJobPostPresenter.accceptJobRequested(mDatum.getId(), mDatum.getStakeholders().getOwner().getId());
                break;
        }
    }

    private String formatPrice(Integer _Price) {
        String mOutputPrice = null;
        if (_Price != null && _Price != 0) {
//            DecimalFormat myFormatter = new DecimalFormat("#,###,##0");
//            mOutputPrice  = myFormatter.format(_Price);
            mOutputPrice = String.format("%s VND", NumberFormat.getNumberInstance(Locale.GERMANY).format(_Price));
        } else if (_Price == 0) {
            mOutputPrice = getResources().getString(R.string.hourly_pay);
        }
        return mOutputPrice;
    }

    @Override
    public void displayNotifyJobPost(JobPendingResponse isJobPost) {
        displayNotifySuccess(isJobPost.getStatus(), getResources().getString(R.string.notification_pass_del_job_post), isJobPost.getMessage());
    }

    @Override
    public void displayError(String error) {
        hideProgress();
    }

    @Override
    public void displayNotifyAccceptJobRequested(JobPendingResponse isJobPost) {
        displayNotifySuccess(isJobPost.getStatus(), getResources().getString(R.string.accepted_successfully), isJobPost.getMessage());
    }

    @Override
    public void displayErrorAccceptJobRequested(String error) {
        hideProgress();
    }

    @Override
    public void displayNotifyRefuseJobRequested(JobPendingResponse isJobPost) {
        displayNotifySuccess(isJobPost.getStatus(), getResources().getString(R.string.denied_successfully), isJobPost.getMessage());
    }

    @Override
    public void displayErrorRefuseJobRequested(String error) {
        hideProgress();
    }

    private void displayNotifySuccess(boolean isJobPost, String message, String error) {
//        progressBar.setVisibility(View.GONE);
        hideProgress();
        if (isJobPost) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setCancelable(false);
            alertDialog.setTitle(getResources().getString(R.string.notification));
            alertDialog.setMessage(message);
            alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EventBus.getDefault().postSticky(true);
                    finish();
                }
            });

            alertDialog.show();
        } else {
            ShowAlertDialog.showAlert(error, DetailJobPostActivity.this);
        }
    }


    @Override
    public void connectServerFail() {
        ShowAlertDialog.showAlert(getResources().getString(R.string.connection_error), this);
    }
}
