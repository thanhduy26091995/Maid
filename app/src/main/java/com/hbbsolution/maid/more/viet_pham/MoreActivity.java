package com.hbbsolution.maid.more.viet_pham;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hbbsolution.maid.R;
import com.hbbsolution.maid.base.ImageLoader;
import com.hbbsolution.maid.maid_profile.view.MaidProfileActivity;
import com.hbbsolution.maid.model.announcement.AnnouncementResponse;
import com.hbbsolution.maid.more.duy_nguyen.view.LanguageActivity;
import com.hbbsolution.maid.more.duy_nguyen.view.StatisticActivity;
import com.hbbsolution.maid.more.phuc_tran.view.AboutActivity;
import com.hbbsolution.maid.more.phuc_tran.view.ContactActivity;
import com.hbbsolution.maid.more.phuc_tran.view.TermActivity;
import com.hbbsolution.maid.more.viet_pham.presenter.MorePresenter;
import com.hbbsolution.maid.more.viet_pham.view.signin.ShareCodeActivity;
import com.hbbsolution.maid.more.viet_pham.view.signin.SignInActivity;
import com.hbbsolution.maid.utils.SessionManagerForAnnouncement;
import com.hbbsolution.maid.utils.SessionManagerForLanguage;
import com.hbbsolution.maid.utils.SessionManagerUser;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hbbsolution.maid.R.id.switch_announcement;

/**
 * Created by buivu on 04/05/2017.
 */

public class MoreActivity extends AppCompatActivity implements MoreForAnnouncementView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.more_title_toothbar)
    TextView txtMore_title_toothbar;
    @BindView(R.id.cardview_statistic)
    Button cvStatistic;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.img_avatar)
    ImageView imgAvatar;
    @BindView(R.id.lnLanguage)
    LinearLayout lnLanguage;
    @BindView(R.id.linearlayout_follow_facebook)
    LinearLayout lnlFollowFacebook;
    @BindView(R.id.lnLogOut)
    Button lnLogOut;
    @BindView(R.id.lo_about)
    LinearLayout lo_about;
    @BindView(R.id.lo_terms1)
    LinearLayout lo_terms1;
    @BindView(R.id.lo_terms)
    LinearLayout lo_terms;
    @BindView(R.id.cv_sign_in)
    CardView cvSignIn;
    @BindView(switch_announcement)
    SwitchCompat switchAnnouncement;
    @BindView(R.id.lo_share_app)
    LinearLayout lo_share_app;
    private SessionManagerForAnnouncement sessionManagerForAnnouncement;
    private SessionManagerUser sessionManagerUser;
    private HashMap<String, String> hashDataUser = new HashMap<>();
    private boolean isPause = false;
    private MorePresenter morePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        ButterKnife.bind(this);
        sessionManagerUser = new SessionManagerUser(this);
        sessionManagerForAnnouncement = new SessionManagerForAnnouncement(this);
        morePresenter = new MorePresenter(this);
        //config toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtMore_title_toothbar.setText(getResources().getString(R.string.more));
        addEvents();
        loadData();
        //event for switch
        switchAnnouncement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (sessionManagerUser.isLoggedIn()) {
                        sessionManagerForAnnouncement.createStateAnnouncement(true);
                        //call api to save deviceToken
                        String deviceToken = String.format("%s@//@android", FirebaseInstanceId.getInstance().getToken());
                        morePresenter.onAnnouncement(deviceToken);
                    }
                } else {
                    if (sessionManagerUser.isLoggedIn()) {
                        sessionManagerForAnnouncement.createStateAnnouncement(false);
                        morePresenter.offAnnouncement();
                    }
                }
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

    public void loadData() {
        hashDataUser = sessionManagerUser.getUserDetails();
        //showing data
        txtName.setText(hashDataUser.get(SessionManagerUser.KEY_NAME));
        txtAddress.setText(hashDataUser.get(SessionManagerUser.KEY_ADDRESS));
        ImageLoader.getInstance().loadImageAvatar(MoreActivity.this, hashDataUser.get(SessionManagerUser.KEY_IMAGE),
                imgAvatar);
        //check state of announcement
        if (sessionManagerForAnnouncement.getStateAnnouncement()) {
            switchAnnouncement.setChecked(true);
        } else {
            switchAnnouncement.setChecked(false);
        }
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

        lo_share_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itShareCode = new Intent(MoreActivity.this, ShareCodeActivity.class);
                startActivity(itShareCode);
            }
        });
        cvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionManagerUser.isLoggedIn()) {
                    Intent intent = new Intent(MoreActivity.this, MaidProfileActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MoreActivity.this, SignInActivity.class);
                    startActivity(intent);
                }
            }
        });


        cvStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iStatistic = new Intent(MoreActivity.this, StatisticActivity.class);
                startActivity(iStatistic);
            }
        });
        lnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MoreActivity.this);
                builder.setTitle(getResources().getString(R.string.signout))
                        .setMessage(getResources().getString(R.string.signoutContent)).setCancelable(true)
                        .setNegativeButton(getResources().getString(R.string.cancel), null)
                        .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                sessionManagerUser.logoutUser();
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("create-task");
                                Intent intent = new Intent(MoreActivity.this, SignInActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }).show();
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

        lnlFollowFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView webview = new WebView(MoreActivity.this);
                webview.getSettings().setJavaScriptEnabled(true);
                webview.loadUrl("https://www.facebook.com/Ng%C6%B0%E1%BB%9Di-Gi%C3%BAp-Vi%E1%BB%87c-247-122998571630965/");
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
        if (isPause) {
            SessionManagerForLanguage sessionManagerForLanguage = new SessionManagerForLanguage(MoreActivity.this);
            boolean isChangeLanguage = sessionManagerForLanguage.changeLanguage();
            if (isChangeLanguage) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(this.getIntent());
                overridePendingTransition(0, 0);
                sessionManagerForLanguage.setChangeLanguage();
            }
        }
    }

    @Override
    public void onAnnouncement(AnnouncementResponse announcementResponse) {

    }

    @Override
    public void offAnnouncement(AnnouncementResponse announcementResponse) {

    }

    @Override
    public void getError(String error) {

    }
}
