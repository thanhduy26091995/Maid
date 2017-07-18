package com.hbbsolution.maid.history.inteface;

import com.hbbsolution.maid.base.ConnectionInterface;

/**
 * Created by Administrator on 29/05/2017.
 */

public interface CommentHistoryView extends ConnectionInterface{
    void checkCommentSuccess(String message);
    void checkCommentFail();
}
