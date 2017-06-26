package com.hbbsolution.maid.history.activity;

import com.hbbsolution.maid.model.choose_work.ChooseWorkResponse;

/**
 * Created by buivu on 26/06/2017.
 */

public interface HistoryView {
    void directConfirm(ChooseWorkResponse chooseWorkResponse);

    void getError(String error);
}
