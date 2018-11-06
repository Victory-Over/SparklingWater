package com.victory.seawater;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UiUtils {
    public static <T extends View> T findViewById(Activity activity, @IdRes int id) {
        return activity.findViewById(id);
    }

    public static String getEditTextString(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static String getTextViewString(TextView textView) {
        return textView.getText().toString().trim();
    }

    private static long lastClickTime = 0;

    /**
     * 返回true 可以点击
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long endTime = time - lastClickTime;
        lastClickTime = time;
        return endTime > 2000;
    }

    /**
     * 获取屏内容
     */
    public static DisplayMetrics getScreen(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics;
    }

    /**
     * dip转换px
     */
    public static int dp2px(Context context, int dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * pxz转换dip
     */
    public static int px2dp(Context context, int px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

}
