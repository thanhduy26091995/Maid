package com.hbbsolution.maid.workmanager.listworkmanager.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.utils.ShowAlertDialog;
import com.hbbsolution.maid.utils.WorkTimeValidate;
import com.hbbsolution.maid.workmanager.adapter.JobPostAdapter;
import com.hbbsolution.maid.workmanager.detailworkmanager.model.JobPendingResponse;
import com.hbbsolution.maid.workmanager.detailworkmanager.view.DetailJobPendingActivity;
import com.hbbsolution.maid.workmanager.listworkmanager.model.workmanager.Datum;
import com.hbbsolution.maid.workmanager.listworkmanager.model.workmanager.WorkManagerResponse;
import com.hbbsolution.maid.workmanager.listworkmanager.presenter.WorkManagerPresenter;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

import static com.hbbsolution.maid.R.id.progressPost;

/**
 * Created by tantr on 6/1/2017.
 */

public class JobPendingFragment extends Fragment implements WorkManagerView {
    private String idProcess = "000000000000000000000003";

    private View rootView;
    private LinearLayout lnNoData;
    private WorkManagerPresenter mWorkManagerPresenter;
    private List<Datum> mJobList = new ArrayList<>();
    private List<Datum> mJobListExpire = new ArrayList<>();
    private List<Datum> mJobListPending = new ArrayList<>();
    private JobPostAdapter mJobPostAdapter;
    private RecyclerView mRecycler;
    private ProgressBar progressBar;
    private SwipeRefreshLayout mSwipeRefreshLayoutSale;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_job_pending, container, false);

            mRecycler = (RecyclerView) rootView.findViewById(R.id.recycler_post);
            progressBar = (ProgressBar) rootView.findViewById(progressPost);
            lnNoData = (LinearLayout) rootView.findViewById(R.id.lnNoData);

            mSwipeRefreshLayoutSale = (SwipeRefreshLayout) rootView.findViewById(R.id.swip_refresh_job_post);

            mWorkManagerPresenter = new WorkManagerPresenter(this);
            progressBar.setVisibility(View.VISIBLE);

            mWorkManagerPresenter.getInfoWorkList(idProcess);

            mSwipeRefreshLayoutSale.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            mWorkManagerPresenter.getInfoWorkList(idProcess);
                            mSwipeRefreshLayoutSale.setRefreshing(false);
                        }
                    }, 1500);
                }
            });
        } else {
            ViewGroup parent = (ViewGroup) container.getParent();
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void getInfoJob(WorkManagerResponse mExample) {
        progressBar.setVisibility(View.GONE);

        mJobListPending.clear();
        mJobListExpire.clear();

//        EventBus.getDefault().postSticky(mExample.getData().size());
        mJobList = mExample.getData();
        if (mJobList.size() > 0) {
            lnNoData.setVisibility(View.GONE);
            mRecycler.setVisibility(View.VISIBLE);
            mRecycler.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());

            for (Datum job : mJobList){
                if (!WorkTimeValidate.compareDays(job.getInfo().getTime().getEndAt()))
                {
                    mJobListExpire.add(job);
                }
                else
                {
                    mJobListPending.add(job);
                }
            }
            mJobListPending.addAll(mJobListExpire);
            mJobList.clear();
            mJobList.addAll(mJobListPending);

            mJobPostAdapter = new JobPostAdapter(getActivity(), mJobList, 2);
            mRecycler.setLayoutManager(linearLayoutManager);
            mRecycler.setAdapter(mJobPostAdapter);

            mJobPostAdapter.setCallback(new JobPostAdapter.Callback() {
                @Override
                public void onItemClickDetail(Datum mDatum) {
                    Intent itDetailJobPost = new Intent(getActivity(), DetailJobPendingActivity.class);
                    itDetailJobPost.putExtra("mDatum", mDatum);
                    startActivity(itDetailJobPost);
                }

                @Override
                public void onItemClickDelete(Datum mDatum) {
                    progressBar.setVisibility(View.GONE);
                    mWorkManagerPresenter.deleteJob(mDatum.getId(), mDatum.getStakeholders().getOwner().getId());
                }

                @Override
                public void onItemLongClick(final Datum mDatum) {
//                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
//                    alertDialog.setCancelable(false);
//                    alertDialog.setTitle(getResources().getString(R.string.notification));
//                    alertDialog.setMessage(getResources().getString(R.string.notification_del_job_post));
//                    alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            progressBar.setVisibility(View.GONE);
//                            mWorkManagerPresenter.deleteJob(mDatum.getId(), mDatum.getStakeholders().getOwner().getId());
//                        }
//                    });
//                    alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//
//                        }
//                    });
//                    alertDialog.show();
                }
            });
        } else {
            lnNoData.setVisibility(View.VISIBLE);
            mRecycler.setVisibility(View.GONE);
        }
    }

    @Override
    public void displayNotifyJobPost(JobPendingResponse isJobPost) {
        progressBar.setVisibility(View.GONE);
        if (isJobPost.getStatus()) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
            alertDialog.setCancelable(false);
            alertDialog.setTitle(getResources().getString(R.string.notification));
            alertDialog.setMessage(getResources().getString(R.string.notification_pass_del_job_post));
            alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EventBus.getDefault().postSticky(true);
                    EventBus.getDefault().postSticky("1");

                    getActivity().finish();
                    getActivity().overridePendingTransition(0, 0);
                    getActivity().startActivity(getActivity().getIntent());
                    getActivity().overridePendingTransition(0, 0);

                }
            });
            alertDialog.show();
        } else {
            ShowAlertDialog.showAlert(isJobPost.getMessage(), getContext());
        }
    }

    @Override
    public void getError() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void connectServerFail() {
        progressBar.setVisibility(View.GONE);
        //ShowAlertDialog.showAlert(getResources().getString(R.string.connection_error), getActivity());
    }
}
