package com.mj.design_patterns.mvp.mvp.v;

import com.mj.design_patterns.mvp.base.BaseView;
import com.mj.design_patterns.mvp.bean.NewsEntity;

import java.util.List;

/**
 * Created by mj
 * on 2017/5/22.
 */

public interface INewsListView extends BaseView {

    void loadSuccess(List<NewsEntity> data, int loadType);

}
