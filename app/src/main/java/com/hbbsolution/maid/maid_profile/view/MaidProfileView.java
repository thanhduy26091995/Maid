package com.hbbsolution.maid.maid_profile.view;


import com.hbbsolution.maid.base.ConnectionInterface;
import com.hbbsolution.maid.maid_profile.model.abilities.AbilitiResponse;
import com.hbbsolution.maid.maid_profile.model.comment_maid.CommentMaidResponse;

/**
 * Created by tantr on 5/22/2017.
 */

public interface MaidProfileView extends ConnectionInterface{

    void getListCommentMaid(CommentMaidResponse mCommentMaidResponse);

    void getMoreListCommentMaid(CommentMaidResponse mCommentMaidResponse);

    void responseChosenMaid(boolean isResponseChosenMaid);

    void getMessager(String error);

    void getAbilities(AbilitiResponse abilitiResponse);
}
