package com.mj.design_patterns.launcher;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mj.design_patterns.R;
import com.mj.design_patterns.databinding.ActivityMainBinding;
import com.mj.design_patterns.mv_vm.ui.MvVmActivity;
import com.mj.design_patterns.mvp.ui.MvpActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mj
 * on 2017/5/22.
 */
public class MainActivity extends AppCompatActivity {

    // btn Mvp
    @OnClick(R.id.activity_main_btn_mvp)
    void btnMvp() {
        Intent intent = new Intent(this, MvpActivity.class);
        startActivity(intent);
    }

    /**
     * mv_vm 绑定的click事件
     *
     * @param view view
     *             一定要加参数 view 否则编译期间找不到 click方法
     */
    public void click(View view) {
        Intent intent = new Intent(this, MvVmActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        // mv_vm 绑定 click 事件
        binding.setHandle(this);
        //butter knife 绑定 click事件
        ButterKnife.bind(this);

    }

}
