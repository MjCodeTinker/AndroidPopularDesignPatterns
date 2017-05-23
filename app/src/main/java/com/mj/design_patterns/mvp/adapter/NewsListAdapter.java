package com.mj.design_patterns.mvp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mj.design_patterns.R;
import com.mj.design_patterns.common.BaseAdapter;
import com.mj.design_patterns.mvp.bean.NewsEntity;
import com.mj.design_patterns.utils.ToastUtils;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by mj
 * on 2017/5/22.
 */

public class NewsListAdapter extends BaseAdapter<NewsEntity, RecyclerView.ViewHolder> {

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
    public RecyclerView.ViewHolder createVH(ViewGroup parent, int viewType) {

        if (viewType == NO_PIC) {
            View itemView = inflater.inflate(R.layout.mvp_no_pic_item, parent, false);
            return new NoPicVH(itemView);
        } else if (viewType == ONE_PIC) {
            View itemView = inflater.inflate(R.layout.mvp_one_pic_item, parent, false);
            return new OnePicVH(itemView);
        } else if (viewType == MORE_PIC) {
            View itemView = inflater.inflate(R.layout.mvp_more_pic_item, parent, false);
            return new MorePicVH(itemView);
        }

        return null;
    }

    @Override
    public void bindVH(RecyclerView.ViewHolder viewHolder, int position) {

        NewsEntity entity = data.get(position);

        if (viewHolder instanceof NoPicVH) {
            //无图
            NoPicVH vh = (NoPicVH) viewHolder;
            vh.tvContent.setText(entity.getContent());
            vh.tvDate.setText(String.valueOf(entity.getDateStr() + "---##第" + entity.getPageNum() + "页数据"));

            if (entity.isNice()) {
                vh.ivThumbUp.setImageResource(R.mipmap.nice_press);
                vh.tvThumbUpNum.setTextColor(context.getResources().getColor(R.color.appColor));
            } else {
                vh.ivThumbUp.setImageResource(R.mipmap.nice_normal);
                vh.tvThumbUpNum.setTextColor(context.getResources().getColor(R.color.c6));
            }
            vh.tvThumbUpNum.setText(String.valueOf(entity.getNiceCount()));
            //点击事件
            vh.ivThumbUp.setOnClickListener(new ItemClick(position, entity));
        } else if (viewHolder instanceof OnePicVH) {
            //一张图
            OnePicVH vh = (OnePicVH) viewHolder;
            Glide.with(context).load(entity.getImageUrls()[0]).into(vh.ivPhoto);

            vh.tvContent.setText(entity.getContent());
            vh.tvDate.setText(String.valueOf(entity.getDateStr() + "---##第" + entity.getPageNum() + "页数据"));
            if (entity.isNice()) {
                vh.ivThumbUp.setImageResource(R.mipmap.nice_press);
                vh.tvThumbUpNum.setTextColor(context.getResources().getColor(R.color.appColor));
            } else {
                vh.ivThumbUp.setImageResource(R.mipmap.nice_normal);
                vh.tvThumbUpNum.setTextColor(context.getResources().getColor(R.color.c6));
            }
            vh.tvThumbUpNum.setText(String.valueOf(entity.getNiceCount()));
            vh.ivThumbUp.setOnClickListener(new ItemClick(position, entity));

        } else {
            //多张图
            MorePicVH vh = (MorePicVH) viewHolder;

            vh.tvContent.setText(entity.getContent());
            vh.tvDate.setText(String.valueOf(entity.getDateStr() + "---##第" + entity.getPageNum() + "页数据"));

            if (entity.isNice()) {
                vh.ivThumbUp.setImageResource(R.mipmap.nice_press);
                vh.tvThumbUpNum.setTextColor(context.getResources().getColor(R.color.appColor));
            } else {
                vh.ivThumbUp.setImageResource(R.mipmap.nice_normal);
                vh.tvThumbUpNum.setTextColor(context.getResources().getColor(R.color.c6));
            }
            vh.tvThumbUpNum.setText(String.valueOf(entity.getNiceCount()));

            //加载多图
            for (int i = 0; i < entity.getImageUrls().length; i++) {
                //防止 获取的url 比 控件ivPhoto多 导致数组越界
                if (i <= vh.ivPhoto.length) {
                    Glide.with(context).load(entity.getImageUrls()[i]).into(vh.ivPhoto[i]);
                }
            }

            vh.ivThumbUp.setOnClickListener(new ItemClick(position, entity));
        }

    }

    /**
     * 无图片的ViewHolder
     */
    static class NoPicVH extends RecyclerView.ViewHolder {

        @BindView(R.id.no_pic_tv_content)
        TextView tvContent;
        @BindView(R.id.no_pic_tv_date)
        TextView tvDate;
        @BindView(R.id.no_pic_iv_thumb_up)
        ImageView ivThumbUp;
        @BindView(R.id.no_pic_tv_thumb_up)
        TextView tvThumbUpNum;

        NoPicVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    /**
     * 一张图片的viewHolder
     */
    static class OnePicVH extends RecyclerView.ViewHolder {
        @BindView(R.id.item_one_pic_iv_img)
        ImageView ivPhoto;
        @BindView(R.id.item_one_pic_tv_content)
        TextView tvContent;
        @BindView(R.id.item_one_pic_tv_date)
        TextView tvDate;
        @BindView(R.id.item_one_pic_iv_thumb_up)
        ImageView ivThumbUp;
        @BindView(R.id.item_one_pic_tv_thumb_up)
        TextView tvThumbUpNum;

        OnePicVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 多张图片的 viewHolder
     */
    static class MorePicVH extends RecyclerView.ViewHolder {

        @BindViews({
                R.id.mvp_more_pic_iv_photo1,
                R.id.mvp_more_pic_iv_photo2,
                R.id.mvp_more_pic_iv_photo3})
        ImageView[] ivPhoto;

        @BindView(R.id.mvp_more_pic_tv_content)
        TextView tvContent;
        @BindView(R.id.mvp_more_pic_tv_date)
        TextView tvDate;
        @BindView(R.id.mvp_more_pic_iv_thumb_up)
        ImageView ivThumbUp;
        @BindView(R.id.mvp_more_pic_tv_thumb_up)
        TextView tvThumbUpNum;

        MorePicVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


    /**
     * item点击事件
     */
    private class ItemClick implements View.OnClickListener {

        int position;
        NewsEntity newsEntity;

        public ItemClick(int position, NewsEntity newsEntity) {
            this.position = position;
            this.newsEntity = newsEntity;
        }

        @Override
        public void onClick(View v) {
            if (newsEntity.isNice()) {
                // 取消点赞
                data.get(position).setNice(false);
                data.get(position).setNiceCount(newsEntity.getNiceCount() - 1);
                ToastUtils.show(context, "取消点赞 position=" + position);
            } else {
                //点赞
                data.get(position).setNice(true);
                data.get(position).setNiceCount(newsEntity.getNiceCount() + 1);
                ToastUtils.show(context, "点赞成功 position=" + position);
            }
            notifyDataSetChanged();
        }
    }

}
