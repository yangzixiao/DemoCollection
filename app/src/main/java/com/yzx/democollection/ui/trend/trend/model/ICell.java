package com.yzx.democollection.ui.trend.trend.model;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2018/5/9 下午11:28
 * description:
 */
public interface ICell {
    int getCellWidth();
    int getCellHeight();
    String getCellContent();
    Paint getBgPaint();
    Paint getContentPaint();
    void drawBackground(Canvas canvas, int cellWidth, int cellHeight, float x, float y);
    void drawContent(Canvas canvas, int cellWidth, int cellHeight, float x, float y);
    boolean isDrawLinkLine();
    int getDrawLinkFlag();
}
