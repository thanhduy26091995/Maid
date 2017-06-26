package com.hbbsolution.maid.more.phuc_tran.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.more.phuc_tran.presenter.AboutPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity implements AboutView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.about_title_toolbar)
    TextView txt_about_title;
    @BindView(R.id.wvAbout)
    WebView wvAbout;

    private AboutPresenter mAboutPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ButterKnife.bind(this);

        //config toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txt_about_title.setText(getResources().getString(R.string.about));

        mAboutPresenter = new AboutPresenter(this);

        mAboutPresenter.getAbout();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getAbout(String content) {
        if (content.equals("")){
            content="Chưa có dữ liệu";
        }
        wvAbout.getSettings().setJavaScriptEnabled(true);
        wvAbout.loadDataWithBaseURL(null,
                content,
                "text/html",
                "utf-8",
                null);
    }
}