package com.hbbsolution.maid.history.inteface;

import com.hbbsolution.maid.base.ConnectionInterface;
import com.hbbsolution.maid.history.model.owner.OwnerHistory;

import java.util.List;

/**
 * Created by Administrator on 29/05/2017.
 */

public interface OwnerHistoryView extends ConnectionInterface{
    void getInfoOwnerHistory(List<OwnerHistory> datumList);
    void getInfoOwnerHistoryFail();
}
