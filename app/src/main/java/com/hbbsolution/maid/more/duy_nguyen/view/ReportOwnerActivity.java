package com.hbbsolution.maid.more.duy_nguyen.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hbbsolution.maid.R;
import com.hbbsolution.maid.history.model.owner.OwnerHistory;
import com.hbbsolution.maid.history.model.work.Owner;
import com.hbbsolution.maid.more.duy_nguyen.inteface.ReportView;
import com.hbbsolution.maid.more.duy_nguyen.presenter.ReportPresenter;
import com.hbbsolution.maid.utils.ShowAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ReportOwnerActivity extends AppCompatActivity implements View.OnClickListener, ReportView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_owner_name)
    TextView mTextOwnerName;
    @BindView(R.id.text_owner_address)
    TextView mTextOwnerAddress;
    @BindView(R.id.tvSend)
    TextView tvSend;
    @BindView(R.id.tvSkip)
    TextView tvSkip;
    @BindView(R.id.edtReport)
    EditText edtReport;
    @BindView(R.id.img_avatar)
    CircleImageView imgAvatar;
    private com.hbbsolution.maid.model.task.Owner info;
    private Owner mInfoOwner;
    private OwnerHistory mInfoOwnerHistory;
    private ReportPresenter reportPresenter;
    private boolean isInJobDetail = false;
    private String idOnwer;
    private int flat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        //init toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        reportPresenter = new ReportPresenter(this);
        loadData();
        tvSend.setOnClickListener(this);
        tvSkip.setOnClickListener(this);
    }

    private void loadData() {
        isInJobDetail = getIntent().getBooleanExtra("IsInJobDetail", false);
        if (isInJobDetail) {
            info = (com.hbbsolution.maid.model.task.Owner) getIntent().getSerializableExtra("InfoOwner");
            mTextOwnerName.setText(info.getInfo().getUsername());
            mTextOwnerAddress.setText(info.getInfo().getAddress().getName());
            idOnwer = info.getId();
            Glide.with(this).load(info.getInfo().getImage())
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .centerCrop()
                    .dontAnimate()
                    .into(imgAvatar);
        } else {
            flat = getIntent().getIntExtra("flat", 0);
            if (flat == 1) {
                mInfoOwnerHistory = (OwnerHistory) getIntent().getSerializableExtra("InfoOwner");
                mTextOwnerName.setText(mInfoOwnerHistory.getId().getInfo().getUsername());
                mTextOwnerAddress.setText(mInfoOwnerHistory.getId().getInfo().getAddress().getName());
                idOnwer = mInfoOwnerHistory.getId().getId();
                Glide.with(this).load(mInfoOwnerHistory.getId().getInfo().getImage())
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.avatar)
                        .error(R.drawable.avatar)
                        .centerCrop()
                        .dontAnimate()
                        .into(imgAvatar);
            } else {
                mInfoOwner = (Owner) getIntent().getSerializableExtra("InfoOwner");
                mTextOwnerName.setText(mInfoOwner.getInfo().getUsername());
                mTextOwnerAddress.setText(mInfoOwner.getInfo().getAddress().getName());
                idOnwer = mInfoOwner.getId();
                Glide.with(this).load(mInfoOwner.getInfo().getImage())
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.avatar)
                        .error(R.drawable.avatar)
                        .centerCrop()
                        .dontAnimate()
                        .into(imgAvatar);
            }
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
        switch (v.getId()) {
            case R.id.tvSend:
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                if (edtReport.getText().toString().length() > 0) {
                    reportPresenter.reportOwner(idOnwer, edtReport.getText().toString().trim());
                } else {
                    ShowAlertDialog.showAlert("Vui lòng điền nội dung báo cáo", ReportOwnerActivity.this);
                }
                break;
            case R.id.tvSkip:
                finish();
                break;
        }
    }

    @Override
    public void reportSuccess(String message) {
        Intent intent = new Intent();
        intent.putExtra("message", message);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void reportFail() {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
