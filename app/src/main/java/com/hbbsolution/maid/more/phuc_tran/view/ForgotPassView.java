package com.hbbsolution.maid.more.phuc_tran.view;

import com.hbbsolution.maid.more.phuc_tran.model.ForgotPassResponse;

/**
 * Created by Tư Lầu on 6/26/17.
 */

public interface ForgotPassView {
    void getForgotPass(ForgotPassResponse forgotPassResponse);
    void getErrorForgotPass(String error);
}
