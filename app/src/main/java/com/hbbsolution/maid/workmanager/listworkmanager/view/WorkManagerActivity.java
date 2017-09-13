package com.hbbsolution.maid.workmanager.listworkmanager.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.base.AuthenticationBaseActivity;
import com.hbbsolution.maid.main.view.HomeActivity;
import com.hbbsolution.maid.utils.ConnectivityReceiver;
import com.hbbsolution.maid.utils.NonSwipeableViewPager;
import com.hbbsolution.maid.workmanager.adapter.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by tantr on 6/1/2017.
 */

public class WorkManagerActivity extends AuthenticationBaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    NonSwipeableViewPager mViewPager;
    @BindView(R.id.imgNo_internet)
    ImageView imgNo_internet;


    private Integer tabMore=-1;
    private boolean isPause = false, mTab = false;
    private int mPositionTab;
    private Integer flag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_management);
        ButterKnife.bind(this);

//        checkConnectionInterner();
        checkConnection();
        //setupView
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgNo_internet.setOnClickListener(this);
        createFragment();

        tabMore = getIntent().getIntExtra("tabMore", -1);
        if (tabMore != -1) {
            mViewPager.setCurrentItem(tabMore);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intentHome = new Intent(WorkManagerActivity.this, HomeActivity.class);
            startActivity(intentHome);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void createFragment() {
        mViewPager = (NonSwipeableViewPager) findViewById(R.id.viewpager);
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
        if (tabMore == -1) {
            if (isPause) {
                if (mTab) {
                    Intent refresh = new Intent(this, WorkManagerActivity.class);
                    startActivity(refresh);
                    // mViewPager.setCurrentItem(mPositionTab);
                    // this.finish();
                    mPositionTab = -1;
                    isPause = false;
                    mTab = false;
                }
            } else {
                if (mPositionTab == -1) {
                    mViewPager.setCurrentItem(0);
                } else {
                    mViewPager.setCurrentItem(mPositionTab);
                    mPositionTab = -1;
                }
            }
        }
        else{
            tabMore = -1;
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

    public void onEventMainThread(String positionTab) {
        mPositionTab = Integer.parseInt(positionTab);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intentHome = new Intent(WorkManagerActivity.this, HomeActivity.class);
        startActivity(intentHome);
        finish();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected) {
            imgNo_internet.setVisibility(View.VISIBLE);
        }
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected) {
            imgNo_internet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgNo_internet:
                boolean isConnected = ConnectivityReceiver.isConnected();
                if (isConnected) {
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                    imgNo_internet.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void responseCheckToken() {
        super.responseCheckToken();
    }
}
