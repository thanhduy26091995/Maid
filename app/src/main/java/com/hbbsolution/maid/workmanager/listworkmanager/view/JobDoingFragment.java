package com.hbbsolution.maid.workmanager.listworkmanager.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hbbsolution.maid.R;
import com.hbbsolution.maid.home.owner_profile.view.OwnerProfileActivity;
import com.hbbsolution.maid.utils.WorkTimeValidate;
import com.hbbsolution.maid.workmanager.detailworkmanager.presenter.DetailJobPendingPresenter;
import com.hbbsolution.maid.workmanager.detailworkmanager.view.DetailJobPostActivity;
import com.hbbsolution.maid.workmanager.detailworkmanager.view.DetailJobPostView;
import com.hbbsolution.maid.workmanager.listworkmanager.model.workmanager.Datum;
import com.hbbsolution.maid.workmanager.listworkmanager.model.workmanager.WorkManagerResponse;
import com.hbbsolution.maid.workmanager.listworkmanager.presenter.WorkManagerPresenter;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tantr on 6/1/2017.
 */

public class JobDoingFragment extends Fragment implements WorkManagerView, View.OnClickListener {
    private String idProcess = "000000000000000000000004";

    @BindView(R.id.img_avatarJobDoingInfoMiad)
    ImageView img_avatarJobDoingInfoMiad;
    @BindView(R.id.txtNameJobDoingInfoMaid)
    TextView txtNameJobDoingInfoMaid;
    @BindView(R.id.txtAddressJobDoingInfoMaid)
    TextView txtAddressJobDoingInfoMaid;
    @BindView(R.id.img_job_type)
    ImageView img_job_type;
    @BindView(R.id.txtTitleJobDoing)
    TextView txtTitleJobDoing;
    @BindView(R.id.txtTypeJobDoing)
    TextView txtTypeJobDoing;
    @BindView(R.id.txtContentJobDoing)
    TextView txtContentJobDoing;
    @BindView(R.id.txtPriceJobDoing)
    TextView txtPriceJobDoing;
    @BindView(R.id.txtDateJobDoing)
    TextView txtDateJobDoing;
    @BindView(R.id.txtTimeDoWrokJobDoing)
    TextView txtTimeDoWrokJobDoing;
    @BindView(R.id.txtAddressJobDoing)
    TextView txtAddressJobDoing;
    @BindView(R.id.lnNoData)
    LinearLayout lnNoData;
    @BindView(R.id.lo_infoOwner)
    LinearLayout lo_infoOwner;
    @BindView(R.id.progressPost)
    ProgressBar progressPost;

    private View rootView;
    private Datum mDatum;
    private WorkManagerPresenter mWorkManagerPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_job_doing, container, false);
            ButterKnife.bind(this, rootView);

            lo_infoOwner.setOnClickListener(this);
            progressPost.setVisibility(View.VISIBLE);
            mWorkManagerPresenter = new WorkManagerPresenter(this);
            mWorkManagerPresenter.getInfoWorkList(idProcess);

        } else {
            ViewGroup parent = (ViewGroup) container.getParent();
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void getInfoJob(WorkManagerResponse mExample) {
        progressPost.setVisibility(View.GONE);
        if (mExample.getData().size() > 0) {
            lnNoData.setVisibility(View.GONE);
            lo_infoOwner.setVisibility(View.VISIBLE);
            mDatum = mExample.getData().get(0);
            loadInfoWorkDoning(mDatum);
        } else {
            lo_infoOwner.setVisibility(View.GONE);
            lnNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void displayNotifyJobPost(boolean isJobPost) {

    }

    @Override
    public void getError() {
        progressPost.setVisibility(View.GONE);
    }

    private String formatPrice(Integer _Price) {
        String mOutputPrice = null;
        if (_Price != null && _Price != 0) {
            DecimalFormat myFormatter = new DecimalFormat("#,###,##0");
            mOutputPrice = myFormatter.format(_Price);
        } else if (_Price == 0) {
            mOutputPrice = "Tính tiền theo thời gian";
        }
        return mOutputPrice;
    }

    private void loadInfoWorkDoning(Datum mDatum) {
        txtNameJobDoingInfoMaid.setText(mDatum.getStakeholders().getOwner().getInfo().getUsername());
        txtAddressJobDoingInfoMaid.setText(mDatum.getStakeholders().getOwner().getInfo().getAddress().getName());
        txtTitleJobDoing.setText(mDatum.getInfo().getTitle());
        txtTypeJobDoing.setText(mDatum.getInfo().getWork().getName());
        txtContentJobDoing.setText(mDatum.getInfo().getDescription());
        txtPriceJobDoing.setText(formatPrice(mDatum.getInfo().getPrice()));
        txtDateJobDoing.setText(WorkTimeValidate.getDatePostHistory(mDatum.getInfo().getTime().getEndAt()));
        txtAddressJobDoing.setText(mDatum.getInfo().getAddress().getName());
        Glide.with(getContext())
                .load(mDatum.getStakeholders().getOwner().getInfo().getImage())
                .thumbnail(0.5f)
                .error(R.drawable.avatar)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.drawable.avatar)
                .dontAnimate()
                .into(img_avatarJobDoingInfoMiad);
        Picasso.with(getContext())
                .load(mDatum.getInfo().getWork().getImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(img_job_type);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this, rootView).unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lo_infoOwner:
                Intent itInfoOwner = new Intent(getActivity(), OwnerProfileActivity.class);
                itInfoOwner.putExtra("InfoOwner", mDatum.getStakeholders().getOwner().getInfo());
                startActivity(itInfoOwner);
                break;
        }
    }
}
