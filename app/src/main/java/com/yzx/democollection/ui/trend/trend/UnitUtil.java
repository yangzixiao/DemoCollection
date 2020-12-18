package com.yzx.democollection.ui.trend.trend;

import android.content.Context;
import android.util.TypedValue;

/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2018/5/10 下午12:01
 * description:
 */
public class UnitUtil {
    public UnitUtil() {
    }

    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(1, dpVal, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(2, spVal, context.getResources().getDisplayMetrics());
    }

    public static float px2dp(Context context, float pxVal) {
        float scale = context.getResources().getDisplayMetrics().density;
        return pxVal / scale;
    }

    public static float px2sp(Context context, float pxVal) {
        return pxVal / context.getResources().getDisplayMetrics().scaledDensity;
    }
}

