package com.hbbsolution.maid.history.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hbbsolution.maid.R;
import com.hbbsolution.maid.history.activity.ListWorkActivity;
import com.hbbsolution.maid.history.model.owner.OwnerHistory;
import com.hbbsolution.maid.home.owner_profile.view.OwnerProfileActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 16/05/2017.
 */

public class HistoryOwnerAdapter extends RecyclerView.Adapter<HistoryOwnerAdapter.RecyclerViewHolder> {
    private Context context;
    private List<OwnerHistory> ownerHistoryList;
    private OwnerHistory ownerHistory;
    private boolean isHis;
    private String time;
    private Date date;
    private int p;

    @Override
    public HistoryOwnerAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_owner, parent, false);
        return new RecyclerViewHolder(view);
    }

    public HistoryOwnerAdapter(Context context, List<OwnerHistory> ownerHistoryList) {
        this.context = context;
        this.ownerHistoryList = ownerHistoryList;
    }

    @Override
    public void onBindViewHolder(HistoryOwnerAdapter.RecyclerViewHolder holder, int position) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");
        ownerHistory = ownerHistoryList.get(position);
        try {
            date = simpleDateFormat.parse(ownerHistory.getTimes().get(0));
            time = dates.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tvName.setText(ownerHistory.getId().getInfo().getName());
        holder.tvDate.setText(time);
        Glide.with(context).load(ownerHistory.getId().getInfo().getImage())
                .thumbnail(0.5f)
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .centerCrop()
                .dontAnimate()
                .into(holder.imgMaid);
    }

    @Override
    public int getItemCount() {
        return ownerHistoryList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        private TextView tvName, tvDate, tvListWork;
        private RatingBar ratingBar;
        private CircleImageView imgMaid;
        private RelativeLayout lo_info_user, lo_ChosenMaid;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            lo_info_user = (RelativeLayout) itemView.findViewById(R.id.rela_info);
            tvName = (TextView) itemView.findViewById(R.id.txt_history_name);
            tvDate = (TextView) itemView.findViewById(R.id.txt_history_date);
            tvListWork = (TextView) itemView.findViewById(R.id.txt_history_list_work);
            imgMaid = (CircleImageView) itemView.findViewById(R.id.img_history_avatar);
            lo_info_user.setOnClickListener(this);
            tvListWork.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rela_info:
                    Intent intent = new Intent(context, OwnerProfileActivity.class);
                    intent.putExtra("InfoOwner", ownerHistoryList.get(getAdapterPosition()).getId().getInfo());
                    intent.putExtra("IsInJobDetail", false);
                    ActivityOptionsCompat historyOption =
                            ActivityOptionsCompat
                                    .makeSceneTransitionAnimation((Activity) context, (View) v.findViewById(R.id.img_history_avatar), "icAvatar");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        context.startActivity(intent, historyOption.toBundle());
                    } else {
                        context.startActivity(intent);
                    }
                    break;
                case R.id.txt_history_list_work:
                    intent = new Intent(context, ListWorkActivity.class);
                    intent.putExtra("idOwner",ownerHistory.getId().getId());
                    context.startActivity(intent);
                    break;
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
