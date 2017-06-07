package com.hbbsolution.maid.workmanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.workmanager.listworkmanager.model.workmanager.Datum;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by tantr on 6/6/2017.
 */

public class JobPostAdapter extends RecyclerView.Adapter<JobPostAdapter.JobPostViewHolder> {

    private Context context;
    private List<Datum> datumList;
    private Callback callback;
    private int tabJob;

    public JobPostAdapter(Context context, List<Datum> datumList, int  tabJob) {
        this.context = context;
        this.datumList = datumList;
        this.tabJob = tabJob;
    }

    @Override
    public JobPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = View.inflate(context, R.layout.item_job_post, null);
        return new JobPostAdapter.JobPostViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(JobPostViewHolder holder, int position) {
        final Datum mDatum = datumList.get(position);
        holder.txtTitleJobPost.setText(mDatum.getInfo().getTitle());
//        holder.txtDatePostHistory.setText(getDatePostHistory(mDatum.getInfo().getTime().getStartAt()));
        Picasso.with(context).load(mDatum.getInfo().getWork().getImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(holder.imgTypeJobPost);

    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    public class JobPostViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTimePostHistory, txtDatePostHistory, txtTimeDoingPost,
                txtTitleJobPost, txtNumber_request_detail_post, txtExpired, txtType;
        private ImageView imgTypeJobPost;
        private LinearLayout lo_background;

        public JobPostViewHolder(View itemView) {
            super(itemView);
            txtTitleJobPost = (TextView) itemView.findViewById(R.id.txtTitleJobPost);
            txtTimePostHistory = (TextView) itemView.findViewById(R.id.txtTimePostHistory);
            txtDatePostHistory = (TextView) itemView.findViewById(R.id.txtDatePostHistory);
            txtTimeDoingPost = (TextView) itemView.findViewById(R.id.txtTimeDoingPost);
            txtNumber_request_detail_post = (TextView) itemView.findViewById(R.id.txtNumber_request_detail_post);
            imgTypeJobPost = (ImageView) itemView.findViewById(R.id.imgTypeJobPost);
            txtExpired = (TextView) itemView.findViewById(R.id.txtExpired_request_detail_post);
            lo_background = (LinearLayout) itemView.findViewById(R.id.lo_background);
            txtType = (TextView) itemView.findViewById(R.id.txtType);
        }
    }

    public interface Callback {
        void onItemClick(Datum mDatum);
        void onItemLongClick(Datum mDatum);
    }
}
