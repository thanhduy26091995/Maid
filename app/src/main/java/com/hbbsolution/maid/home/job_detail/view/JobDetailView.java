package com.hbbsolution.maid.home.job_detail.view;

import com.hbbsolution.maid.model.choose_work.ChooseWorkResponse;

/**
 * Created by buivu on 13/06/2017.
 */

public interface JobDetailView {
    void chooseWork(ChooseWorkResponse chooseWorkResponse);

    void getError(String error);
}
