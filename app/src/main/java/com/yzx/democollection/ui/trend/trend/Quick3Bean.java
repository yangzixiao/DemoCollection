package com.yzx.democollection.ui.trend.trend;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.yzx.democollection.ui.trend.trend.model.Cell;
import com.yzx.democollection.ui.trend.trend.model.ICell;
import com.yzx.democollection.ui.trend.trend.model.ICellList;

import java.util.ArrayList;
import java.util.List;

/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2018/5/9 下午2:32
 * description:快三的数据
 */
public class Quick3Bean implements ICellList {

    public static final String TAG_111 = "奇奇奇";
    public static final String TAG_112 = "奇奇偶";
    public static final String TAG_122 = "奇偶偶";
    public static final String TAG_121 = "奇偶奇";
    public static final String TAG_211 = "偶奇奇";
    public static final String TAG_221 = "偶偶奇";
    public static final String TAG_212 = "偶奇偶";
    public static final String TAG_222 = "偶偶偶";

    public static final String TAG_DDD = "大大大";
    public static final String TAG_DDX = "大大小";
    public static final String TAG_DXD = "大小大";
    public static final String TAG_DXX = "大小小";
    public static final String TAG_XDD = "小大大";
    public static final String TAG_XDX = "小大小";
    public static final String TAG_XXD = "小小大";
    public static final String TAG_XXX = "小小小";

    private String id = "";
    private String number = "";
    private List<ICell> cellList = new ArrayList();
    private int rowWidth = 0;
    private int rowHeight = 30;

    private String numberTag = "";
    private String numberDXTag = "";

    private Paint ballBgPaint ,ballTextPaint ,tagBgPaint,tagTextPaint,repeatCountBgPaint,repeatCountContentPaint;


    public Quick3Bean(Context context, String id, String number) {
        this.id = id;
        ballBgPaint = new Paint();
        ballBgPaint=new Paint();
        ballBgPaint.setAntiAlias(true);
        ballBgPaint.setARGB(255,255,68,68);

        ballTextPaint = new Paint();
        ballTextPaint=new Paint();
        ballTextPaint.setColor(Color.WHITE);
        ballTextPaint.setTextSize(UnitUtil.sp2px(context,14));
        ballTextPaint.setAntiAlias(true);
        ballTextPaint.setStrokeWidth(2f);

        tagBgPaint = new Paint();
        tagBgPaint=new Paint();
        tagBgPaint.setAntiAlias(true);
        tagBgPaint.setARGB(255,44,68,68);

        tagTextPaint=new Paint();
        tagTextPaint.setColor(Color.LTGRAY);
        tagTextPaint.setTextSize(UnitUtil.sp2px(context,12));
        tagTextPaint.setAntiAlias(true);
        tagTextPaint.setStrokeWidth(2f);

        repeatCountBgPaint = new Paint();
        repeatCountBgPaint=new Paint();
        repeatCountBgPaint.setAntiAlias(true);
        repeatCountBgPaint.setARGB(255,0,68,0);

        repeatCountContentPaint = new Paint();
        repeatCountContentPaint=new Paint();
        repeatCountContentPaint.setColor(Color.WHITE);
        repeatCountContentPaint.setTextSize(UnitUtil.sp2px(context,10));
        repeatCountContentPaint.setAntiAlias(true);
        repeatCountContentPaint.setStrokeWidth(2f);


        setNumber(number);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
        cellList.addAll(split(number));

        String[] arr = number.split(",");
        if(arr.length == 3){
            String numberTagResult = "";
            String numberDXTagResult = "";
            for(String num : arr){
                numberTagResult += Integer.parseInt(num)%2 == 0?"偶":"奇";
                numberDXTagResult += Integer.parseInt(num)>3 ?"大":"小";
            }
            numberTag = numberTagResult;
            numberDXTag = numberDXTagResult;

            cellList.add(new Cell(numberTag,Cell.STYLE_BG_ROUNDED_RECTANGLE,60,30,tagBgPaint,tagTextPaint));
            cellList.add(TAG_111.equals(numberTag)?new Cell(numberTag,true,1,Cell.STYLE_BG_ROUNDED_RECTANGLE,60,30,tagBgPaint,tagTextPaint):new Cell(null,60,30));
            cellList.add(TAG_112.equals(numberTag)?new Cell(numberTag,true,1,Cell.STYLE_BG_ROUNDED_RECTANGLE,60,30,tagBgPaint,tagTextPaint):new Cell(null,60,30));
            cellList.add(TAG_122.equals(numberTag)?new Cell(numberTag,true,1,Cell.STYLE_BG_ROUNDED_RECTANGLE,60,30,tagBgPaint,tagTextPaint):new Cell(null,60,30));
            cellList.add(TAG_121.equals(numberTag)?new Cell(numberTag,true,1,Cell.STYLE_BG_ROUNDED_RECTANGLE,60,30,tagBgPaint,tagTextPaint):new Cell(null,60,30));
            cellList.add(TAG_211.equals(numberTag)?new Cell(numberTag,true,1,Cell.STYLE_BG_ROUNDED_RECTANGLE,60,30,tagBgPaint,tagTextPaint):new Cell(null,60,30));
            cellList.add(TAG_221.equals(numberTag)?new Cell(numberTag,true,1,Cell.STYLE_BG_ROUNDED_RECTANGLE,60,30,tagBgPaint,tagTextPaint):new Cell(null,60,30));
            cellList.add(TAG_212.equals(numberTag)?new Cell(numberTag,true,1,Cell.STYLE_BG_ROUNDED_RECTANGLE,60,30,tagBgPaint,tagTextPaint):new Cell(null,60,30));
            cellList.add(TAG_222.equals(numberTag)?new Cell(numberTag,true,1,Cell.STYLE_BG_ROUNDED_RECTANGLE,60,30,tagBgPaint,tagTextPaint):new Cell(null,60,30));

            cellList.add(new Cell(numberDXTagResult,Cell.STYLE_BG_ROUNDED_RECTANGLE,60,30,tagBgPaint,tagTextPaint));
            cellList.add(TAG_DDD.equals(numberDXTag)?new Cell(numberDXTag,true,2,Cell.STYLE_BG_ROUNDED_RECTANGLE,60,30,tagBgPaint,tagTextPaint):new Cell(null,60,30));
            cellList.add(TAG_DDX.equals(numberDXTag)?new Cell(numberDXTag,true,2,Cell.STYLE_BG_ROUNDED_RECTANGLE,60,30,tagBgPaint,tagTextPaint):new Cell(null,60,30));
            cellList.add(TAG_DXD.equals(numberDXTag)?new Cell(numberDXTag,true,2,Cell.STYLE_BG_ROUNDED_RECTANGLE,60,30,tagBgPaint,tagTextPaint):new Cell(null,60,30));
            cellList.add(TAG_DXX.equals(numberDXTag)?new Cell(numberDXTag,true,2,Cell.STYLE_BG_ROUNDED_RECTANGLE,60,30,tagBgPaint,tagTextPaint):new Cell(null,60,30));
            cellList.add(TAG_XDD.equals(numberDXTag)?new Cell(numberDXTag,true,2,Cell.STYLE_BG_ROUNDED_RECTANGLE,60,30,tagBgPaint,tagTextPaint):new Cell(null,60,30));
            cellList.add(TAG_XDX.equals(numberDXTag)?new Cell(numberDXTag,true,2,Cell.STYLE_BG_ROUNDED_RECTANGLE,60,30,tagBgPaint,tagTextPaint):new Cell(null,60,30));
            cellList.add(TAG_XXD.equals(numberDXTag)?new Cell(numberDXTag,true,2,Cell.STYLE_BG_ROUNDED_RECTANGLE,60,30,tagBgPaint,tagTextPaint):new Cell(null,60,30));
            cellList.add(TAG_XXX.equals(numberDXTag)?new Cell(numberDXTag,true,2,Cell.STYLE_BG_ROUNDED_RECTANGLE,60,30,tagBgPaint,tagTextPaint):new Cell(null,60,30));
        }



        for(int i = 0;i<cellList.size();i++){
//            cell : cellList
            Log.e("TAG","i:" + i);

            rowWidth+= cellList.get(i).getCellWidth();
        }

    }

    @Override
    public List<ICell> getCellList() {
        return cellList;
    }

    @Override
    public int getRowWidth() {
        return rowWidth;
    }

    @Override
    public int getRowHeight() {
        return rowHeight;
    }


    private List<ICell> split(String number){
        String[] arr = number.split(",");
        List<ICell> cellList = new ArrayList();

        for(String cellNumber:arr){
            cellList.add(new Cell(cellNumber));
        }
        return cellList;
    }
}
