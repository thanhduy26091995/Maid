package com.hbbsolution.maid.history.activity;

import com.hbbsolution.maid.base.ConnectionInterface;
import com.hbbsolution.maid.history.model.direct_bill.DirectBillResponse;
import com.hbbsolution.maid.model.choose_work.ChooseWorkResponse;

/**
 * Created by buivu on 04/07/2017.
 */

public interface DetailWorkView extends ConnectionInterface{
    void getDirectBill(DirectBillResponse directBillResponse);
    void directConfirm(ChooseWorkResponse chooseWorkResponse);

    void cancelDirectConfirm(ChooseWorkResponse chooseWorkResponse);
    void getError(String error);
}
