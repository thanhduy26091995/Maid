package com.hbbsolution.maid.main.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.base.BaseActivity;
import com.hbbsolution.maid.base.IconTextView;
import com.hbbsolution.maid.history.activity.HistoryActivity;
import com.hbbsolution.maid.home.job_near_by_new_version.view.JobNearByNewActivity;
import com.hbbsolution.maid.main.presenter.HomePresenter;
import com.hbbsolution.maid.more.viet_pham.MoreActivity;
import com.hbbsolution.maid.more.viet_pham.view.signin.SignInActivity;
import com.hbbsolution.maid.service.BadgeIntentService;
import com.hbbsolution.maid.utils.SessionManagerForLanguage;
import com.hbbsolution.maid.utils.SessionManagerUser;
import com.hbbsolution.maid.utils.SessionShortcutBadger;
import com.hbbsolution.maid.workmanager.listworkmanager.view.WorkManagerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.leolin.shortcutbadger.ShortcutBadger;

public class HomeActivity extends BaseActivity implements View.OnClickListener, HomeView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.home_title_toothbar)
    TextView txtHome_title_toothbar;
    @BindView(R.id.lo_maid_around)
    RelativeLayout mLayout_MaidAround;
    @BindView(R.id.lo_your_tasks)
    RelativeLayout mLayout_YourTasks;
    @BindView(R.id.lo_history)
    RelativeLayout mLayout_History;
    @BindView(R.id.ic_text_view_more)
    IconTextView iconTextViewMore;
    @BindView(R.id.txt_work_management)
    TextView txt_work_management;
    @BindView(R.id.txt_work_management_history)
    TextView txt_work_management_history;
    @BindView(R.id.nearbyJob)
    TextView txt_nearbyJob;
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
        stopService(new Intent(HomeActivity.this,BadgeIntentService.class));
        // setup toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        txtHome_title_toothbar.setText(getResources().getString(R.string.home_home));
        sessionManagerForLanguage = new SessionManagerForLanguage(this);
        String lang = sessionManagerForLanguage.getLanguage();
        if (lang.equals("Tiếng Việt")) {
            txt_nearbyJob.setText(changeCharInPosition(setTitle(txt_nearbyJob.getText().toString(), 2), '\n', txt_nearbyJob.getText().toString()));
            txt_work_management.setText(changeCharInPosition(setTitle(txt_work_management.getText().toString(), 2), '\n', txt_work_management.getText().toString()));
            txt_work_management_history.setText(changeCharInPosition(setTitle(txt_work_management_history.getText().toString(), 2), '\n', txt_work_management_history.getText().toString()));
        } else {
            txt_nearbyJob.setText(changeCharInPosition(setTitle(txt_nearbyJob.getText().toString(), 1), '\n', txt_nearbyJob.getText().toString()));
            txt_work_management.setText(changeCharInPosition(setTitle(txt_work_management.getText().toString(), 1), '\n', txt_work_management.getText().toString()));
            txt_work_management_history.setText(changeCharInPosition(setTitle(txt_work_management_history.getText().toString(), 1), '\n', txt_work_management_history.getText().toString()));
        }

        // on click
        mLayout_MaidAround.setOnClickListener(this);
        mLayout_YourTasks.setOnClickListener(this);
        mLayout_History.setOnClickListener(this);

        mHomePresenter = new HomePresenter(this);
        mHomePresenter.requestCheckToken();
        addInits();
        addEvents();
        sessionManagerUser = new SessionManagerUser(HomeActivity.this);
    }

    public void addInits() {
        iconTextViewMore = (IconTextView) findViewById(R.id.ic_text_view_more);
    }

    public void addEvents() {

        iconTextViewMore.setOnClickListener(new View.OnClickListener() {
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
        stopService(new Intent(HomeActivity.this,BadgeIntentService.class));
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

    private void ShowToast(String msg) {
        Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
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

    private int setTitle(String title, int positionSpace) {
        int i = 0, spaceCount = 0;
        while (i < title.length() && spaceCount < positionSpace) {
            if (title.charAt(i) == ' ') {
                spaceCount++;
            }
            i++;
        }
        return i - 1;
    }

    public String changeCharInPosition(int position, char ch, String str) {
        char[] charArray = str.toCharArray();
        charArray[position] = ch;
        return new String(charArray);
    }
}
