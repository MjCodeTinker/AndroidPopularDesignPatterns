package com.mj.design_patterns.mvp.mvp.m;


import android.os.Handler;

import com.mj.design_patterns.mvp.bean.NewsEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mj
 * on 2017/5/22.
 */

public class NewsListModelImpl implements INewsListModelBiz {

    @Override
    public void load(final int loadType, int pageNum, final LoadResponse loadResponse) {
        final List<NewsEntity> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NewsEntity entity = new NewsEntity();

            switch (i % 3) {
                case 0:
                    entity.setImageUrls(new String[]{
                            "http://p3.pstatp.com/large/212c0003ed3dd945f6a9",
                            "http://p1.pstatp.com/large/213b000407eac6b4a983",
                            "http://p9.pstatp.com/large/213500011c9332ac1540"
                    });
                    break;
                case 1:
                    break;
                case 2:
                    entity.setImageUrls(new String[]{
                            "http://p3.pstatp.com/large/213500011c427dca7d94"
                    });
                    break;
            }
            entity.setPageNum(pageNum);
            entity.setContent("陈盛桐，白百何与陈羽凡的儿子，小名元宝，如今9岁多的元宝也懂事了，知道了父母离婚的事情，在这个小小的年纪，对元宝来说是很大的伤害。");
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
