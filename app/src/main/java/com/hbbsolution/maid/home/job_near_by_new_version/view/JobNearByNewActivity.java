package com.hbbsolution.maid.home.job_near_by_new_version.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.base.BaseActivity;
import com.hbbsolution.maid.base.ConfigSingleton;
import com.hbbsolution.maid.home.job_near_by_new_version.model.FilterModel;
import com.hbbsolution.maid.home.job_near_by_new_version.presenter.JobNearByPresenter;
import com.hbbsolution.maid.main.view.HomeActivity;
import com.hbbsolution.maid.model.job.TypeJobResponse;
import com.hbbsolution.maid.model.task.TaskData;
import com.hbbsolution.maid.workmanager.adapter.ViewPagerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


/**
 * Created by buivu on 25/08/2017.
 */

public class JobNearByNewActivity extends BaseActivity implements View.OnClickListener, JobNearByNewView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.relative_filter)
    RelativeLayout relativeFilter;
    @BindView(R.id.textView_filter_result)
    TextView textViewFilter;
    @BindView(R.id.textView_filter_result_address)
    TextView textViewFilterAddress;
    @BindView(R.id.rdbCreate)
    RadioButton rdbCreate;
    @BindView(R.id.rdbStart)
    RadioButton rdbStart;

    private JobNearByPresenter mJobNearByPresenter;
    public static final int REQUEST_CODE_INTENT = 5;
    private FilterModel mFilterModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_near_by_new);
        ButterKnife.bind(this);
        //event click
        setupComponents();
        relativeFilter.setOnClickListener(this);
        getSortType();
    }

    private void getSortType() {
        rdbCreate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    EventBus.getDefault().postSticky(1);
                } else {
                    EventBus.getDefault().postSticky(2);
                }
            }
        });
    }

    private void setupComponents() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        //set title
        textViewFilterAddress.setText(String.format("%s: %s", getResources().getString(R.string.near_by_filter_location),
                getResources().getString(R.string.near_by_filter_current_location)));
        textViewFilter.setText(String.format("%s: %s" + "\r\n" + "%s: %s", getResources().getString(R.string.near_by_filter_distance), String.format("%d km", 5), getResources().getString(R.string.near_by_filter_type_job), getResources().getString(R.string.near_by_filter_type_job_all)));
        //init presenter
        mJobNearByPresenter = new JobNearByPresenter(this);
        mJobNearByPresenter.getAllTypeJob();
        //setup tab layout and view pager

        setupViewPagerUser(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(1);
        //event change tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {
                    ListJobMapFragment.getInstance().updateMap(ListJobFragment.getInstance().getCurrentTaskData());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setupViewPagerUser(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ListJobFragment(), getString(R.string.near_by_job_list));
        adapter.addFragment(new ListJobMapFragment(), getString(R.string.near_by_job_map));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v == relativeFilter) {
            Intent intent = new Intent(JobNearByNewActivity.this, JobNearByFilterActivity.class);
            mFilterModel = ListJobFragment.getInstance().getFilterModel();
            intent.putExtra("FilterModel", mFilterModel);
            startActivityForResult(intent, REQUEST_CODE_INTENT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_INTENT && resultCode == RESULT_OK) {
            try {
                List<TaskData> taskDatas = (List<TaskData>) data.getSerializableExtra("TaskList");
                FilterModel filterModel = (FilterModel) data.getSerializableExtra("FilterModelResult");
                String mTypeJobName = "";
                if (filterModel.getWorkName() != null) {
                    mTypeJobName = filterModel.getWorkName();
                } else {
                    mTypeJobName = getResources().getString(R.string.near_by_filter_type_job_all);
                }
                ListJobFragment.getInstance().saveFiterData(filterModel);
                //set text
                textViewFilterAddress.setText(String.format("%s: %s", getResources().getString(R.string.near_by_filter_location),
                        filterModel.getAddressName()));
                textViewFilter.setText(String.format("%s: %s" + "\r\n" + "%s: %s", getResources().getString(R.string.near_by_filter_distance), String.format("%d km", filterModel.getDistance()), getResources().getString(R.string.near_by_filter_type_job), mTypeJobName));
                ListJobFragment listJobFragment = ListJobFragment.getInstance();
                listJobFragment.updateList(filterModel);
                //check nếu đang ở tab 1
                //if (tabLayout.getSelectedTabPosition() == 1) {
                ListJobMapFragment.getInstance().updateMap(taskDatas);
                //}
            }
            catch (Exception e){}
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intentHome = new Intent(JobNearByNewActivity.this, HomeActivity.class);
            startActivity(intentHome);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intentHome = new Intent(JobNearByNewActivity.this, HomeActivity.class);
        startActivity(intentHome);
        finish();
    }

    @Override
    public void getAllTypeJob(TypeJobResponse typeJobResponse) {
        ConfigSingleton.getInstance().saveData(typeJobResponse);
    }


    @Override
    public void getError(String error) {

    }

}
