package com.yzx.democollection.ui.trend.trend.widget;

/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2018/5/10 下午6:06
 * description:
 */
public class TableHeaderBean {

    private int width = 0;
    private String title = "";

    public TableHeaderBean(String title, int width) {
        this.width = width;
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
