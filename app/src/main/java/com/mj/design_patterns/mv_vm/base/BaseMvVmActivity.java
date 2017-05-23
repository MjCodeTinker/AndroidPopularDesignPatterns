package com.mj.design_patterns.mv_vm.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by mj
 * on 2017/5/22.
 * description:
 * vdb: view data binding
 */

public abstract class BaseMvVmActivity<VDB extends ViewDataBinding> extends AppCompatActivity {
    /**
     * view data binding
     */
    public VDB viewDataBinding;
    /**
     * context
     */
    public Context context;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context = this;
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        init(savedInstanceState);
    }

}
