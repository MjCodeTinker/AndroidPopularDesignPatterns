package com.mj.design_patterns.mv_vm.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

import com.mj.design_patterns.BR;
import com.mj.design_patterns.R;
import com.mj.design_patterns.common.BaseAdapter;
import com.mj.design_patterns.mv_vm.bean.NewsEntity;
import com.mj.design_patterns.utils.ToastUtils;

/**
 * Created by mj
 * on 2017/5/22.
 */

public class NewsListAdapter extends BaseAdapter<NewsEntity, BindingVH> {

    /**
     * 没有图片的item 类型
     */
    private final int NO_PIC = 0;
    /**
     * 有一张图片的item 类型
     */
    private final int ONE_PIC = 1;
    /**
     * 更多图片的item 类型
     */
    private final int MORE_PIC = 2;

    /**
     * 根据图片数量判断item 的类型
     *
     * @param position position
     * @return itemType
     */
    @Override
    public int getItemViewType(int position) {
        if (data.get(position).getPicNum() == 0) {
            return NO_PIC;
        } else if (data.get(position).getPicNum() == 1) {
            return ONE_PIC;
        } else {
            return MORE_PIC;
        }
    }

    @Override
    public BindingVH createVH(ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = null;
        switch (viewType) {
            case NO_PIC:
                viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.mv_vm_item_text, parent, false);
                break;
            case ONE_PIC:
                viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.mv_vm_item_one_pic, parent, false);
                break;
            case MORE_PIC:
                viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.mv_vm_item_more_pic, parent, false);
                break;
        }
        return new BindingVH<>(viewDataBinding);
    }

    @Override
    public void bindVH(BindingVH bindingVH, int position) {
        bindingVH.getBinding().setVariable(BR.newsEntity, data.get(position));
        bindingVH.getBinding().setVariable(BR.handle, this);
        bindingVH.getBinding().setVariable(BR.position, position);

        bindingVH.getBinding().executePendingBindings();
    }

    /**
     * 点赞
     *
     * @param newsEntity entity
     */
    public void thumbUpClick(NewsEntity newsEntity, int position) {

        if (newsEntity.isNice()) {
            newsEntity.setNice(false);
            newsEntity.setNiceCount(newsEntity.getNiceCount() - 1);
            ToastUtils.show(context, "取消点赞 position=" + position);

        } else {
            newsEntity.setNice(true);
            newsEntity.setNiceCount(newsEntity.getNiceCount() + 1);
            ToastUtils.show(context, "点赞成功 position=" + position);
        }

    }


}
