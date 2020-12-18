package com.yzx.democollection.ui.trend.trend.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import com.yzx.democollection.ui.trend.trend.UnitUtil;
import com.yzx.democollection.ui.trend.trend.model.ICell;
import com.yzx.democollection.ui.trend.trend.model.ICellList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuqiang on 2014/12/29.
 * 绘制的走势图View
 */
public class TrendView extends View {
    //小球文字画笔
    private Paint defaultTextPaint = null;

    //背景画笔
    private Paint defaultBgPaint=null;

    //网格线画笔
    private Paint defaultLinePaint=null;

    //小球之间的连线画笔
    private Paint mLinkLine=null;

    //当前View的宽度
    private int mWidth;

    //当前View的高度
    private int mHeight;

    //数据合计
    private List<ICellList> tableContentData=new ArrayList();


   public TrendView(Context context, AttributeSet attrs) {
       super(context, attrs, 0);
       initSource();
   }

    /***
     * 初始化资源
     */
   private void initSource(){
        int dpValue=getScreenDenisty();
        //网格线画笔
       defaultLinePaint=new Paint();
       defaultLinePaint.setColor(Color.GRAY);
       defaultLinePaint.setAntiAlias(true);
       defaultLinePaint.setStrokeWidth(UnitUtil.dp2px(getContext(),0.5f));

        //小球上面的文字画笔
       defaultTextPaint=new Paint();
       defaultTextPaint.setColor(Color.WHITE);
       defaultTextPaint.setTextSize((dpValue*12/160));
       defaultTextPaint.setAntiAlias(true);
       defaultTextPaint.setStrokeWidth(2f);

        //小球之间连线画笔
        mLinkLine=new Paint();
//        mLinkLine.setColor(Color.BLUE);
        mLinkLine.setARGB(255,0,68,0);
        mLinkLine.setAntiAlias(true);
        mLinkLine.setStrokeWidth(UnitUtil.dp2px(getContext(),2));

        //小球画笔
       defaultBgPaint=new Paint();
       defaultBgPaint.setAntiAlias(true);
       defaultBgPaint.setARGB(255,255,68,68);

   }

    /**
     * 设置表格数据数据元
     * @param tableContentData
     */
    public void setTableContentData(List<ICellList> tableContentData) {
        this.tableContentData = tableContentData;
        invalidate();
    }

    @Override
   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //设置测量View的大小:宽度和高度
        setMeasuredDimension(UnitUtil.dp2px(getContext(),tableContentData.get(0).getRowWidth()), (int) (tableContentData.size() * UnitUtil.dp2px(getContext(),tableContentData.get(0).getRowHeight())));
        //取得测量之后当前View的宽度
        this.mWidth = getMeasuredWidth();
        //取得测量之后当前View的高度
        this.mHeight = getMeasuredHeight();
        //重新绘制,不重绘,不会生效;
        invalidate();
   }

   @Override
   protected void onDraw(Canvas canvas) {
       super.onDraw(canvas);
       drawXYLine(canvas);
       drawLinkLine(canvas);
       drawRedBall(canvas);

   }

    /***
     * 绘制红球在网格中的分布图
     * @param canvas 画布
     */
   private void drawRedBall(Canvas canvas) {

       //保存每期的开奖号码;蓝球
        //最外面控制期号
        for (int i=0;i<tableContentData.size();i++){
            ICellList cellList = tableContentData.get(i);
            Rect src = new Rect();
            Rect dst = new Rect();
            //最里面控制（绘制）每期所在行,红球和蓝球分布位置
           for (int j=0;j<cellList.getCellList().size();j++){
               List<ICell> cells = cellList.getCellList();
               for(int m = 0;m<cells.size();m++){
                   ICell cell = cells.get(m);

                   if(!TextUtils.isEmpty(cell.getCellContent())){
                       float[] xy = this.translateBallXY(tableContentData,i, m);
                       int cellWidth = UnitUtil.dp2px(getContext(),cell.getCellWidth());
                       int cellHeight = UnitUtil.dp2px(getContext(),cell.getCellHeight());

                       if(cell.getBgPaint() != null){
                           cell.drawBackground(canvas,cellWidth,cellHeight,xy[0],xy[1]);
                       }else{
                           src.left = 0;
                           src.top = 0;
                           src.bottom = cellHeight/4;
                           src.right =cellWidth/4;

                           dst.left = (int) (xy[0] + cellWidth * 0.1f);
                           dst.top = (int)(xy[1]+ cellHeight * 0.1f);
                           dst.bottom = (int) (dst.top + cellHeight * 0.8f);
                           dst.right = (int) (dst.left + cellWidth * 0.8f);
                           RectF rf=new RectF(dst.left,dst.top,dst.right,dst.bottom);
                           //这个是画圆
                            canvas.drawOval(rf,defaultBgPaint);
                           //如果有合适的小球图片,就用下面的方法...上面的去掉
                           //这个是画图片
//                           canvas.drawBitmap(mRedBitmapBall,src,dst,mBallPaint);
                       }

                       if(cell.getContentPaint() != null){
                           cell.drawContent(canvas,cellWidth,cellHeight,xy[0],xy[1]);
                       }else{
                           if(cell.getCellContent().length() == 1){
                               canvas.drawText(" " + cell.getCellContent(), dst.left + src.right / 2, dst.top + cellHeight * 0.5f, defaultTextPaint);
                           }else{
                               canvas.drawText(cell.getCellContent(), dst.left + src.right / 2, dst.top + cellHeight * 0.5f, defaultTextPaint);
                           }
                       }
                    }
               }
           }
        }
   }
    /**
     * 绘制连线。
     * @param canvas
     */
    private void drawLinkLine(Canvas canvas) {
        //获取所有的连接标志
        List<Integer> linkFlagList = new ArrayList();
        for(ICellList iCellList : tableContentData){
            for(ICell cell : iCellList.getCellList()){
                boolean isExist = false;
                for(int flag : linkFlagList){
                    if(flag == cell.getDrawLinkFlag()){
                        isExist = true;
                        break;
                    }
                }
                if(!isExist && cell.getDrawLinkFlag() != -1)linkFlagList.add(cell.getDrawLinkFlag());
            }
        }

        float[] lastXy = new float[2];
        for(int flag:linkFlagList){
            boolean isFirst = true;
            for(int i = 0;i<tableContentData.size();i++){
                ICellList iCellList = tableContentData.get(i);
                for(int j = 0;j<iCellList.getCellList().size();j++){
                    ICell cell = iCellList.getCellList().get(j);
                    if(cell.isDrawLinkLine() && cell.getDrawLinkFlag() == flag){
                        if(isFirst){
                            lastXy = this.translateBallXY(tableContentData,i,j);
                            isFirst = false;
                        }else{
                            float[] xy = this.translateBallXY(tableContentData,i,j);
                            int cellWidth = UnitUtil.dp2px(getContext(),cell.getCellWidth());
                            int cellHeight = UnitUtil.dp2px(getContext(),cell.getCellWidth());
                            canvas.drawLine(lastXy[0]+ cellWidth * 0.5f,lastXy[1]+ cellHeight * 0.25f , xy[0]+ cellWidth * 0.5f,xy[1]+ cellHeight * 0.25f , mLinkLine);

                            lastXy = xy;
                        }
                    }
                }
            }
        }
    }

    /***
     * 绘制X轴和Y轴的网格线
     * @param canvas 画布
     */
   private void drawXYLine(Canvas canvas) {
          //期号数:X轴;含顶部和底部的边角线;
          for (int i = 0;i < tableContentData.size();i ++) {
              ICellList cellList = tableContentData.get(i);
              canvas.drawLine(0, UnitUtil.dp2px(getContext(),cellList.getRowHeight()) * i, this.mWidth, UnitUtil.dp2px(getContext(),cellList.getRowHeight()) * i, defaultLinePaint);
              if(i == tableContentData.size() -1){
                  canvas.drawLine(0, UnitUtil.dp2px(getContext(),cellList.getRowHeight()) * (i+1), this.mWidth, UnitUtil.dp2px(getContext(),cellList.getRowHeight()) * (i+1), defaultLinePaint);
              }
          }

          if(tableContentData.size() >0){
              List<ICell> cellList = tableContentData.get(0).getCellList();
              int xVal = 0;
              for(int j = 0;j<cellList.size();j++){
                  if(j == 0){
                      canvas.drawLine(0 , 0, 0, this.mHeight, defaultLinePaint);
                  }
                  ICell cell = cellList.get(j);
                  xVal += UnitUtil.dp2px(getContext(),cell.getCellWidth());
                  canvas.drawLine(xVal , 0, xVal, this.mHeight, defaultLinePaint);
              }
          }

   }

    /**
     * 返回小球坐标
     * @param cellList 单元格列表;
     * @param rowIndex 索引X轴;
     * @param columnIndex  索引Y轴.
     * @return
     */
    private float[] translateBallXY(List<ICellList> cellList, int rowIndex, int columnIndex) {
        float[] xy = new float[2];
        int height = 0;
        int width = 0;
        for(int i = 0;i<cellList.size();i++) {
            if(i!=0)height += UnitUtil.dp2px(getContext(),cellList.get(i).getRowHeight());
            if (i == rowIndex) {
                xy[1] = height;
                width = 0;
                for (int j = 0; j < cellList.get(i).getCellList().size(); j++) {
                        if (j == columnIndex){
                            xy[0] = width;
                            break;
                        }
                        width += UnitUtil.dp2px(getContext(),cellList.get(i).getCellList().get(j).getCellWidth());
                }
            }
        }
        return xy;
    }

    /**
     * 获取当前屏幕的密度
     * @return
     */
    public int getScreenDenisty(){
        DisplayMetrics dm=getResources().getDisplayMetrics();
        return dm.densityDpi;
    }

}