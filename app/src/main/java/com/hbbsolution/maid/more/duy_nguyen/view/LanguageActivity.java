package com.hbbsolution.maid.more.duy_nguyen.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.api.ApiClient;
import com.hbbsolution.maid.utils.SessionManagerForLanguage;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LanguageActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvVietnamese)
    TextView tvVietnamese;
    @BindView(R.id.tvEnglish)
    TextView tvEnglish;
    @BindView(R.id.txt_chk_en)
    TextView txtEng;
    @BindView(R.id.txt_chk_vi)
    TextView txtVi;
    private Locale locale;
    private SessionManagerForLanguage sessionManagerForLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        ButterKnife.bind(this);
        setToolbar();
        setEventClick();
        sessionManagerForLanguage = new SessionManagerForLanguage(this);
        String language = sessionManagerForLanguage.getLanguage();
        if (language.equals("Tiếng Việt") || language.equals("Vietnamese")) {
            hideIconEng();
        } else {
            hideIconVi();
        }
    }

    private void hideIconEng() {
        txtEng.setVisibility(View.GONE);
        txtVi.setVisibility(View.VISIBLE);
    }

    private void hideIconVi() {
        txtEng.setVisibility(View.VISIBLE);
        txtVi.setVisibility(View.GONE);
    }

    public void setToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setEventClick() {
        tvEnglish.setOnClickListener(this);
        tvVietnamese.setOnClickListener(this);
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
        String language;
        switch (v.getId()) {
            case R.id.tvVietnamese:
                ApiClient.setLanguage("vi");
                language = "Tiếng Việt";
                setLocale("vi", language);
                sessionManagerForLanguage.setIsClickedLanguage(true);
                sessionManagerForLanguage.createLanguageSession(language);
                break;
            case R.id.tvEnglish:
                ApiClient.setLanguage("en");
                language = "English";
                setLocale("en", language);
                sessionManagerForLanguage.setIsClickedLanguage(true);
                sessionManagerForLanguage.createLanguageSession(language);
                break;
        }
    }

    private void setLocale(String lang, String language) {
        locale = new Locale(lang);
        Resources res = this.getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        Configuration configuration = res.getConfiguration();
        configuration.locale = locale;
        res.updateConfiguration(configuration, displayMetrics);
        finish();
        overridePendingTransition(0, 0);
        Intent refresh = new Intent(this, this.getClass());
        refresh.putExtra("language", language);
        startActivity(refresh);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
}
