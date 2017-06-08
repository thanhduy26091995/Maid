package com.hbbsolution.maid.api;

<<<<<<< Updated upstream

import com.hbbsolution.maid.history.model.WorkHistoryResponse;
import com.hbbsolution.maid.workmanager.listworkmanager.model.workmanager.WorkManagerResponse;
=======
import com.hbbsolution.maid.model.task_around.TaskAroundResponse;
>>>>>>> Stashed changes

import retrofit2.Call;
import retrofit2.http.GET;
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

}
