package com.mj.design_patterns.mvp.base;

/**
 * Created by mj
 * on 2017/5/22.
 */

 public class BasePresenter<V extends BaseView> {
    /**
     * IView 接口
     */
    protected V mView;

    /**
     * attach
     * @param v view
     */
    public void attach(V v) {
        mView = v;
    }

    /**
     * detach
     */
    public void detach() {
        if (mView != null) {
            mView = null;
        }
    }


}
