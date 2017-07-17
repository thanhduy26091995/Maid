package com.hbbsolution.maid.home.owner_profile.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.base.AuthenticationBaseActivity;
import com.hbbsolution.maid.base.ImageLoader;
import com.hbbsolution.maid.history.model.owner.OwnerHistory;
import com.hbbsolution.maid.history.model.work.Owner;
import com.hbbsolution.maid.more.duy_nguyen.view.ReportOwnerActivity;
import com.hbbsolution.maid.utils.ShowAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 05/06/2017.
 */

public class OwnerProfileActivity extends AuthenticationBaseActivity implements View.OnClickListener {
    @BindView(R.id.toolbar_header)
    Toolbar toolbar_header;
    @BindView(R.id.img_blur_image)
    ImageView imgBlurImage;
    @BindView(R.id.txt_profile_name)
    TextView txtNameInfoMaid;
    @BindView(R.id.txt_profile_gender)
    TextView txtGenderInfoMaid;
    @BindView(R.id.txt_profile_phone)
    TextView txtPhoneInfoMaid;
    @BindView(R.id.txt_profile_address)
    TextView txtAddressInfoMaid;
    @BindView(R.id.img_avatar)
    ImageView img_avatar;
    @BindView(R.id.linear_report_owner)
    LinearLayout linearReportOwner;
    private Owner mInfoOwner;
    private OwnerHistory mInfoOwnerHistory;
    private boolean isInJobDetail = false;
    private com.hbbsolution.maid.model.task.Owner info;
    private Bundle extras;
    private static final int REPORT = 0;
    private int flat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile);
        ButterKnife.bind(this);
        toolbar_header.setTitle("");
        setSupportActionBar(toolbar_header);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        linearReportOwner.setOnClickListener(this);
        isInJobDetail = getIntent().getBooleanExtra("IsInJobDetail", false);
        if (isInJobDetail) {
            info = (com.hbbsolution.maid.model.task.Owner) getIntent().getSerializableExtra("InfoOwner");
            txtNameInfoMaid.setText(info.getInfo().getName());
            txtGenderInfoMaid.setText(getGenderMaid(info.getInfo().getGender()));
            txtPhoneInfoMaid.setText(info.getInfo().getPhone());
            txtAddressInfoMaid.setText(info.getInfo().getAddress().getName());
            ImageLoader.getInstance().loadImageAvatar(OwnerProfileActivity.this, info.getInfo().getImage(),
                    img_avatar);
        } else {
            flat = getIntent().getIntExtra("flat", 0);
            if (flat == 1) {
                mInfoOwnerHistory = (OwnerHistory) getIntent().getSerializableExtra("InfoOwner");
                txtNameInfoMaid.setText(mInfoOwnerHistory.getId().getInfo().getName());
                txtGenderInfoMaid.setText(getGenderMaid(mInfoOwnerHistory.getId().getInfo().getGender()));
                txtPhoneInfoMaid.setText(mInfoOwnerHistory.getId().getInfo().getPhone());
                txtAddressInfoMaid.setText(mInfoOwnerHistory.getId().getInfo().getAddress().getName());
                supportPostponeEnterTransition();
                ImageLoader.getInstance().loadImageAvatar(OwnerProfileActivity.this, mInfoOwnerHistory.getId().getInfo().getImage(),
                        img_avatar);
            } else {
                mInfoOwner = (Owner) getIntent().getSerializableExtra("InfoOwner");
                txtNameInfoMaid.setText(mInfoOwner.getInfo().getName());
                txtGenderInfoMaid.setText(getGenderMaid(mInfoOwner.getInfo().getGender()));
                txtPhoneInfoMaid.setText(mInfoOwner.getInfo().getPhone());
                txtAddressInfoMaid.setText(mInfoOwner.getInfo().getAddress().getName());
                supportPostponeEnterTransition();
                ImageLoader.getInstance().loadImageAvatar(OwnerProfileActivity.this, mInfoOwner.getInfo().getImage(),
                        img_avatar);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private String getGenderMaid(int gender) {
        if (gender == 0) {
            return getResources().getString(R.string.pro_file_gender_male);
        }
        return getResources().getString(R.string.pro_file_gender_female);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_report_owner:
                Intent intent = new Intent(OwnerProfileActivity.this, ReportOwnerActivity.class);
                if (isInJobDetail) {
                    intent.putExtra("InfoOwner", info);
                } else {
                    if (flat == 1) {
                        intent.putExtra("InfoOwner", mInfoOwnerHistory);
                    } else {
                        intent.putExtra("InfoOwner", mInfoOwner);
                    }
                    intent.putExtra("flat", flat);
                }
                intent.putExtra("IsInJobDetail", isInJobDetail);
                startActivityForResult(intent, REPORT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REPORT) {
            if (resultCode == Activity.RESULT_OK) {
                ShowAlertDialog.showAlert(getResources().getString(R.string.reportsuccess), this);
            }
        }
    }
}
