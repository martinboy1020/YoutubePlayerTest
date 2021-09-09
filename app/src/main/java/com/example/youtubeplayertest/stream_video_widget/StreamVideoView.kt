package com.example.youtubeplayertest.stream_video_widget

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.CountDownTimer
import android.util.AttributeSet
import android.util.Log
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.youtubeplayertest.DisplayUtility
import com.example.youtubeplayertest.DisplayUtility.getDensity
import com.example.youtubeplayertest.R
import com.google.android.exoplayer2.ui.PlayerView

class StreamVideoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), VideoPlayerManager.VideoPlayerManagerListener {

    private var view: View? = null
    private var videoView: PlayerView? = null
    private var videoPlayerManager: VideoPlayerManager? = null
    private var videoUrl: String? = null
    private var layoutPlayerView: ConstraintLayout? = null
    private var listener: StreamVideoViewListener? = null
    private var textBuffering: TextView? = null
    private var viewHeight: Int = 250

    private var bufferLoadingCountDownTimer: CountDownTimer = object : CountDownTimer(10000, 1000) {
        override fun onTick(millisUntilFinished: Long) {}
        override fun onFinish() {
            showPleaseOtherVideoSource()
        }
    }

    interface StreamVideoViewListener {
        fun isBuffering()
        fun isReady()
        fun playError()
    }

    init {
        initView()
        videoPlayerManager = VideoPlayerManager(context, videoView)
        videoPlayerManager?.setVideoManagerListener(this)
    }

    fun setStreamVideoViewListener(listener: StreamVideoViewListener) {
        this.listener = listener
    }

    private fun initView() {
        view = View.inflate(context, R.layout.layout_stream_video, this)
        videoView = view?.findViewById(R.id.video_view)
        textBuffering = view?.findViewById(R.id.text_buffering)
        layoutPlayerView = view?.findViewById(R.id.layout_player_view)
        changeViewHeight(this.viewHeight)
    }

    fun setStreamVideo(videoUrl: String?, videoHeight: Int = 0) {
        if (videoHeight != 0) changeViewHeight(videoHeight)
        if (videoUrl.isNullOrEmpty()) {
            textBuffering?.text = context.resources.getString(R.string.live_streaming_not_exist)
            textBuffering?.visibility = View.VISIBLE
        } else {
            checkVideoUrl(videoUrl)
        }
    }

    fun changeViewHeight(viewHeight: Int) {
        this.viewHeight = viewHeight
        val videoViewParams: ViewGroup.LayoutParams =
            layoutPlayerView?.layoutParams as ViewGroup.LayoutParams
        videoViewParams.height = convertDpToPixel(context, viewHeight.toFloat()).toInt()
    }

    fun checkVideoUrl(videoUrl: String) {
        if (this.videoUrl.isNullOrEmpty()) {
            initPlayer(videoUrl)
        } else if (this.videoUrl != videoUrl) {
            changeVideoSource(videoUrl)
        }
    }

    /**
     * 初始化播放器
     * @param videoUrl 影片URL
     */
    private fun initPlayer(videoUrl: String) {
        this.videoUrl = videoUrl
        videoPlayerManager?.initializeExoplayer(videoUrl)
    }

    /**
     * 切換影片
     * @param videoUrl 影片URL
     */
    fun changeVideoSource(videoUrl: String) {
        this.videoUrl = videoUrl
        videoPlayerManager?.changeVideoResource(videoUrl)
    }

    /**
     * 影片View轉向功能
     * @param newConfig 透過Activity onConfigurationChange返回的Configuration類
     * @param activity 當前頁面的Activity
     */
    fun autoChangeVideoDirection(newConfig: Configuration, activity: AppCompatActivity) {
        val videoViewParams: ViewGroup.LayoutParams =
            layoutPlayerView?.layoutParams as ViewGroup.LayoutParams
        if (checkMobileRotationIsLandScape(newConfig, activity)) {
//            videoViewParams.height =
//                resources.displayMetrics.heightPixels - getStatusBarHeight(activity)
            videoViewParams.height = resources.displayMetrics.heightPixels
        } else {
            videoViewParams.height = convertDpToPixel(activity, this.viewHeight.toFloat()).toInt()
        }
    }

    /**
     * 播放開始
     */
    fun startPlayer() {
        if (!videoUrl.isNullOrEmpty()) videoPlayerManager?.startPlayer()
    }

    /**
     * 播放暫停
     */
    fun pausePlayer() {
        videoPlayerManager?.pausePlayer()
    }

    /**
     * 釋放播放器
     */
    fun releasePlayer() {
        videoPlayerManager?.releasePlayer()
        this.videoUrl = ""
    }

    fun checkPlayerPlaying(): Boolean? {
        return if (videoPlayerManager == null) {
            false
        } else {
            videoPlayerManager?.checkPlayerPlaying()
        }
    }

    override fun isBuffering() {
        textBuffering?.text = context.resources.getString(R.string.live_streaming_loading)
        textBuffering?.visibility = View.VISIBLE
    }

    override fun isReady() {
        textBuffering?.visibility = View.GONE
    }

    override fun playError() {
        textBuffering?.text = context.resources.getString(R.string.live_streaming_play_fail)
        textBuffering?.visibility = View.VISIBLE
        listener?.playError()
    }

    private fun showPleaseOtherVideoSource() {
//        progressBar?.visibility = View.GONE
//        textBuffering?.visibility = View.VISIBLE
//        textBuffering?.text = context.resources.getString(R.string.please_change_other_video_source)
    }

    /**
     * Covert dp to px
     * @param dp
     * @param context
     * @return pixel
     */
    fun convertDpToPixel(context: Context, dp: Float): Float {
        return dp * getDensity(context)
    }

    companion object {

        /**
         * 獲得狀態列高度
         * @param context
         * @return
         */
        fun getStatusBarHeight(context: Context): Int {
            var result = 0
            val resourceId: Int =
                context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = context.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }

        /**
         * 判斷手機螢幕橫豎狀態
         * @param newConfig
         * @param activity
         * @return
         */
        fun checkMobileRotationIsLandScape(
            newConfig: Configuration,
            activity: AppCompatActivity
        ): Boolean {
            // 為了7.0無法從newConfig抓到正確的轉向值 所以透過getWindowManager().getDefaultDisplay().getRotation()抓目前螢幕的旋轉值
            val rotation = activity.windowManager.defaultDisplay.rotation
            val angle =
                if (rotation == Surface.ROTATION_90) 90 else if (rotation == Surface.ROTATION_180) 180 else if (rotation == Surface.ROTATION_270) 270 else 0
            return newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE || activity.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE || angle == 90 || angle == 270
        }

    }

}