package com.mj.design_patterns.mvp.mvp.p;

import com.mj.design_patterns.mvp.base.BasePresenter;
import com.mj.design_patterns.mvp.bean.NewsEntity;
import com.mj.design_patterns.mvp.mvp.m.INewsListModelBiz;
import com.mj.design_patterns.mvp.mvp.m.NewsListModelImpl;
import com.mj.design_patterns.mvp.mvp.v.INewsListView;
import com.mj.design_patterns.mvp.ui.MvpActivity;

import java.util.List;

/**
 * Created by mj
 * on 2017/5/22.
 */

public class NewsListPresenterImpl extends BasePresenter<INewsListView> implements INewsListPresenterBiz, INewsListModelBiz.LoadResponse {
    /**
     * 加载列表数据的业务逻辑处理
     */
    private INewsListModelBiz iNewsListModelBiz;

    public NewsListPresenterImpl() {
        iNewsListModelBiz = new NewsListModelImpl();
    }

    @Override
    public void loadNewsList(int loadType,int pageNum) {
        if (mView != null) {
            // 首次进入界面展示加载对话框
            if (loadType == MvpActivity.FIRST_LOAD) {
                mView.showLoading("加载中...");
            }
            iNewsListModelBiz.load(loadType,pageNum, this);
        }
    }

    @Override
    public void loadSuccess(List<NewsEntity> data, int loadType) {
        if (mView != null) {
            mView.loadSuccess(data, loadType);
            mView.hideLoading();
        }
    }

    @Override
    public void loadFailure(String msg) {
        if (mView != null) {
            mView.showError(msg);
            mView.hideLoading();
        }
    }


}
