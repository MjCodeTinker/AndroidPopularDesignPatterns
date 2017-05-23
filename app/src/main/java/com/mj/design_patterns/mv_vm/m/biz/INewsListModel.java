package com.mj.design_patterns.mv_vm.m.biz;

import com.mj.design_patterns.mv_vm.bean.NewsEntity;

import java.util.List;

/**
 * Created by mj
 * on 2017/5/22.
 */

public interface INewsListModel {
    /**
     * 加载新闻数据
     *
     * @param loadType     firstLoad首次加载 refresh 刷新 loadMore 加载更多
     * @param pageNum 页数
     * @param loadResponse 加载结果回调
     */
    void load(int loadType,int pageNum ,LoadResponse loadResponse);

    /**
     * 加载结果回调
     */
    interface LoadResponse {
        /**
         * 加载成功
         *
         * @param data     data
         * @param loadType 加载类型
         */
        void loadSuccess(List<NewsEntity> data, int loadType);

        /**
         * 加载失败
         *
         * @param msg message
         */
        void loadFailure(String msg);
    }
}
