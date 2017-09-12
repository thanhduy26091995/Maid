package com.hbbsolution.maid.home.list_job;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.base.ImageLoader;
import com.hbbsolution.maid.home.job_detail.view.JobDetailActivity;
import com.hbbsolution.maid.model.task.TaskData;
import com.hbbsolution.maid.utils.WorkTimeValidate;

import java.util.List;

/**
 * Created by buivu on 08/06/2017.
 */

public class ListJobAdapter extends RecyclerView.Adapter<ListJobAdapter.ListJobViewHolder> {

    private Activity mActivity;
    private List<TaskData> mTaskDatas;
    private long elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds;
    private String date;
    private String startTime, endTime;


    public ListJobAdapter(Activity mActivity, List<TaskData> mTaskDatas) {
        this.mActivity = mActivity;
        this.mTaskDatas = mTaskDatas;
    }

    @Override
    public ListJobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mActivity, R.layout.item_list_job, null);
        return new ListJobViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListJobViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        final TaskData taskData = mTaskDatas.get(position);
        holder.txtJobName.setText(taskData.getInfo().getTitle());

        WorkTimeValidate.setWorkTimeRegister(mActivity, holder.txtTime, taskData.getHistory().getUpdateAt());

        if (taskData.getDist().getCalculated() < 1000) {
            holder.txtJobDistance.setText(String.format("%d m", Math.round(taskData.getDist().getCalculated() * 100) / 100));
        } else {
            holder.txtJobDistance.setText(String.format("%d km", (Math.round(taskData.getDist().getCalculated() / 1000) * 100) / 100));
        }
        holder.txtDate.setText(WorkTimeValidate.getDatePostHistory(taskData.getInfo().getTime().getStartAt()));
        holder.txtDetailTime.setText(WorkTimeValidate.getTimeWorkLanguage(mActivity, taskData.getInfo().getTime().getStartAt()) + " - " + WorkTimeValidate.getTimeWorkLanguage(mActivity, taskData.getInfo().getTime().getEndAt()));
        //load image
        if (taskData.getInfo().getWork().getImage() != "") {
            ImageLoader.getInstance().loadImageOther(mActivity, taskData.getInfo().getWork().getImage(),
                    holder.imgAvatar);
        }
        //event click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, JobDetailActivity.class);
                intent.putExtra("TaskData", taskData);
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTaskDatas.size();
    }

    public class ListJobViewHolder extends RecyclerView.ViewHolder {

        public TextView txtJobName, txtJobDistance, txtTime, txtDate, txtDetailTime;
        public ImageView imgAvatar;

        public ListJobViewHolder(View itemView) {
            super(itemView);
            txtJobDistance = (TextView) itemView.findViewById(R.id.txt_job_distance);
            txtJobName = (TextView) itemView.findViewById(R.id.txt_job_name);
            txtTime = (TextView) itemView.findViewById(R.id.txt_time);
            txtDate = (TextView) itemView.findViewById(R.id.txt_date);
            txtDetailTime = (TextView) itemView.findViewById(R.id.txt_detail_time);
            imgAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
        }
    }
}
