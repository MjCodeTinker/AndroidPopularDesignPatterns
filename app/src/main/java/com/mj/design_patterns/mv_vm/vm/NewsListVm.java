package com.mj.design_patterns.mv_vm.vm;

import android.content.Context;

import com.mj.design_patterns.databinding.ActivityMvVmBinding;
import com.mj.design_patterns.mv_vm.adapter.NewsListAdapter;
import com.mj.design_patterns.mv_vm.bean.NewsEntity;
import com.mj.design_patterns.mv_vm.m.biz.INewsListModel;
import com.mj.design_patterns.mv_vm.m.impl.NewsListModelBiz;
import com.mj.design_patterns.utils.PromptDialog;

import java.util.List;

/**
 * Created by mj
 * on 2017/5/22.
 */

public class NewsListVm implements INewsListModel.LoadResponse {
    /**
     * 首次加载
     */
    private final int FIRST_LOAD = 0;
    /**
     * 下拉刷新
     */
    private final int REFRESH = 1;
    /**
     * 加载更多
     */
    private final int LOAD_MORE = 2;
    /**
     * binding
     */
    private ActivityMvVmBinding binding;
    /**
     * adapter
     */
    private NewsListAdapter adapter;
    /**
     * 加载列表数据业务逻辑
     */
    private NewsListModelBiz newsListModelBiz;
    /**
     * 页数
     */
    private int pageNum = 1;
    /**
     * context
     */
    private Context context;

    public NewsListVm(Context context, ActivityMvVmBinding binding, NewsListAdapter adapter) {
        this.context = context;
        this.binding = binding;
        this.adapter = adapter;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        newsListModelBiz = new NewsListModelBiz();
        firstLoadData();
    }

    /**
     * 首次加载
     */
    private void firstLoadData() {
        PromptDialog.getInstance().show(context, "加载中...");
        newsListModelBiz.load(FIRST_LOAD, pageNum, this);
    }

    /**
     * 刷新数据
     */
    public void setRefreshData() {
        pageNum = 1;
        newsListModelBiz.load(REFRESH, pageNum, this);
    }

    /**
     * 加载更多
     */
    public void loadMoreData() {
        pageNum++;
        newsListModelBiz.load(LOAD_MORE, pageNum, this);
    }

    @Override
    public void loadSuccess(List<NewsEntity> data, int loadType) {
        switch (loadType) {
            case FIRST_LOAD:
                adapter.refreshData(data);
                binding.activityMvVmList.refreshComplete();
                break;
            case REFRESH:
                adapter.refreshData(data);
                binding.activityMvVmList.refreshComplete();
                break;
            case LOAD_MORE:
                adapter.loadMoreData(data);
                binding.activityMvVmList.loadMoreComplete();
                break;
        }
        PromptDialog.getInstance().close();
    }

    @Override
    public void loadFailure(String msg) {
        // 加载失败后的提示
        if (pageNum > 1) {
            pageNum--;
        }
        PromptDialog.getInstance().close();
        binding.activityMvVmList.refreshComplete();
        binding.activityMvVmList.loadMoreComplete();
    }

}
