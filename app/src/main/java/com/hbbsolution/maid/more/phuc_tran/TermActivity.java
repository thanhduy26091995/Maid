package com.hbbsolution.maid.more.phuc_tran;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.more.phuc_tran.presenter.AboutPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermActivity extends AppCompatActivity implements AboutView{

    @BindView(R.id.term_toolbar)
    Toolbar toolbar;
    @BindView(R.id.term_title_toolbar)
    TextView txt_term_toolbar;
    @BindView(R.id.webview_content_term)
    WebView webview_content_term;

    private AboutPresenter mAboutPresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        ButterKnife.bind(this);

        //config toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txt_term_toolbar.setText(getResources().getString(R.string.term_title));

        mAboutPresenter = new AboutPresenter(this);

        mAboutPresenter.getTerm();


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
        webview_content_term.getSettings().setJavaScriptEnabled(true);
        webview_content_term.loadDataWithBaseURL(null,
                content,
                "text/html",
                "utf-8",
                null);
    }
}