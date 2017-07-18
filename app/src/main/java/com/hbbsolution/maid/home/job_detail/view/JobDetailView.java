package com.hbbsolution.maid.home.job_detail.view;

import com.hbbsolution.maid.base.ConnectionInterface;
import com.hbbsolution.maid.model.choose_work.ChooseWorkResponse;

/**
 * Created by buivu on 13/06/2017.
 */

public interface JobDetailView extends ConnectionInterface{
    void chooseWork(ChooseWorkResponse chooseWorkResponse);

    void getError(String error);
}
