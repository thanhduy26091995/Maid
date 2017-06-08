package com.hbbsolution.maid.api;


import com.hbbsolution.maid.history.model.OwnerHistoryResponse;
import com.hbbsolution.maid.history.model.WorkHistoryResponse;
import com.hbbsolution.maid.model.task_around.TaskAroundResponse;
import com.hbbsolution.maid.more.duy_nguyen.model.ReportResponse;
import com.hbbsolution.maid.workmanager.listworkmanager.model.workmanager.WorkManagerResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by buivu on 04/05/2017.
 */

public interface ApiInterface {
    @GET("more/getTaskAround")
    Call<TaskAroundResponse> getAllTask(@Query("lat") Double lat, @Query("lng") Double lng, @Query("maxDistance") Integer maxDistance);

    @GET("maid/getAllTasks")
    Call<WorkManagerResponse> getInfo(@Query("process") String idProcess, @Query("sortByTaskTime") boolean isSortByTaskTime);

    @GET("maid/getHistoryTasks")
    Call<WorkHistoryResponse> getInfoWorkHistory(@Query("startAt") String startAt, @Query("endAt") String endAt, @Query("page") int page);

    @GET("maid/getAllWorkedOwner")
    Call<OwnerHistoryResponse> getInfoOwnerHistory(@Query("startAt") String startAt, @Query("endAt") String endAt);

    @FormUrlEncoded
    @POST("maid/report")
    Call<ReportResponse> reportOwner(@Field("toId") String toId, @Field("content") String content);

}
