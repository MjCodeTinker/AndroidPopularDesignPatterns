package com.mj.design_patterns.mv_vm.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.mj.design_patterns.R;
import com.mj.design_patterns.databinding.ActivityMvVmBinding;
import com.mj.design_patterns.mv_vm.adapter.NewsListAdapter;
import com.mj.design_patterns.mv_vm.base.BaseMvVmActivity;
import com.mj.design_patterns.mv_vm.vm.NewsListVm;

/**
 * Created by mj
 * on 2017/5/22.
 */

public class MvVmActivity extends BaseMvVmActivity<ActivityMvVmBinding> implements XRecyclerView.LoadingListener {

    /**
     * 新闻列表 adapter
     */
    NewsListAdapter adapter;
    /**
     * view model
     */
    NewsListVm newsListVm;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mv_vm;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initAdapter();
        initVM();
    }

    /**
     * 初始化adapter
     */

    private void initAdapter() {
        XRecyclerView xRecyclerView = viewDataBinding.activityMvVmList;

        xRecyclerView.setLoadingListener(this);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        xRecyclerView.setArrowImageView(R.mipmap.pull_down_arrow);
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotate);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);

        adapter = new NewsListAdapter();
        xRecyclerView.setAdapter(adapter);
    }

    /**
     * 初始化viewModel
     */
    private void initVM() {
        newsListVm = new NewsListVm(context,viewDataBinding, adapter);
    }

    @Override
    public void onRefresh() {
        newsListVm.setRefreshData();
    }

    @Override
    public void onLoadMore() {
        newsListVm.loadMoreData();
    }


}
