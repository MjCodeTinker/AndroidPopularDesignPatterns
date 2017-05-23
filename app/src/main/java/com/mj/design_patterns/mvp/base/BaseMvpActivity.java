package com.mj.design_patterns.mvp.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by mj
 * on 2017/5/22.
 */

public abstract class BaseMvpActivity<V extends BaseView, P extends BasePresenter<V>> extends AppCompatActivity implements BaseView {

    /**
     * presenter
     */
    public P presenter;

    /**
     * @return 初始化presenter
     */
    public abstract P initPresenter();

    /**
     * 获取 layout 资源id
     *
     * @return 资源id
     */
    public abstract int getLayoutId();

    /**
     * 初始化操作
     *
     * @param savedInstanceState savedInstanceState
     */
    public abstract void init(Bundle savedInstanceState);

    /**
     * butter knife unBinder
     */
    Unbinder bind;

    public Context context;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutId());
        presenter = initPresenter();
        bind = ButterKnife.bind(this);
        context = this;
        presenter.attach((V) this);
        init(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
        bind.unbind();
    }

}
