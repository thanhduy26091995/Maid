package com.hbbsolution.maid.history.inteface;

import com.hbbsolution.maid.base.ConnectionInterface;
import com.hbbsolution.maid.history.model.work.WorkHistory;

import java.util.List;

/**
 * Created by Administrator on 08/06/2017.
 */

public interface ListWorkView extends ConnectionInterface{
    void getInfoListWorkHistory(List<WorkHistory> listWorkHistory, int pages);
    void getMoreInfoListWorkHistory(List<WorkHistory> listWorkHistory);
    void getError();
}