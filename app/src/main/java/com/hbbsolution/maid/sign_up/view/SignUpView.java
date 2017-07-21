package com.hbbsolution.maid.sign_up.view;

import com.hbbsolution.maid.sign_up.model.SignUpResponse;

/**
 * Created by buivu on 21/07/2017.
 */

public interface SignUpView{
    void onError(String error);

    void getDataResponse(SignUpResponse signUpResponse);
}
