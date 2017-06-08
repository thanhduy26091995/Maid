package com.hbbsolution.maid.home.job_detail.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.base.ImageLoader;
import com.hbbsolution.maid.model.task.TaskData;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 05/06/2017.
 */

public class JobDetailActivity extends AppCompatActivity {


    private TaskData taskData;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_title)
    TextView txtTitle;
    @BindView(R.id.txt_owner_name)
    TextView txtOwnerName;
    @BindView(R.id.txt_owner_address)
    TextView txtOwnerAddress;
    @BindView(R.id.img_avatar)
    ImageView imgAvatar;
    @BindView(R.id.img_job_type)
    ImageView imgJobType;
    @BindView(R.id.txt_job_name)
    TextView txtJobName;
    @BindView(R.id.txt_job_type)
    TextView txtJobType;
    @BindView(R.id.txt_job_description)
    TextView txtJobDescription;
    @BindView(R.id.txt_job_price)
    TextView txtJobPrice;
    @BindView(R.id.txt_date)
    TextView txtDate;
    @BindView(R.id.txt_detail_time)
    TextView txtDetailTime;
    @BindView(R.id.txt_address)
    TextView txtAddress;

    private String date;
    private String startTime, endTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        ButterKnife.bind(this);
        //get intent
        taskData = (TaskData) getIntent().getSerializableExtra("TaskData");
        //setup toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        //load data
        loadData();
    }

    private void loadData() {
        txtTitle.setText(taskData.getInfo().getWork().getName());
        txtOwnerName.setText(taskData.getStakeholders().getOwner().getInfo().getName());
        txtOwnerAddress.setText(taskData.getStakeholders().getOwner().getInfo().getAddress().getName());
        //load avatar
        ImageLoader.getInstance().loadImageAvatar(JobDetailActivity.this, taskData.getStakeholders().getOwner().getInfo().getImage(),
                imgAvatar);
        ImageLoader.getInstance().loadImageOther(JobDetailActivity.this, taskData.getInfo().getWork().getImage(), imgJobType);
        txtJobName.setText(taskData.getInfo().getTitle());
        txtJobType.setText(taskData.getInfo().getWork().getName());
        txtJobDescription.setText(taskData.getInfo().getDescription());
        txtJobPrice.setText(String.format("%d VND", taskData.getInfo().getPrice()));
        txtAddress.setText(taskData.getInfo().getAddress().getName());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat time = new SimpleDateFormat("H:mm a", Locale.US);
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.US);
        // OVERRIDE SOME symbols WHILE RETAINING OTHERS
        symbols.setAmPmStrings(new String[]{"am", "pm"});
        time.setDateFormatSymbols(symbols);
        try {
            Date endDate = simpleDateFormat.parse(taskData.getInfo().getTime().getEndAt());
            Date nowDate = new Date();
            Date startDate = simpleDateFormat.parse(taskData.getInfo().getTime().getStartAt());
            date = dates.format(endDate);
            startTime = time.format(startDate);
            endTime = time.format(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        txtDate.setText(date);
        txtDetailTime.setText(startTime.replace(":", "h") + " - " + endTime.replace(":", "h"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
