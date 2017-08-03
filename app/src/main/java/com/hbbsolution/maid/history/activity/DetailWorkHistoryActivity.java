package com.hbbsolution.maid.history.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hbbsolution.maid.R;
import com.hbbsolution.maid.history.inteface.CommentHistoryView;
import com.hbbsolution.maid.history.model.direct_bill.DirectBillResponse;
import com.hbbsolution.maid.history.model.work.WorkHistory;
import com.hbbsolution.maid.history.presenter.CommentHistoryPresenter;
import com.hbbsolution.maid.history.presenter.DetailWorkPresenter;
import com.hbbsolution.maid.home.owner_profile.view.OwnerProfileActivity;
import com.hbbsolution.maid.model.choose_work.ChooseWorkResponse;
import com.hbbsolution.maid.utils.ShowAlertDialog;
import com.hbbsolution.maid.utils.WorkTimeValidate;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailWorkHistoryActivity extends AppCompatActivity implements View.OnClickListener, DetailWorkView, CommentHistoryView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.detail_work_history_type)
    ImageView imgJobType;
    @BindView(R.id.detail_work_history_tvJob)
    TextView tvJob;
    @BindView(R.id.detail_work_history_tvWork)
    TextView tvWork;
    @BindView(R.id.detail_work_history_tvContent)
    TextView tvContent;
    @BindView(R.id.detail_work_history_tvSalary)
    TextView tvSalary;
    @BindView(R.id.detail_work_history_tvDate)
    TextView tvDate;
    @BindView(R.id.detail_work_history_tvTime)
    TextView tvTime;
    @BindView(R.id.detail_work_history_tvAddress)
    TextView tvAddress;

    @BindView(R.id.detail_owner_history_avatar)
    ImageView imgOwner;
    @BindView(R.id.detail_owner_history_helper_name)
    TextView tvNameOwner;
    @BindView(R.id.detail_work_history_helper_address)
    TextView tvAddressOwner;

    @BindView(R.id.rela_info)
    RelativeLayout rlInfo;
    @BindView(R.id.tvContentComment)
    TextView tvContentComment;
    @BindView(R.id.txtIsTools)
    TextView txtIsTools;

    @BindView(R.id.v_line)
    View vLine;

    private WorkHistory doc;
    public static Activity detailWorkHistory;
    private DetailWorkPresenter mPresenter;
    private ProgressDialog mProgressDialog;
    private AlertDialog.Builder alertDialogConfirm;
    private CommentHistoryPresenter commentHistoryPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_work_history);
        ButterKnife.bind(this);
        detailWorkHistory = this;
        mPresenter = new DetailWorkPresenter(this);
        setToolbar();
        getData();
        setEventClick();
    }

    public void setEventClick() {
        rlInfo.setOnClickListener(this);
    }

    public void setToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void getData() {
        Bundle extras = getIntent().getExtras();
        commentHistoryPresenter = new CommentHistoryPresenter(this);
        if (extras != null) {
            doc = (WorkHistory) extras.getSerializable("work");
            Glide.with(this).load(doc.getInfo().getWork().getImage())
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .centerCrop()
                    .dontAnimate()
                    .into(imgJobType);
            tvJob.setText(doc.getInfo().getTitle());
            tvWork.setText(doc.getInfo().getWork().getName());
            tvContent.setText(doc.getInfo().getDescription());
            tvSalary.setText(formatPrice(doc.getInfo().getPrice()));
            tvAddress.setText(doc.getInfo().getAddress().getName());
            tvDate.setText(WorkTimeValidate.getDatePostHistory(doc.getInfo().getTime().getEndAt()));
            tvTime.setText(WorkTimeValidate.getTimeWorkLanguage(DetailWorkHistoryActivity.this,doc.getInfo().getTime().getStartAt()) + " - " + WorkTimeValidate.getTimeWorkLanguage(DetailWorkHistoryActivity.this,doc.getInfo().getTime().getEndAt()));
            //check tools
            if (doc.getInfo().getTools()) {
                txtIsTools.setVisibility(View.VISIBLE);
            } else {
                txtIsTools.setVisibility(View.GONE);
            }
            Glide.with(this).load(doc.getStakeholders().getOwner().getInfo().getImage())
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .centerCrop()
                    .dontAnimate()
                    .into(imgOwner);
            tvNameOwner.setText(doc.getStakeholders().getOwner().getInfo().getName());
            tvAddressOwner.setText(doc.getStakeholders().getOwner().getInfo().getAddress().getName());
            //call api to check bill
            showProgress();
            mPresenter.getDirectBill(doc.getId());
            commentHistoryPresenter.checkComment(doc.getId());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rela_info:
                intent = new Intent(this, OwnerProfileActivity.class);
                intent.putExtra("InfoOwner", doc.getStakeholders().getOwner());
//                ActivityOptionsCompat historyOption =
//                        ActivityOptionsCompat
//                                .makeSceneTransitionAnimation(this, (View) findViewById(R.id.detail_owner_history_avatar), "icAvatar");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    startActivity(intent, historyOption.toBundle());
//                } else {
                startActivity(intent);
//                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void showProgress() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getResources().getString(R.string.loading));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    private void hideProgress() {
        if (mProgressDialog.isShowing() && mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void getDirectBill(final DirectBillResponse directBillResponse) {
        hideProgress();
        boolean status = directBillResponse.isStatus();
        if (status) {
            alertDialogConfirm = new AlertDialog.Builder(DetailWorkHistoryActivity.this);
            alertDialogConfirm.setCancelable(false);
            alertDialogConfirm.setTitle(getResources().getString(R.string.completed));
            alertDialogConfirm.setMessage(getResources().getString(R.string.confirm_cast));
            alertDialogConfirm.setPositiveButton(getResources().getString(R.string.terms_btn_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mPresenter.directConfirm(directBillResponse.getData().getId());
                }
            });
            alertDialogConfirm.setNegativeButton(getResources().getString(R.string.huy), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //alertDialogConfirm.show().dismiss();
                    mPresenter.cancelDirectConfirm(directBillResponse.getData().getId());
                }
            });
            alertDialogConfirm.show();
        }
    }

    @Override
    public void directConfirm(ChooseWorkResponse chooseWorkResponse) {
        alertDialogConfirm.create().dismiss();
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailWorkHistoryActivity.this);
        alertDialogBuilder.setMessage(getResources().getString(R.string.confirm_ok));
        alertDialogBuilder.setPositiveButton(getResources().getText(R.string.okAlert),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        alertDialogBuilder.create().dismiss();
                    }

                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void cancelDirectConfirm(ChooseWorkResponse chooseWorkResponse) {
        alertDialogConfirm.create().dismiss();
    }

    @Override
    public void getError(String error) {
        hideProgress();
        alertDialogConfirm.create().dismiss();
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailWorkHistoryActivity.this);
        alertDialogBuilder.setMessage(getResources().getString(R.string.confirm_failed));
        alertDialogBuilder.setPositiveButton(getResources().getText(R.string.okAlert),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        alertDialogBuilder.create().dismiss();
                    }

                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void checkCommentSuccess(String message) {
        tvContentComment.setVisibility(View.VISIBLE);
        vLine.setVisibility(View.VISIBLE);
        tvContentComment.setText(message);
    }

    @Override
    public void checkCommentFail() {
    }

    private String formatPrice(Integer _Price) {
        String mOutputPrice = null;
        if (_Price != null && _Price != 0) {
            mOutputPrice = String.format("%s VND", NumberFormat.getNumberInstance(Locale.GERMANY).format(_Price));
        } else if (_Price == 0) {
            mOutputPrice = getResources().getString(R.string.hourly_pay);
        }
        return mOutputPrice;
    }

    @Override
    public void connectServerFail() {
        ShowAlertDialog.showAlert(getResources().getString(R.string.connection_error), this);
    }
}
