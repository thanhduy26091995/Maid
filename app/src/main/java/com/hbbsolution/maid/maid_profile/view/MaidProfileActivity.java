package com.hbbsolution.maid.maid_profile.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.base.IconTextView;
import com.hbbsolution.maid.base.ImageLoader;
import com.hbbsolution.maid.history.model.work.WorkHistory;
import com.hbbsolution.maid.maid_profile.ListCommentAdapter;
import com.hbbsolution.maid.maid_profile.TypeJobAdapter;
import com.hbbsolution.maid.maid_profile.model.abilities.AbilitiResponse;
import com.hbbsolution.maid.maid_profile.model.comment_maid.CommentMaidResponse;
import com.hbbsolution.maid.maid_profile.model.comment_maid.Doc;
import com.hbbsolution.maid.maid_profile.presenter.MaidProfilePresenter;
import com.hbbsolution.maid.utils.EndlessRecyclerViewScrollListener;
import com.hbbsolution.maid.utils.SessionManagerUser;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 15/05/2017.
 */

public class MaidProfileActivity extends AppCompatActivity implements MaidProfileView, AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.lo_toolbar)
    LinearLayout toolbar;
    @BindView(R.id.info_user_appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.recycler_comment)
    RecyclerView mRecycler;
    @BindView(R.id.txtNameInfoMaid)
    TextView txtNameInfoMaid;
    @BindView(R.id.txtGenderInfoMaid)
    TextView txtGenderInfoMaid;
    @BindView(R.id.txtPhoneInfoMaid)
    TextView txtPhoneInfoMaid;
    @BindView(R.id.txtAddressInfoMaid)
    TextView txtAddressInfoMaid;
    @BindView(R.id.ratingInfoMaid)
    RatingBar ratingInfoMaid;
    @BindView(R.id.toolbar_header)
    Toolbar toolbarHeader;
    @BindView(R.id.txtBackInfoMaid)
    IconTextView txtBackInfoMaid;
    @BindView(R.id.img_avatarMaid)
    ImageView img_avatarMaid;
    @BindView(R.id.img_blur_image)
    ImageView imgBlurImage;
    @BindView(R.id.recy_listTypeJob)
    RecyclerView recy_listTypeJob;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.txtPriceInfoMaid)
    TextView txtPrice;

    @BindView(R.id.txtNoComment)
    TextView txtNoComment;

    //    private MaidProfilePresenter mMaidProfilePresenter;
    private List<Doc> commentList = new ArrayList<>();
    private ListCommentAdapter listCommentAdapter;
    //    private Maid mMaidInfo;
    private WorkHistory workHistory;
    //    private MaidHistory datum;
    private EndlessRecyclerViewScrollListener scrollListener;
    private int currentPage = 1;
    private String idTaskProcess;
    private boolean isChosenMaidFromRecruitment = false;

    public static MaidProfileActivity maidProfileActivity;
    private SessionManagerUser mSessionManagerUser;
    private HashMap<String, String> dataHashMap = new HashMap<>();
    private List<String> list;
    public static Activity mMaidProfileActivity = null;
    private MaidProfilePresenter maidProfilePresenter;
    private static final int REPORT = 0;
    private TypeJobAdapter typeJobAdapter;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maid_profile);
        mMaidProfileActivity = this;
        ButterKnife.bind(this);
        //init
        maidProfileActivity = this;
        setSupportActionBar(toolbarHeader);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        mSessionManagerUser = new SessionManagerUser(this);
        maidProfilePresenter = new MaidProfilePresenter(this);
        loadData();
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideProgress() {
        if (progressDialog.isShowing() && progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void loadData() {
        dataHashMap = mSessionManagerUser.getUserDetails();
        txtNameInfoMaid.setText(dataHashMap.get(SessionManagerUser.KEY_NAME));
        txtAddressInfoMaid.setText(dataHashMap.get(SessionManagerUser.KEY_ADDRESS));
        if (Integer.parseInt(dataHashMap.get(SessionManagerUser.KEY_GENDER)) == 0) {
            txtGenderInfoMaid.setText(getResources().getString(R.string.pro_file_gender_male));
        } else {
            txtGenderInfoMaid.setText(getResources().getString(R.string.pro_file_gender_female));
        }
        txtPrice.setText(String.format("%s VND", NumberFormat.getNumberInstance(Locale.GERMANY).format(Integer.parseInt(dataHashMap.get(SessionManagerUser.KEY_PRICE)))));
        txtPhoneInfoMaid.setText(dataHashMap.get(SessionManagerUser.KEY_PHONE));
        ImageLoader.getInstance().loadImageAvatar(MaidProfileActivity.this, dataHashMap.get(SessionManagerUser.KEY_IMAGE),
                img_avatarMaid);
        ratingInfoMaid.setRating((float) Integer.parseInt(dataHashMap.get(SessionManagerUser.KEY_EVALUATION)));
        // from Bitmap
//        Glide.with(MaidProfileActivity.this)
//                .load(dataHashMap.get(SessionManagerUser.KEY_IMAGE))
//                .asBitmap()
//                .error(R.drawable.avatar)
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        Blurry.with(MaidProfileActivity.this)
//                                .radius(10)
//                                .from(resource)
//                                .into(imgBlurImage);
//                    }
//                });
        showProgress();
        //load comment
        maidProfilePresenter.getListComment(dataHashMap.get(SessionManagerUser.KEY_ID), currentPage);
        maidProfilePresenter.getAbilities();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (collapsingToolbarLayout.getHeight() + verticalOffset <= 1.5 * ViewCompat.getMinimumHeight(collapsingToolbarLayout)) {
            toolbar.setVisibility(View.VISIBLE);
            toolbar.animate().alpha(1).setDuration(200);
        } else {

            toolbar.setVisibility(View.GONE);
            toolbar.animate().alpha(0).setDuration(200);
        }
    }

    @Override
    public void getListCommentMaid(CommentMaidResponse mCommentMaidResponse) {
        hideProgress();
        final int pages = mCommentMaidResponse.getData().getPages();
        commentList = mCommentMaidResponse.getData().getDocs();
        if (commentList.size() > 0) {
            mRecycler.setVisibility(View.VISIBLE);
            txtNoComment.setVisibility(View.GONE);
            listCommentAdapter = new ListCommentAdapter(this, commentList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mRecycler.setLayoutManager(layoutManager);
            mRecycler.setHasFixedSize(true);
            mRecycler.setAdapter(listCommentAdapter);
            listCommentAdapter.notifyDataSetChanged();
            scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    if (currentPage < pages) {
                        maidProfilePresenter.getMoreListComment(dataHashMap.get(SessionManagerUser.KEY_ID), currentPage);
                    }
                }
            };
        } else {
            mRecycler.setVisibility(View.GONE);
            txtNoComment.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getMoreListCommentMaid(CommentMaidResponse mCommentMaidResponse) {
        commentList.addAll(mCommentMaidResponse.getData().getDocs());
        currentPage++;
        listCommentAdapter.notifyDataSetChanged();
        mRecycler.post(new Runnable() {
            @Override
            public void run() {
                listCommentAdapter.notifyItemRangeInserted(listCommentAdapter.getItemCount(), commentList.size() - 1);
            }
        });
    }

    @Override
    public void responseChosenMaid(boolean isResponseChosenMaid) {

    }

    @Override
    public void getMessager(String error) {
        hideProgress();
    }

    @Override
    public void getAbilities(AbilitiResponse abilitiResponse) {
        hideProgress();
        if (abilitiResponse.getStatus()) {
            typeJobAdapter = new TypeJobAdapter(this, abilitiResponse.getAbilityList());
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
            recy_listTypeJob.setLayoutManager(layoutManager);
            recy_listTypeJob.setAdapter(typeJobAdapter);
            typeJobAdapter.notifyDataSetChanged();
        }
    }
}
