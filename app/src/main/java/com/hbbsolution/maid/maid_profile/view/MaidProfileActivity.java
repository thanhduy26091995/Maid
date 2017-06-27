package com.hbbsolution.maid.maid_profile.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hbbsolution.maid.R;
import com.hbbsolution.maid.base.IconTextView;
import com.hbbsolution.maid.base.ImageLoader;
import com.hbbsolution.maid.history.model.work.WorkHistory;
import com.hbbsolution.maid.utils.EndlessRecyclerViewScrollListener;
import com.hbbsolution.maid.utils.SessionManagerUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.blurry.Blurry;

/**
 * Created by buivu on 15/05/2017.
 */

public class MaidProfileActivity extends AppCompatActivity /*implements MaidProfileView, View.OnClickListener, AppBarLayout.OnOffsetChangedListener*/ {

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
//    @BindView(R.id.txtNoComment)
//    TextView txtNoComment;

    //    private MaidProfilePresenter mMaidProfilePresenter;
//    private List<Doc> commentList = new ArrayList<>();
//    private ListCommentAdapter listCommentAdapter;
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

    private static final int REPORT = 0;

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
        loadData();

        // mMaidProfilePresenter = new MaidProfilePresenter(this);

//        appBarLayout.addOnOffsetChangedListener(this);
//        //event click
//        lo_ChosenMaidInfo.setOnClickListener(this);
//        txtBackInfoMaid.setOnClickListener(this);
//        linearReportMaid.setOnClickListener(this);

        list = new ArrayList<>();
        list.add("http://res.cloudinary.com/einzweidrei2/image/upload/v1494818990/don_dep_nha_z2lny1.png");
        list.add("http://res.cloudinary.com/einzweidrei2/image/upload/v1494818997/nau_an_copy_ogjsu6.png");
        list.add("http://res.cloudinary.com/einzweidrei2/image/upload/v1494818990/don_dep_nha_z2lny1.png");
        list.add("http://res.cloudinary.com/einzweidrei2/image/upload/v1494818997/nau_an_copy_ogjsu6.png");
        list.add("http://res.cloudinary.com/einzweidrei2/image/upload/v1494818997/nau_an_copy_ogjsu6.png");
        list.add("http://res.cloudinary.com/einzweidrei2/image/upload/v1494818990/don_dep_nha_z2lny1.png");
//        list.add("http://res.cloudinary.com/einzweidrei2/image/upload/v1494818997/nau_an_copy_ogjsu6.png");
//        list.add("http://res.cloudinary.com/einzweidrei2/image/upload/v1494818990/don_dep_nha_z2lny1.png");


//        mMaidInfo = (Maid) getIntent().getSerializableExtra("maid");
//        datum = (MaidHistory) getIntent().getSerializableExtra("helper");
        workHistory = (WorkHistory) getIntent().getSerializableExtra("work");
        isChosenMaidFromRecruitment = getIntent().getBooleanExtra("chosenMaidFromListRecruitment", false);
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
        txtPrice.setText(String.format("%s VND", dataHashMap.get(SessionManagerUser.KEY_PRICE)));
        txtPhoneInfoMaid.setText(dataHashMap.get(SessionManagerUser.KEY_PHONE));
        ImageLoader.getInstance().loadImageAvatar(MaidProfileActivity.this, dataHashMap.get(SessionManagerUser.KEY_IMAGE),
                img_avatarMaid);

        // from Bitmap
        Glide.with(MaidProfileActivity.this)
                .load(dataHashMap.get(SessionManagerUser.KEY_IMAGE))
                .asBitmap()
                .error(R.drawable.avatar)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Blurry.with(MaidProfileActivity.this)
                                .radius(10)
                                .from(resource)
                                .into(imgBlurImage);
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
