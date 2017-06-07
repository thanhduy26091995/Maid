package com.hbbsolution.maid.workmanager.listworkmanager.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.base.IconTextView;
import com.hbbsolution.maid.workmanager.adapter.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tantr on 6/1/2017.
 */

public class WorkManagerActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.management_title_toothbar)
    TextView txtManagement_title_toothbar;
    @BindView(R.id.management_compose_toothbar)
    IconTextView txtManagement_compose_toothbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private int tabMore, mQuantityJobPost;
    private boolean isPause = false, mTab = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_management);
        ButterKnife.bind(this);

        //setupView
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        txtManagement_title_toothbar.setText("Quản lý công việc");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtManagement_compose_toothbar.setOnClickListener(this);

        createFragment();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tabMore = extras.getInt("tabMore");
            mViewPager.setCurrentItem(tabMore);
        }
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
        adapter.addFragment(new JobPostedFragment(), "Đã đăng");
        adapter.addFragment(new JobPendingFragment(), "Đã phân công");
        adapter.addFragment(new JobDoingFragment(), "Đang làm");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.management_compose_toothbar:
                break;
        }
    }
}
