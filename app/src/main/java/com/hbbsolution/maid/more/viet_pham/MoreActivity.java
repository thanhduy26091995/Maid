package com.hbbsolution.maid.more.viet_pham;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.base.ImageLoader;
import com.hbbsolution.maid.main.HomeActivity;
import com.hbbsolution.maid.more.duy_nguyen.view.LanguageActivity;
import com.hbbsolution.maid.more.duy_nguyen.view.StatisticActivity;
import com.hbbsolution.maid.more.phuc_tran.AboutActivity;
import com.hbbsolution.maid.more.phuc_tran.ContactActivity;
import com.hbbsolution.maid.more.phuc_tran.TermActivity;
import com.hbbsolution.maid.more.viet_pham.view.signin.SignInActivity;
import com.hbbsolution.maid.utils.SessionManagerForLanguage;
import com.hbbsolution.maid.utils.SessionManagerUser;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 04/05/2017.
 */

public class MoreActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.more_title_toothbar)
    TextView txtMore_title_toothbar;
    @BindView(R.id.cardview_statistic)
    CardView cvStatistic;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.img_avatar)
    ImageView imgAvatar;
    @BindView(R.id.lnLanguage)
    LinearLayout lnLanguage;
    @BindView(R.id.lnLogOut)
    LinearLayout lnLogOut;
    @BindView(R.id.lo_about)
    RelativeLayout lo_about;
    @BindView(R.id.lo_terms1)
    RelativeLayout lo_terms1;
    @BindView(R.id.lo_terms)
    RelativeLayout lo_terms;
    @BindView(R.id.cv_sign_in)
    CardView cvSignIn;
    private SessionManagerUser sessionManagerUser;
    private HashMap<String, String> hashDataUser = new HashMap<>();
    private boolean isPause = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        ButterKnife.bind(this);
        //config toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtMore_title_toothbar.setText(getResources().getString(R.string.more));
        addEvents();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent iHome = new Intent(MoreActivity.this, HomeActivity.class);
            startActivity(iHome);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadData() {
        hashDataUser = sessionManagerUser.getUserDetails();
        //showing data
        txtName.setText(hashDataUser.get(SessionManagerUser.KEY_NAME));
        txtAddress.setText(hashDataUser.get(SessionManagerUser.KEY_ADDRESS));
        ImageLoader.getInstance().loadImageAvatar(MoreActivity.this, hashDataUser.get(SessionManagerUser.KEY_IMAGE),
                imgAvatar);
    }

    public void addEvents() {

//        lo_terms.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Toast.makeText(MoreActivity.this, "AAA", Toast.LENGTH_SHORT).show();
//                Intent itTerms = new Intent(MoreActivity.this, TermsActivity.class);
//                startActivity(itTerms);
//            }
//        });

        cvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MoreActivity.this, SignInActivity.class);
                startActivity(intent);

            }
        });


        cvStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iStatistic = new Intent(MoreActivity.this, StatisticActivity.class);
                startActivity(iStatistic);
            }
        });
        lnLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoreActivity.this, LanguageActivity.class);
                startActivity(intent);
            }
        });

        lo_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iStatistic = new Intent(MoreActivity.this, AboutActivity.class);
                startActivity(iStatistic);
            }
        });
        lo_terms1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iStatistic = new Intent(MoreActivity.this, TermActivity.class);
                startActivity(iStatistic);
            }
        });
        lo_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iStatistic = new Intent(MoreActivity.this, ContactActivity.class);
                startActivity(iStatistic);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isPause) {
            SessionManagerForLanguage sessionManagerForLanguage = new SessionManagerForLanguage(MoreActivity.this);
            boolean isChangeLanguage = sessionManagerForLanguage.changeLanguage();
            if(isChangeLanguage) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(this.getIntent());
                overridePendingTransition(0, 0);
            }
        }
    }
}
