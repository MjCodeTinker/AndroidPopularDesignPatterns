package com.mj.design_patterns.mv_vm.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.mj.design_patterns.BR;

import java.util.Arrays;

/**
 * Created by mj
 * on 2017/5/22.
 *
 */

public class NewsEntity extends BaseObservable{

    private String content;
    private String[] imageUrls;
    private String dateStr;
    private int niceCount;
    private boolean isNice;
    private int pageNum;

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

    /**
     * 获取日期拼接页数的字符串
     * @return 格式--> 2017年5月23日--##第1页数据
     */
    public String getDateSplicingPageNum(){
        return dateStr+"--##第"+pageNum+"页数据";
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
    @Bindable
    public boolean isNice() {
        return isNice;
    }

    public void setNice(boolean nice) {
        isNice = nice;
        notifyChange();
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
