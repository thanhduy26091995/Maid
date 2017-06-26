package com.hbbsolution.maid.api;


import com.hbbsolution.maid.history.model.owner.OwnerHistoryResponse;
import com.hbbsolution.maid.history.model.work.WorkHistoryResponse;
import com.hbbsolution.maid.model.choose_work.ChooseWorkResponse;
import com.hbbsolution.maid.model.geocodemap.GeoCodeMapResponse;
import com.hbbsolution.maid.model.task.TaskResponse;
import com.hbbsolution.maid.model.task_around.TaskAroundResponse;
import com.hbbsolution.maid.more.duy_nguyen.model.ReportResponse;
import com.hbbsolution.maid.more.duy_nguyen.model.StatisticResponse;
import com.hbbsolution.maid.more.phuc_tran.model.AboutResponse;
import com.hbbsolution.maid.more.phuc_tran.model.ContactResponse;
import com.hbbsolution.maid.more.phuc_tran.model.ForgotPassResponse;
import com.hbbsolution.maid.more.viet_pham.model.signin_signup.BodyResponse;
import com.hbbsolution.maid.workmanager.detailworkmanager.model.JobPendingResponse;
import com.hbbsolution.maid.workmanager.listworkmanager.model.workmanager.WorkManagerResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by buivu on 04/05/2017.
 */

public interface ApiInterface {
    @GET("more/getTaskAround")
    Call<TaskAroundResponse> getAllTask(@Query("lat") Double lat, @Query("lng") Double lng, @Query("maxDistance") Integer maxDistance);

    @GET("more/getTaskByWork")
    Call<TaskResponse> getTaskByWork(@Query("lat") Double lat, @Query("lng") Double lng, @Query("maxDistance") Integer maxDistance,
                                     @Query("work") String workId, @Query("page") int page);

    @GET("maid/getAllTasks")
    Call<WorkManagerResponse> getInfo(@Query("process") String idProcess, @Query("sortByTaskTime") boolean isSortByTaskTime);

    @GET("maid/getHistoryTasks")
    Call<WorkHistoryResponse> getInfoWorkHistory(@Query("startAt") String startAt, @Query("endAt") String endAt, @Query("page") int page);

    @GET("maid/getAllWorkedOwner")
    Call<OwnerHistoryResponse> getInfoOwnerHistory(@Query("startAt") String startAt, @Query("endAt") String endAt);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "task/cancel", hasBody = true)
    Call<JobPendingResponse> deleteJob(@Field("id") String idTask, @Field("maidId") String ownerId);

    @FormUrlEncoded
    @POST("maid/report")
    Call<ReportResponse> reportOwner(@Field("toId") String toId, @Field("content") String content);

    @GET
    Call<GeoCodeMapResponse> getLocaltionAddress(@Url String url, @Query("address") String address);

    @Multipart
    @POST("auth/maid/login")
    Call<BodyResponse> signInAccount(@Part("username") RequestBody username,
                                     @Part("password") RequestBody password,
                                     @Part("device_token") RequestBody deviceToken
    );

    @FormUrlEncoded
    @POST("task/reserve")
    Call<ChooseWorkResponse> chooseWork(@Field("id") String taskId);

    @FormUrlEncoded
    @POST("task/acceptRequest")
    Call<JobPendingResponse> accceptJobRequested(@Field("id") String taskId, @Field("ownerId") String ownerId);

    @FormUrlEncoded
    @POST("task/denyRequest")
    Call<JobPendingResponse> refuseJobRequested(@Field("id") String taskId, @Field("ownerId") String ownerId);

    @GET("maid/getTaskOfOwner")
    Call<WorkHistoryResponse> getListWorkByMaid(@Query("owner") String idOwner, @Query("page") int page);

    @GET("more/getGV24HInfo")
    Call<AboutResponse> getAbout(@Query("id") String idTask);

    @GET("more/getContact")
    Call<ContactResponse> getContact();

    @GET("maid/statistical")
    Call<StatisticResponse> getStatistic(@Query("startAt") String startAt, @Query("endAt") String endAt);

    @FormUrlEncoded
    @POST("maid/forgotPassword")
    Call<ForgotPassResponse> forgotPassword(@Field("email") String email,
                                            @Field("username") String username);

    @FormUrlEncoded
    @POST("payment/payDirectConfirm")
    Call<ChooseWorkResponse> directConfirm(@Field("billId") String billId);

}
