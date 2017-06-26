package com.hbbsolution.maid.home.owner_profile.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.hbbsolution.maid.R;
import com.hbbsolution.maid.base.ImageLoader;
import com.hbbsolution.maid.history.model.work.Owner;
import com.hbbsolution.maid.model.task.Info;
import com.hbbsolution.maid.more.duy_nguyen.view.ReportOwnerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 05/06/2017.
 */

public class OwnerProfileActivity extends AppCompatActivity implements View.OnClickListener {
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
    private boolean isInJobDetail = false;
    private Info info;
    private Bundle extras;
    private static final int REPORT = 0;
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
            info = (Info) getIntent().getSerializableExtra("InfoOwner");
            txtNameInfoMaid.setText(info.getName());
            txtGenderInfoMaid.setText(getGenderMaid(info.getGender()));
            txtPhoneInfoMaid.setText(info.getPhone());
            txtAddressInfoMaid.setText(info.getAddress().getName());
            ImageLoader.getInstance().loadImageAvatar(OwnerProfileActivity.this, info.getImage(),
                    img_avatar);
        } else {
            mInfoOwner = (Owner) getIntent().getSerializableExtra("InfoOwner");
            txtNameInfoMaid.setText(mInfoOwner.getInfo().getName());
            txtGenderInfoMaid.setText(getGenderMaid(mInfoOwner.getInfo().getGender()));
            txtPhoneInfoMaid.setText(mInfoOwner.getInfo().getPhone());
            txtAddressInfoMaid.setText(mInfoOwner.getInfo().getAddress().getName());
            supportPostponeEnterTransition();
            Glide.with(this)
                    .load(mInfoOwner.getInfo().getImage())
                    .thumbnail(0.5f)
                    .error(R.drawable.no_image)
                    .centerCrop()
                    .dontAnimate()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            supportStartPostponedEnterTransition();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            supportStartPostponedEnterTransition();
                            return false;
                        }
                    })
                    .into(img_avatar);
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
                    intent.putExtra("InfoOwner", mInfoOwner);
                }
                intent.putExtra("IsInJobDetail",isInJobDetail);
                startActivityForResult(intent, REPORT);
                break;
        }
    }
}
