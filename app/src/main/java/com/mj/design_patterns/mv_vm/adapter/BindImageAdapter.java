package com.mj.design_patterns.mv_vm.adapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by mj
 * on 2017/5/22.
 */

public class BindImageAdapter {
    /**
     * mv_vm xml 传入url 加载图片
     * imageUrl 为xml中 的命名
     *
     * @param iv   imageView
     * @param path 图片路径
     */
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView iv, String path) {
        Glide.with(iv.getContext()).load(path).into(iv);
    }

    /**
     * mv_vm xml 设置 mipmap Resource
     *
     * @param iv    imageView
     * @param resId resource id
     */
    @BindingAdapter({"resId"})
    public static void loadMipmapResource(ImageView iv, int resId) {
        iv.setImageResource(resId);
    }

}
