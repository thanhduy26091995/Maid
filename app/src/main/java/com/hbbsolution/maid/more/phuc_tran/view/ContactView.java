package com.hbbsolution.maid.more.phuc_tran.view;

import com.hbbsolution.maid.more.phuc_tran.model.DataContact;

/**
 * Created by Tư Lầu on 6/22/17.
 */

public interface ContactView {
    void getContactSuccess(DataContact dataContact);
    void getContactFail();
}