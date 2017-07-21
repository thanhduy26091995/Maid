package com.hbbsolution.maid.sign_up.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.base.BaseActivity;
import com.hbbsolution.maid.base.InternetConnection;
import com.hbbsolution.maid.sign_up.model.SignUpResponse;
import com.hbbsolution.maid.sign_up.presenter.SignUpPresenter;
import com.hbbsolution.maid.utils.EmailValidate;
import com.hbbsolution.maid.utils.PhoneValidate;
import com.hbbsolution.maid.utils.ShowAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 19/07/2017.
 */

public class SignUpActivity extends BaseActivity implements SignUpView, View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_username)
    EditText edtUsername;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_phone_number)
    EditText edtPhoneNumber;
    @BindView(R.id.edt_note)
    EditText edtNote;
    @BindView(R.id.btn_register)
    Button btnRegister;

    private SignUpPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setupComponents();
        presenter = new SignUpPresenter(this);
    }

    private void setupComponents() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        //event click
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnRegister) {
            if (InternetConnection.getInstance().isOnline(this)) {
                register();
            } else {
                ShowAlertDialog.showAlert(getResources().getString(R.string.no_internet), this);
            }
        }
    }

    private void register() {
        boolean result = true;
        String name = edtUsername.getText().toString();
        String phone = edtPhoneNumber.getText().toString();
        String email = edtEmail.getText().toString();
        String note = edtNote.getText().toString();

        //name isEmpty
        if (TextUtils.isEmpty(name)) {
            result = false;
            edtUsername.requestFocus();
            showKeyboard(edtUsername);
            ShowAlertDialog.showAlert(getResources().getString(R.string.sign_up_fill_name), this);
            return;
        }

        //email isEmpty
        if (TextUtils.isEmpty(email)) {
            result = false;
            edtEmail.requestFocus();
            showKeyboard(edtEmail);
            ShowAlertDialog.showAlert(getResources().getString(R.string.sign_up_fill_email), this);
            return;
        }

        //validate email
        if (!EmailValidate.IsOk(email)) {
            result = false;
            edtEmail.requestFocus();
            showKeyboard(edtEmail);
            ShowAlertDialog.showAlert(getResources().getString(R.string.sign_up_email_wrong), this);
            return;
        }

        //phone isEmpty
        if (TextUtils.isEmpty(phone)) {
            result = false;
            edtPhoneNumber.requestFocus();
            showKeyboard(edtPhoneNumber);
            ShowAlertDialog.showAlert(getResources().getString(R.string.sign_up_fill_phone), this);
            return;
        }

        //validate phone number
        if (!PhoneValidate.isPhoneNumberSuccess(phone)) {
            result = false;
            edtPhoneNumber.requestFocus();
            showKeyboard(edtPhoneNumber);
            ShowAlertDialog.showAlert(getResources().getString(R.string.sign_up_length_phone), this);
            return;
        }


        if (result) {
            showProgressDialog();
            presenter.signUp(name, email, phone, note);
        }
    }

    private void showKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    private void confirmBeforeClosing() {
        try {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(getResources().getString(R.string.confirm_message));
            alertDialogBuilder.setPositiveButton(getResources().getText(R.string.confirm_yes),
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            alertDialogBuilder.create().dismiss();
                        }

                    });

            alertDialogBuilder.setNegativeButton(getResources().getText(R.string.confirm_no),
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            alertDialogBuilder.create().dismiss();
                            finish();
                        }

                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } catch (Exception e) {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (edtEmail.getText().length() != 0 || edtPhoneNumber.getText().length() != 0 || edtUsername.getText().length() != 0 ||
                    edtNote.getText().length() != 0) {
                confirmBeforeClosing();
            } else {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (edtEmail.getText().length() != 0 || edtPhoneNumber.getText().length() != 0 || edtUsername.getText().length() != 0 ||
                edtNote.getText().length() != 0) {
            confirmBeforeClosing();
        } else {
            finish();
        }

    }

    @Override
    public void onError(String error) {
        hideProgress();
    }

    @Override
    public void getDataResponse(SignUpResponse signUpResponse) {
        hideProgress();
        if (signUpResponse.isStatus()) {
            try {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage(getResources().getString(R.string.sign_up_notification));
                alertDialogBuilder.setPositiveButton(getResources().getText(R.string.okAlert),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                alertDialogBuilder.create().dismiss();
                                finish();
                            }

                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } catch (Exception e) {

            }
        }
    }


}
