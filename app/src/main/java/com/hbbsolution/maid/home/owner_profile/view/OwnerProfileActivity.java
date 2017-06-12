package com.hbbsolution.maid.home.owner_profile.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.base.ImageLoader;
import com.hbbsolution.maid.history.model.work.InfoOwner;
import com.hbbsolution.maid.model.task.Info;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 05/06/2017.
 */

public class OwnerProfileActivity extends AppCompatActivity {
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
    private InfoOwner mInfoOwner;
    private boolean isInJobDetail = false;
    private Info info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile);
        ButterKnife.bind(this);
        toolbar_header.setTitle("");
        setSupportActionBar(toolbar_header);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        isInJobDetail = getIntent().getBooleanExtra("IsInJobDetail", false);
        if (isInJobDetail) {
            info = (Info) getIntent().getSerializableExtra("InfoOwner");
            txtNameInfoMaid.setText(info.getUsername());
            txtGenderInfoMaid.setText(getGenderMaid(info.getGender()));
            txtPhoneInfoMaid.setText(info.getPhone());
            txtAddressInfoMaid.setText(info.getAddress().getName());
            ImageLoader.getInstance().loadImageAvatar(OwnerProfileActivity.this, info.getImage(),
                    img_avatar);
        } else {
            setData();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setData() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            mInfoOwner = (InfoOwner) extras.getSerializable("InfoOwner");
        }
        if (mInfoOwner != null) {
            txtNameInfoMaid.setText(mInfoOwner.getUsername());
            txtGenderInfoMaid.setText(getGenderMaid(mInfoOwner.getGender()));
            txtPhoneInfoMaid.setText(mInfoOwner.getPhone());
            txtAddressInfoMaid.setText(mInfoOwner.getAddress().getName());
            supportPostponeEnterTransition();
            Picasso.with(this)
                    .load(mInfoOwner.getImage())
                    .fit()
                    .noFade()
                    .centerCrop()
                    .into(img_avatar, new Callback() {
                        @Override
                        public void onSuccess() {
                            supportStartPostponedEnterTransition();
                        }

                        @Override
                        public void onError() {
                            supportStartPostponedEnterTransition();
                        }
                    });
        }
    }

    private String getGenderMaid(int gender) {
        if (gender == 0) {
            return getResources().getString(R.string.pro_file_gender_male);
        }
        return getResources().getString(R.string.pro_file_gender_female);
    }
}
