package com.hbbsolution.maid.history.inteface;

import com.hbbsolution.maid.base.ConnectionInterface;
import com.hbbsolution.maid.history.model.work.WorkHistory;

import java.util.List;

/**
 * Created by Administrator on 18/05/2017.
 */

public interface WorkHistoryView extends ConnectionInterface{
    void getInfoWorkHistory(List<WorkHistory> listWorkHistory, int pages);
    void getMoreInfoWorkHistory(List<WorkHistory> listWorkHistory);
    void getInfoWorkHistoryTime(List<WorkHistory> listWorkHistory, String startAt, String endAt, int pages);
    void getMoreInfoWorkHistoryTime(List<WorkHistory> listWorkHistory, String startAt, String endAt);
    void getError();
}
