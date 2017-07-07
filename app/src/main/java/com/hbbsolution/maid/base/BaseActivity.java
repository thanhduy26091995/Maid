package com.hbbsolution.maid.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.utils.ShowSnackbar;

/**
 * Created by buivu on 04/07/2017.
 */

public class BaseActivity extends AppCompatActivity {
    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void checkConnectionInterner() {
        if (!InternetConnection.getInstance().isOnline(this)) {
            ShowSnackbar.showSnack(this, getResources().getString(R.string.no_internet));
        }
    }

    protected void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    protected void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
