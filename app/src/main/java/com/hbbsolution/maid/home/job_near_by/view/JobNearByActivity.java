package com.hbbsolution.maid.home.job_near_by.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hbbsolution.maid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 05/06/2017.
 */

public class JobNearByActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.seekBar_distance)
    SeekBar seekBarDistance;
    @BindView(R.id.text_distance)
    TextView txtDistance;

    private Integer maxDistance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_near_by);
        ButterKnife.bind(this);
        //setup toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        //event change seekbar
        seekBarDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                maxDistance = progress;
                txtDistance.setText(String.format("%d km", progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.bind(this).unbind();
        super.onDestroy();
    }
}
