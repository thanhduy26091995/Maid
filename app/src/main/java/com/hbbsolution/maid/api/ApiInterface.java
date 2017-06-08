package com.hbbsolution.maid.api;


import com.hbbsolution.maid.history.model.WorkHistoryResponse;
import com.hbbsolution.maid.workmanager.listworkmanager.model.workmanager.WorkManagerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by buivu on 04/05/2017.
 */

public interface ApiInterface {
    @GET("maid/getAllTasks")
    Call<WorkManagerResponse> getInfo(@Query("process") String idProcess, @Query("sortByTaskTime") boolean isSortByTaskTime);

    @GET("maid/getHistoryTasks")
    Call<WorkHistoryResponse> getInfoWorkHistory(@Query("startAt") String startAt, @Query("endAt") String endAt, @Query("page") int page);

}
