package com.hbbsolution.maid.home.job_near_by;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.base.ImageLoader;
import com.hbbsolution.maid.home.list_job.view.ListJobActivity;
import com.hbbsolution.maid.model.task_around.TaskData;
import com.hbbsolution.maid.utils.Constants;

import java.util.List;

/**
 * Created by buivu on 06/06/2017.
 */

public class JobNearByAdapter extends RecyclerView.Adapter<JobNearByAdapter.JobNearByViewHolder> {

    private Activity activity;
    private List<TaskData> taskArounds;
    private Double lat, lng;
    private Integer maxDistance;

    public JobNearByAdapter(Activity activity, List<TaskData> taskArounds) {
        this.activity = activity;
        this.taskArounds = taskArounds;
    }

    public JobNearByAdapter(Activity activity, List<TaskData> taskArounds, Double lat, Double lng, Integer maxDistance) {
        this.activity = activity;
        this.taskArounds = taskArounds;
        this.lat = lat;
        this.lng = lng;
        this.maxDistance = maxDistance;
    }

    @Override
    public JobNearByViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(activity, R.layout.item_job_near_by, null);
        return new JobNearByViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(JobNearByViewHolder holder, int position) {
        final TaskData taskAround = taskArounds.get(position);
        //load data
        holder.txtNumber.setText(String.format("%d công việc", taskAround.getCount()));
        holder.txtName.setText(taskAround.getJobTypeInfo().getName());
        ImageLoader.getInstance().loadImageOther(activity, taskAround.getJobTypeInfo().getImage(), holder.imgIcon);
        //event click item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ListJobActivity.class);
                intent.putExtra(Constants.LAT, lat);
                intent.putExtra(Constants.LNG, lng);
                intent.putExtra(Constants.MAX_DISTANCE, maxDistance);
                intent.putExtra(Constants.WORK_ID, taskAround.getJobTypeInfo().getId());
                intent.putExtra(Constants.WORK_NAME, taskAround.getJobTypeInfo().getName());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskArounds.size();
    }

    public class JobNearByViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgIcon;
        public TextView txtName, txtNumber;

        public JobNearByViewHolder(View itemView) {
            super(itemView);

            imgIcon = (ImageView) itemView.findViewById(R.id.img_icon_job);
            txtName = (TextView) itemView.findViewById(R.id.txt_job_name);
            txtNumber = (TextView) itemView.findViewById(R.id.txt_job_count);
        }
    }
}
