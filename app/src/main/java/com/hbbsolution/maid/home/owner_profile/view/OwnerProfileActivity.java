package com.hbbsolution.maid.home.owner_profile.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hbbsolution.maid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 05/06/2017.
 */

public class OwnerProfileActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_header)
    Toolbar toolbar_header;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile);
        ButterKnife.bind(this);

        toolbar_header.setTitle("");
        setSupportActionBar(toolbar_header);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
