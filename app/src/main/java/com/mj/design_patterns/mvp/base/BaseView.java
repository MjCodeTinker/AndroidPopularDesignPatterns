package com.mj.design_patterns.mvp.base;

/**
 * Created by mj
 * on 2017/5/22.
 */

public interface BaseView {
    /**
     * @param errorMsg 错误信息
     */
    void showError(String errorMsg);

    /**
     * 展示加载框
     *
     * @param msg 加载信息
     */
    void showLoading(String msg);

    /**
     * 关闭加载
     */
    void hideLoading();
}
