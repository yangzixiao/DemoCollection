package com.yzx.democollection.ui.chip;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.google.android.material.chip.Chip;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;

public class CircleChip extends Chip {

    private Paint paint;
    private Rect rect;

    public CircleChip(Context context) {
        this(context, null);
    }

    public CircleChip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleChip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        rect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int realSize = Math.max(widthSize, heightSize);
        super.onMeasure(realSize, realSize);
//        setChipCornerRadius(realSize / 2);
    }
//
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//
//        int cornerRadius = Math.max(w, h) / 2;
//        setChipCornerRadius(cornerRadius);
//    }


    @Override
    protected void onDraw(Canvas canvas) {
        paint.setTextSize(getTextSize());
        CharSequence text = getText();
        paint.getTextBounds(text.toString(),0,text.length(),rect);
        int centerX = getMeasuredHeight() / 2;
        if (isChecked()) {
            paint.setColor(Color.RED);
        } else {
            paint.setColor(Color.BLACK);
        }
        canvas.drawCircle(centerX, centerX, centerX, paint);
        super.onDraw(canvas);
        int  startX=centerX-rect.width()/2;
        int  startY=centerX+rect.height()/2;
        paint.setColor(Color.WHITE);
        canvas.drawText(text,0,text.length(),startX,startY,paint);
//        getText()
//        canvas.drawText();
//        setShapeAppearanceModel(ShapeAppearanceModel.builder().setAllCornerSizes(new RelativeCornerSize(1.0f))
//                .build());
    }
}
