package com.mj.design_patterns.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mj
 * on 2017/5/18.
 * E: list集合存放的数据类型
 * VH: viewHolder
 */

public abstract class BaseAdapter<E, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    /**
     * 创建 View Holder
     *
     * @param parent   parent
     * @param viewType item type
     * @return view holder
     */
    public abstract VH createVH(ViewGroup parent, int viewType);

    /**
     * 绑定 View Holder
     *
     * @param vh       view holder
     * @param position position
     */
    public abstract void bindVH(VH vh, int position);

    /**
     * list data
     */
    public List<E> data;
    /**
     * inflater
     */
    public LayoutInflater inflater;
    /**
     * context
     */
    public Context context;

    /**
     * constructor
     */
    public BaseAdapter() {
        data = new ArrayList<>();
    }

    /**
     * 刷新
     *
     * @param data list data
     */
    public void refreshData(List<E> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 加载更多
     *
     * @param data list data
     */
    public void loadMoreData(List<E> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        return createVH(parent, viewType);
    }

    @Override
    public void onBindViewHolder(VH vh, int position) {
        bindVH(vh, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
