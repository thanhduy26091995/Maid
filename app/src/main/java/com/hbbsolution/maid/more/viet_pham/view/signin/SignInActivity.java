package com.hbbsolution.maid.more.viet_pham.view.signin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hbbsolution.maid.R;
import com.hbbsolution.maid.api.ApiClient;
import com.hbbsolution.maid.home.job_near_by.view.JobNearByActivity;
import com.hbbsolution.maid.main.view.HomeActivity;
import com.hbbsolution.maid.more.phuc_tran.view.ForgotPassActivity;
import com.hbbsolution.maid.more.viet_pham.MoreView;
import com.hbbsolution.maid.more.viet_pham.model.signin_signup.BodyResponse;
import com.hbbsolution.maid.more.viet_pham.model.signin_signup.DataUpdateResponse;
import com.hbbsolution.maid.more.viet_pham.presenter.SignInPresenter;
import com.hbbsolution.maid.utils.SessionManagerUser;
import com.hbbsolution.maid.utils.ShowAlertDialog;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 5/9/2017.
 */

public class SignInActivity extends AppCompatActivity implements MoreView
//        , GoogleApiClient.OnConnectionFailedListener
{
    @BindView(R.id.toobar)
    Toolbar toolbar;
    @BindView(R.id.bt_work_around_here)
    Button btnWorkAroundHere;
    @BindView(R.id.bt_sign_in)
    Button btnSignIn;
    @BindView(R.id.bt_forget_password)
    Button btnForgetPassword;
    @BindView(R.id.edit_username)
    EditText editUserName;
    @BindView(R.id.edit_password)
    EditText editPassword;
    private SignInPresenter mSignInPresenter;
    private SessionManagerUser sessionManagerUser;
    private HashMap<String, String> hashDataUser = new HashMap<>();

    public static GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private static final int RC_SIGN_IN = 007;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        sessionManagerUser = new SessionManagerUser(this);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addEvents();

        mSignInPresenter = new SignInPresenter(this);
        mProgressDialog = new ProgressDialog(this);
        //      loginGoogle();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addEvents() {
        // Sign up now
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editUserName.getText().toString();
                String password = editPassword.getText().toString();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    ShowAlertDialog.showAlert(getResources().getString(R.string.fill_all_data), SignInActivity.this);
                } else {
                    mProgressDialog.setMessage(getResources().getString(R.string.loading));
                    mProgressDialog.show();
                    mProgressDialog.setCanceledOnTouchOutside(false);

                    String token = FirebaseInstanceId.getInstance().getToken();
                    String realToken = String.format("%s@//@android", token);
                    Log.d("DEVICE_TOKEN", realToken);
                    mSignInPresenter.signIn(username, password, realToken);
                }
            }
        });
        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itForgot = new Intent(SignInActivity.this, ForgotPassActivity.class);
                startActivity(itForgot);
            }
        });

        btnWorkAroundHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMaidNearBy = new Intent(SignInActivity.this, JobNearByActivity.class);
                startActivity(intentMaidNearBy);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    @Override
    public void displaySignUpAndSignIn(BodyResponse bodyResponse) {
        mProgressDialog.dismiss();
        if (bodyResponse.getStatus() == true) {
            //save session
            sessionManagerUser.createLoginSession(bodyResponse.getData());
            hashDataUser = sessionManagerUser.getUserDetails();
            ApiClient.setToken(hashDataUser.get(SessionManagerUser.KEY_TOKEN));
            Log.d("TOKEN", hashDataUser.get(SessionManagerUser.KEY_TOKEN));
            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            ShowAlertDialog.showAlert(bodyResponse.getMessage().toString(), SignInActivity.this);
        }
    }

    @Override
    public void displayUpdate(DataUpdateResponse dataUpdateResponse) {

    }


    @Override
    public void displayError(String error) {
        mProgressDialog.dismiss();
        ShowAlertDialog.showAlert(getResources().getString(R.string.error), SignInActivity.this);

    }

    @Override
    public void displayNotFoundLocaltion() {

    }


}
