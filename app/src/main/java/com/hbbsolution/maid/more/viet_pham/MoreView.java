package com.hbbsolution.maid.more.viet_pham;


import com.hbbsolution.maid.more.viet_pham.model.signin_signup.BodyResponse;
import com.hbbsolution.maid.more.viet_pham.model.signin_signup.DataUpdateResponse;


/**
 * Created by Administrator on 5/29/2017.
 */

public interface MoreView {
    void displaySignUpAndSignIn(BodyResponse bodyResponse);
    void displayUpdate(DataUpdateResponse dataUpdateResponse);
    void displayError(String error);
    void displayNotFoundLocaltion();

}
