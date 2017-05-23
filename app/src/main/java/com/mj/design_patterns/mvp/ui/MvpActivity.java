package com.mj.design_patterns.mvp.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.mj.design_patterns.R;
import com.mj.design_patterns.mvp.adapter.NewsListAdapter;
import com.mj.design_patterns.mvp.base.BaseMvpActivity;
import com.mj.design_patterns.mvp.bean.NewsEntity;
import com.mj.design_patterns.mvp.mvp.p.NewsListPresenterImpl;
import com.mj.design_patterns.mvp.mvp.v.INewsListView;
import com.mj.design_patterns.utils.PromptDialog;
import com.mj.design_patterns.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by mj
 * on 2017/5/22.
 */

public class MvpActivity extends BaseMvpActivity<INewsListView, NewsListPresenterImpl> implements INewsListView, XRecyclerView.LoadingListener {
    /**
     * 首次加载
     */
    public static final int FIRST_LOAD = 0;
    /**
     * 刷新
     */
    public static final int REFRESH = 1;
    /**
     * 加载更多
     */
    public static final int LOAD_MORE = 2;
    /**
     * recyclerView
     */
    @BindView(R.id.mvp_list_view)
    XRecyclerView recyclerView;
    /**
     * adapter
     */
    NewsListAdapter adapter;
    /**
     * 页数
     */
    private int pageNum = 1;

    @Override
    public NewsListPresenterImpl initPresenter() {
        return new NewsListPresenterImpl();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mvp;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initAdapter();
        firstLoadData();
    }

    /**
     * 初始化adapter
     */
    private void initAdapter() {
        LinearLayoutManager lm = new LinearLayoutManager(context);
        recyclerView.setLoadingListener(this);
        recyclerView.setLayoutManager(lm);
        recyclerView.setArrowImageView(R.mipmap.pull_down_arrow);
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallPulseSync);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulseSync);

        adapter = new NewsListAdapter();
        recyclerView.setAdapter(adapter);
    }

    /**
     * 首次加载数据
     */
    private void firstLoadData() {
        presenter.loadNewsList(FIRST_LOAD, pageNum);
    }

    /**
     * 刷新数据
     */
    private void refreshData() {
        pageNum = 1;
        presenter.loadNewsList(REFRESH, pageNum);
    }

    /**
     * 加载更多
     */
    private void loadMoreData() {
        pageNum++;
        presenter.loadNewsList(LOAD_MORE, pageNum);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        refreshData();
    }

    /**
     * 加载更多
     */

    @Override
    public void onLoadMore() {
        loadMoreData();
    }

    /**
     * 加载成功回调
     *
     * @param data     列表数据
     * @param loadType 加载类型
     */
    @Override
    public void loadSuccess(List<NewsEntity> data, int loadType) {
        switch (loadType) {
            case FIRST_LOAD:
                adapter.refreshData(data);
                recyclerView.refreshComplete();
                break;
            case REFRESH:
                adapter.refreshData(data);
                recyclerView.refreshComplete();
                break;
            case LOAD_MORE:
                adapter.loadMoreData(data);
                recyclerView.loadMoreComplete();
                break;
        }
    }

    /**
     * 加载出错
     *
     * @param msg 错误信息
     */
    @Override
    public void showError(String msg) {
        ToastUtils.show(context, msg);
        if (pageNum > 1) {
            pageNum--;
        }
        recyclerView.refreshComplete();
        recyclerView.loadMoreComplete();
    }

    /**
     * 展示加载进度
     *
     * @param msg 加载信息
     */
    @Override
    public void showLoading(String msg) {
        PromptDialog.getInstance().show(context,msg);
    }

    /**
     * 关闭加载进度
     */
    @Override
    public void hideLoading() {
        PromptDialog.getInstance().close();
    }

}
