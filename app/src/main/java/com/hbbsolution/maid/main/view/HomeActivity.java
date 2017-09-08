package com.hbbsolution.maid.main.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.google.firebase.messaging.FirebaseMessaging;
import com.hbbsolution.maid.R;
import com.hbbsolution.maid.base.BaseActivity;
import com.hbbsolution.maid.base.ImageLoader;
import com.hbbsolution.maid.history.activity.HistoryActivity;
import com.hbbsolution.maid.home.job_near_by_new_version.view.JobNearByNewActivity;
import com.hbbsolution.maid.main.presenter.HomePresenter;
import com.hbbsolution.maid.more.viet_pham.MoreActivity;
import com.hbbsolution.maid.more.viet_pham.view.signin.SignInActivity;
import com.hbbsolution.maid.utils.SessionManagerForLanguage;
import com.hbbsolution.maid.utils.SessionManagerUser;
import com.hbbsolution.maid.utils.SessionShortcutBadger;
import com.hbbsolution.maid.workmanager.listworkmanager.view.WorkManagerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import me.leolin.shortcutbadger.ShortcutBadger;

public class HomeActivity extends BaseActivity implements View.OnClickListener, HomeView {

    @BindView(R.id.lo_maid_around)
    Button mLayout_MaidAround;
    @BindView(R.id.lo_your_tasks)
    Button mLayout_YourTasks;
    @BindView(R.id.lo_history)
    Button mLayout_History;
    @BindView(R.id.imgAvatar)
    CircleImageView imgAvatar;
    private boolean isPause = false;
    private HomePresenter mHomePresenter;
    private SessionManagerForLanguage sessionManagerForLanguage;
    private SessionManagerUser sessionManagerUser;
    private SessionShortcutBadger sessionShortcutBadger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        checkConnectionInterner();
        //Clear shortcutBadger
        sessionShortcutBadger = new SessionShortcutBadger(this);
        sessionShortcutBadger.removeCount();
        ShortcutBadger.removeCount(this); //for 1.1.4+

        // on click
        mLayout_MaidAround.setOnClickListener(this);
        mLayout_YourTasks.setOnClickListener(this);
        mLayout_History.setOnClickListener(this);

        mHomePresenter = new HomePresenter(this);
        mHomePresenter.requestCheckToken();
        addEvents();
        sessionManagerUser = new SessionManagerUser(HomeActivity.this);

        //Set Avatar
        ImageLoader.getInstance().loadImageAvatar(HomeActivity.this,sessionManagerUser.getUserDetails().get(SessionManagerUser.KEY_IMAGE),imgAvatar);
    }


    public void addEvents() {

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transActivity(MoreActivity.class);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.lo_maid_around:
                transActivity(JobNearByNewActivity.class);
                finish();
                break;
            case R.id.lo_your_tasks:
                transActivity(WorkManagerActivity.class);
                finish();
                break;
            case R.id.lo_history:
                transActivity(HistoryActivity.class);
                finish();
                break;
        }
    }

    // Transition Activity
    private void transActivity(Class activity) {
        Intent itTransActivity = new Intent(HomeActivity.this, activity);
        startActivity(itTransActivity);
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
            SessionManagerForLanguage sessionManagerForLanguage = new SessionManagerForLanguage(HomeActivity.this);
            boolean isChangeLanguage = sessionManagerForLanguage.changeLanguage();
            if (isChangeLanguage) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(this.getIntent());
                overridePendingTransition(0, 0);
            }
        }
    }

    @Override
    public void responseCheckToken() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle(getResources().getString(R.string.notification));
        alertDialog.setMessage(getResources().getString(R.string.auth));
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sessionManagerUser.logoutUser();
                FirebaseMessaging.getInstance().unsubscribeFromTopic("create-task");
                Intent itBackSignIn = new Intent(HomeActivity.this, SignInActivity.class);
                startActivity(itBackSignIn);
                finish();
            }
        });
        alertDialog.show();
    }

    @Override
    public void errorConnectService() {

    }
}
