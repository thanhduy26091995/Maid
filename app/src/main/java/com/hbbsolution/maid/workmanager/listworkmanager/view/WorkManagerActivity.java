package com.hbbsolution.maid.workmanager.listworkmanager.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.workmanager.adapter.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by tantr on 6/1/2017.
 */

public class WorkManagerActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private Integer tabMore;
    private boolean isPause = false, mTab = false;
    private int mPositionTab;
    private Integer flag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_management);
        ButterKnife.bind(this);

        //setupView
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createFragment();
        tabMore = getIntent().getIntExtra("tabMore", 0);
        flag = getIntent().getIntExtra("flag", 0);
        if (tabMore != null && flag != null) {
            mViewPager.setCurrentItem(tabMore);

            if (flag == 1) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(WorkManagerActivity.this);
                alertDialog.setCancelable(false);
                alertDialog.setTitle("Thông báo");
                alertDialog.setMessage("Hoàn tất công việc");
                alertDialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.show().dismiss();
                    }
                });
                alertDialog.show();
            } else if (flag == 2) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(WorkManagerActivity.this);
                alertDialog.setCancelable(false);
                alertDialog.setTitle("Hoàn tất công việc");
                alertDialog.setMessage("Vui lòng xác nhận tiền mặt bằng cách nhấn " + "Ok");
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.show().dismiss();
                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.show().dismiss();
                    }
                });
                alertDialog.show();
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


    private void createFragment() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        setupViewPagerUser(mViewPager);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(2);
    }

    private void setupViewPagerUser(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new JobPostedFragment(), getResources().getString(R.string.posted_work));
        adapter.addFragment(new JobPendingFragment(), getResources().getString(R.string.assigned));
        adapter.addFragment(new JobDoingFragment(), getResources().getString(R.string.running_work));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().registerSticky(this);
    }

    @Override
    protected void onPause() {
        isPause = true;
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }


    @Override
    protected void onResume() {
        if (isPause) {
            if (mTab) {
                Intent refresh = new Intent(this, WorkManagerActivity.class);
                startActivity(refresh);
                mViewPager.setCurrentItem(mPositionTab);
                this.finish();
                mPositionTab = 0;
                isPause = false;
                mTab = false;
            }
        }

//        if (Constants.isLoadTabDoing) {
//            mViewPager.setCurrentItem(2);
//            Constants.isLoadTabDoing = false;
//        }
        super.onResume();
    }


    public void onEventMainThread(Boolean isTab) {
        mTab = isTab;
    }

    public void onEventMainThread(Integer positionTab) {
        mPositionTab = positionTab;
    }

}
