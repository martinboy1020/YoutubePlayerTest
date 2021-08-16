package com.example.youtubeplayertest

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Insets
import android.os.Build
import android.util.DisplayMetrics
import android.view.Surface
import android.view.WindowInsets


object DisplayUtility {

    /**
     * 將px值轉換為sp值，保證文字大小不變
     *
     * @param pxValue
     * @param fontScale
     * (DisplayMetrics類中屬性scaledDensity)
     * @return
     */
    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * 將sp值轉換為px值，保證文字大小不變
     *
     * @param spValue
     * @param fontScale
     * (DisplayMetrics類中屬性scaledDensity)
     * @return
     */
    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * Covert dp to px
     * @param dp
     * @param context
     * @return pixel
     */
    fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * getDensity(context)
    }

    /**
     * Covert px to dp
     * @param px
     * @param context
     * @return dp
     */
    fun convertPixelToDp(px: Float, context: Context): Float {
        return px / getDensity(context)
    }

    /**
     * 取得螢幕密度
     * 120dpi = 0.75
     * 160dpi = 1 (default)
     * 240dpi = 1.5
     * @param context
     * @return
     */
    fun getDensity(context: Context): Float {
        val metrics = context.resources.displayMetrics
        return metrics.density
    }

    // 檢查手機螢幕是否轉為橫屏
    fun checkMobileRotationIsLandScape(
        newConfig: Configuration,
        activity: Activity
    ): Boolean {
        // 為了7.0無法從newConfig抓到正確的轉向值 所以透過getWindowManager().getDefaultDisplay().getRotation()抓目前螢幕的旋轉值
        val rotation = activity.windowManager.defaultDisplay.rotation
        val angle =
            if (rotation == Surface.ROTATION_90) 90 else if (rotation == Surface.ROTATION_180) 180 else if (rotation == Surface.ROTATION_270) 270 else 0
        return newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE || activity.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE || angle == 90 || angle == 270
    }

    fun getScreenWidth(activity: Activity): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity.windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }

}
