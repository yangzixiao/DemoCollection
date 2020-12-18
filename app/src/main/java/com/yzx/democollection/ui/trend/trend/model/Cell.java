package com.yzx.democollection.ui.trend.trend.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2018/5/9 下午11:29
 * description:
 */
public class Cell implements ICell{

    public static final int STYLE_BG_CIRCLE = 1;//圆形
    public static final int STYLE_BG_ROUNDED_RECTANGLE = 2;//圆角矩形
    public static final int STYLE_3 = 3;
    public static final int STYLE_4 = 4;

    private int style = STYLE_BG_CIRCLE;
    private String content = "";
    private int cellWidth = 0;
    private int cellHeight = 0;
    private boolean isDrawLinkLine = false;
    private int drawLinkLineFlag = -1;

    protected Paint bgPaint,contentPaint;

    protected Rect src = null;
    protected Rect dst = null;

    public Cell(String content) {
        this(content,STYLE_BG_CIRCLE,30,30);
    }

    public Cell(String content, int style, Paint bgPaint, Paint contentPaint) {
        this(content,style,30,30,bgPaint,contentPaint);
    }

    public Cell(String content, Paint bgPaint, Paint contentPaint) {
        this(content,STYLE_BG_CIRCLE,30,30,bgPaint,contentPaint);
    }

    public Cell(String content, int style, int cellWidth, int cellHeight) {
        this(content,style,cellWidth,cellHeight,null,null);
    }
    public Cell(String content, int cellWidth, int cellHeight) {
        this(content,STYLE_BG_CIRCLE,cellWidth,cellHeight,null,null);
    }

    public Cell(String content, int cellWidth, int cellHeight, Paint bgPaint, Paint contentPaint) {
        this(content,STYLE_BG_CIRCLE,cellWidth,cellHeight,bgPaint,contentPaint);
    }

    public Cell(String content, int style, int cellWidth, int cellHeight, Paint bgPaint, Paint contentPaint) {
        this(content,false,-1,style,cellWidth,cellHeight,bgPaint,contentPaint);
    }

    public Cell(String content, boolean isDrawLinkLine, int drawLinkLineFlag, int style, int cellWidth, int cellHeight, Paint bgPaint, Paint contentPaint) {
        this.style = style;
        this.content = content;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        this.bgPaint = bgPaint;
        this.contentPaint = contentPaint;
        this.isDrawLinkLine = isDrawLinkLine;
        this.drawLinkLineFlag = drawLinkLineFlag;
    }

    @Override
    public int getCellWidth() {
        return cellWidth;
    }

    @Override
    public int getCellHeight() {
        return cellHeight;
    }

    @Override
    public String getCellContent() {
        return content;
    }

    @Override
    public Paint getBgPaint() {
        return bgPaint;
    }

    @Override
    public Paint getContentPaint() {
        return contentPaint;
    }

    private void init(int cellWidth, int cellHeight, float x, float y){
        if(src == null || dst == null){
            src = new Rect();
            dst = new Rect();

            src.left = 0;
            src.top = 0;
            src.bottom = cellHeight/4;
            src.right =cellWidth/4;

            dst.left = (int) (x + cellWidth * 0.1f);
            dst.top = (int)(y+ cellHeight * 0.1f);
            dst.bottom = (int) (dst.top + cellHeight * 0.8f);
            dst.right = (int) (dst.left + cellWidth * 0.8f);

        }
    }


    @Override
    public void drawBackground(Canvas canvas, int cellWidth, int cellHeight, float x, float y) {
        init(cellWidth,cellHeight,x,y);
        switch (style){
            case STYLE_BG_CIRCLE:
                canvas.drawOval(new RectF(dst.left,dst.top,dst.right,dst.bottom),getBgPaint());
                break;
            case STYLE_BG_ROUNDED_RECTANGLE:
                canvas.drawRoundRect(new RectF(dst.left,dst.top,dst.right,dst.bottom),10,10,getBgPaint());
                break;
        }
    }

    @Override
    public void drawContent(Canvas canvas, int cellWidth, int cellHeight, float x, float y) {
        init(cellWidth,cellHeight,x,y);

        switch (style){
            case STYLE_BG_CIRCLE:
                canvas.drawText(content, dst.left + src.right, dst.top + cellHeight * 0.5f, contentPaint);
                break;
            case STYLE_BG_ROUNDED_RECTANGLE:
                canvas.drawText(content, (float) (dst.left + src.right/2.1), dst.top + cellHeight * 0.53f, contentPaint);
                break;
        }
    }

    @Override
    public boolean isDrawLinkLine() {
        return isDrawLinkLine;
    }

    @Override
    public int getDrawLinkFlag() {
        return drawLinkLineFlag;
    }

}
