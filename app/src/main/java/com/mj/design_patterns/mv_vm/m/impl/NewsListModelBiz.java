package com.mj.design_patterns.mv_vm.m.impl;

import android.os.Handler;

import com.mj.design_patterns.mv_vm.bean.NewsEntity;
import com.mj.design_patterns.mv_vm.m.biz.INewsListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mj
 * on 2017/5/22.
 * 加载的model
 */

public class NewsListModelBiz implements INewsListModel {

    @Override
    public void load(final int loadType, int pageNum, final LoadResponse loadResponse) {

        final List<NewsEntity> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NewsEntity entity = new NewsEntity();

            switch (i % 3) {
                case 0:
                    entity.setImageUrls(new String[]{
                            "http://img1.gtimg.com/19/1967/196723/19672355_980x640_281.jpg",
                            "http://img1.gtimg.com/19/1967/196723/19672354_980x640_281.jpg",
                            "http://img1.gtimg.com/19/1967/196723/19672357_980x640_281.jpg"
                    });
                    break;
                case 1:
                    break;
                case 2:
                    entity.setImageUrls(new String[]{
                            "http://img1.gtimg.com/19/1967/196723/19672356_980x640_281.jpg"
                    });
                    break;
            }
            entity.setPageNum(pageNum);
            entity.setContent("视觉中国讯 近日，章子怡现身某会场。章子怡走出休息室心情不错，被粉丝围观面带微笑十分从容。");
            entity.setDateStr("2017年5月23日");
            entity.setNiceCount(100);
            data.add(entity);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadResponse.loadSuccess(data, loadType);
            }
        }, 2000);

    }
}
