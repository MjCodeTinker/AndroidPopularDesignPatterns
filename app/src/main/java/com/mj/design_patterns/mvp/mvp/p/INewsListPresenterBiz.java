package com.mj.design_patterns.mvp.mvp.p;

/**
 * Created by mj
 * on 2017/5/22.
 */

public interface INewsListPresenterBiz {
    /**
     * 加载列表数据
     *
     * @param loadType 加载类型
     * @param pageNum  页数
     */
    void loadNewsList(int loadType, int pageNum);

}
