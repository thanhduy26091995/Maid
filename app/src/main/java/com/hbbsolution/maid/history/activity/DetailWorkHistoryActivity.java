package com.hbbsolution.maid.history.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hbbsolution.maid.R;
import com.hbbsolution.maid.history.model.work.WorkHistory;
import com.hbbsolution.maid.home.owner_profile.view.OwnerProfileActivity;
import com.hbbsolution.maid.utils.WorkTimeValidate;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailWorkHistoryActivity extends AppCompatActivity implements View.OnClickListener {
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


    private WorkHistory doc;
    public static Activity detailWorkHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_work_history);
        ButterKnife.bind(this);
        detailWorkHistory = this;
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
            tvSalary.setText(NumberFormat.getNumberInstance(Locale.GERMANY).format(doc.getInfo().getPrice())+ " VND");
            tvAddress.setText(doc.getInfo().getAddress().getName());
            tvDate.setText(WorkTimeValidate.getDatePostHistory(doc.getInfo().getTime().getEndAt()));
            tvTime.setText(WorkTimeValidate.getTimeWork(doc.getInfo().getTime().getStartAt()).replace(":", "h") + " - " + WorkTimeValidate.getTimeWork(doc.getInfo().getTime().getEndAt()).replace(":", "h"));

            Glide.with(this).load(doc.getStakeholders().getOwner().getInfo().getImage())
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .centerCrop()
                    .dontAnimate()
                    .into(imgOwner);
            tvNameOwner.setText(doc.getStakeholders().getOwner().getInfo().getName());
            tvAddressOwner.setText(doc.getStakeholders().getOwner().getInfo().getAddress().getName());
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
}
