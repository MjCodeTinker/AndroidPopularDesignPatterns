package com.mj.design_patterns.mvp.bean;

import java.util.Arrays;

/**
 * Created by mj
 * on 2017/5/22.
 */

public class NewsEntity {

    private String content;//内容
    private String[] imageUrls;//图片url
    private String dateStr;//日期str
    private int niceCount;//点赞数
    private boolean isNice;//是否点赞
    private int pageNum;//页数

    /**
     * 根据图片数量 显示列表类型
     *
     * @return 图片数量
     */
    public int getPicNum() {
        if (imageUrls == null) {
            return 0;
        } else {
            return imageUrls.length;
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String[] imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public int getNiceCount() {
        return niceCount;
    }

    public void setNiceCount(int niceCount) {
        this.niceCount = niceCount;
    }

    public boolean isNice() {
        return isNice;
    }

    public void setNice(boolean nice) {
        isNice = nice;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public String toString() {
        return "NewsEntity{" +
                "content='" + content + '\'' +
                ", imageUrls=" + Arrays.toString(imageUrls) +
                ", dateStr='" + dateStr + '\'' +
                ", niceCount=" + niceCount +
                ", isNice=" + isNice +
                ", pageNum=" + pageNum +
                '}';
    }

}
