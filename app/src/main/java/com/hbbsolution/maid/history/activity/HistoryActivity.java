package com.hbbsolution.maid.history.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hbbsolution.maid.R;
import com.hbbsolution.maid.history.fragment.HistoryViewPagerFragment;
import com.hbbsolution.maid.history.presenter.HistoryPresenter;
import com.hbbsolution.maid.model.choose_work.ChooseWorkResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity extends AppCompatActivity implements HistoryView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout_history)
    TabLayout tabLayoutHistory;
    @BindView(R.id.view_pager_history)
    ViewPager viewPagerHistory;
    private HistoryViewPagerFragment historyViewPagerFragment;
    private Integer tabMore, flag;
    private String bill;
    private HistoryPresenter historyPresenter;
    private AlertDialog.Builder alertDialogConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        setToolbar();
        historyViewPagerFragment = new HistoryViewPagerFragment(getSupportFragmentManager(), this, 2);
        viewPagerHistory.setAdapter(historyViewPagerFragment);
        viewPagerHistory.setOffscreenPageLimit(3);
        tabLayoutHistory.setupWithViewPager(viewPagerHistory);
        tabMore = getIntent().getIntExtra("tabMore", 0);
        flag = getIntent().getIntExtra("flag", 0);
        bill = getIntent().getStringExtra("bill");
        historyPresenter = new HistoryPresenter(this);
        if (tabMore != null && flag != null) {
            viewPagerHistory.setCurrentItem(tabMore);

            if (flag == 1) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(HistoryActivity.this);
                alertDialog.setCancelable(false);
                alertDialog.setTitle("Thông báo");
                alertDialog.setMessage("Hoàn tất công việc");
                alertDialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.show().dismiss();
                    }
                });
                alertDialog.show();
            } else if (flag == 2) {
                alertDialogConfirm = new AlertDialog.Builder(HistoryActivity.this);
                alertDialogConfirm.setCancelable(false);
                alertDialogConfirm.setTitle("Hoàn tất công việc");
                alertDialogConfirm.setMessage("Vui lòng xác nhận tiền mặt bằng cách nhấn " + "Ok");
                alertDialogConfirm.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        historyPresenter.directConfirm(bill);
                    }
                });
                alertDialogConfirm.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialogConfirm.show().dismiss();
                    }
                });
                alertDialogConfirm.show();
            }
        }
    }

    public void setToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    @Override
    public void directConfirm(ChooseWorkResponse chooseWorkResponse) {
        alertDialogConfirm.create().dismiss();
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HistoryActivity.this);
        alertDialogBuilder.setMessage("Xác nhận thành công");
        alertDialogBuilder.setPositiveButton(getResources().getText(R.string.okAlert),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        alertDialogBuilder.create().dismiss();
                    }

                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public void getError(String error) {
        alertDialogConfirm.create().dismiss();
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HistoryActivity.this);
        alertDialogBuilder.setMessage("Xác nhận thất bại");
        alertDialogBuilder.setPositiveButton(getResources().getText(R.string.okAlert),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        alertDialogBuilder.create().dismiss();
                    }

                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
