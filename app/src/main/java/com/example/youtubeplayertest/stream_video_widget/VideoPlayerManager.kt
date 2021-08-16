package com.example.youtubeplayertest.stream_video_widget

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.HttpDataSource.HttpDataSourceException
import com.google.android.exoplayer2.upstream.HttpDataSource.InvalidResponseCodeException
import java.io.IOException


class VideoPlayerManager(context: Context?, playerView: PlayerView?, videoUrl: String? = null) : Player.EventListener {

    private var context: Context? = null
    private var playerView: PlayerView? = null
    private var videoUrl: String? = null
    private var player: SimpleExoPlayer? = null
    private var listener: VideoPlayerManagerListener? = null

    interface VideoPlayerManagerListener {
        fun isBuffering()
        fun isReady()
        fun playError()
    }

    init {
        this.context = context
        this.playerView = playerView
        this.videoUrl = videoUrl
    }

    fun setVideoManagerListener(listener: VideoPlayerManagerListener) {
        this.listener = listener
    }

    fun initializeExoplayer(videoUrl: String) {
        this.videoUrl = videoUrl

        if (this.videoUrl.isNullOrEmpty()) {
            Toast.makeText(context, "No Video Stream Url", Toast.LENGTH_SHORT).show()
            return
        }

        playerView?.useController = false
        player = SimpleExoPlayer.Builder(context!!).build()
        playerView?.player = player
        player?.setMediaItem(MediaItem.fromUri(videoUrl))
        player?.seekToDefaultPosition()
        player?.addListener(this)
        player?.prepare()
        player?.play()
//        player.playWhenReady = true
    }

    fun changeVideoResource(videoUrl: String) {
        player?.stop()
        player?.setMediaItem(MediaItem.fromUri(videoUrl))
        player?.seekToDefaultPosition()
        player?.prepare()
        player?.play()
    }

    fun startPlayer() {
        if (player != null && player?.playWhenReady == false) {
            player?.seekToDefaultPosition()
            player?.playWhenReady = true
            player?.play()
        }
    }

    fun pausePlayer() {
        if (player != null && player?.playWhenReady == true) {
            player?.playWhenReady = false
            player?.pause()
        }
    }

    fun releasePlayer() {
        if (player != null) {
            player?.stop()
            player?.release()
        }
    }

    fun checkPlayerPlaying(): Boolean? {
        return if(player == null) {
            false
        } else {
            player?.isPlaying
        }
    }

    override fun onIsLoadingChanged(isLoading: Boolean) {
        super.onIsLoadingChanged(isLoading)
        Log.d("VideoPlayerManager", "onIsLoadingChanged: $isLoading")
    }

    override fun onPlayerError(error: ExoPlaybackException) {
        super.onPlayerError(error)
        listener?.playError()
        if (error.type == ExoPlaybackException.TYPE_SOURCE) {
            val cause: IOException = error.sourceException
            if (cause is HttpDataSourceException) { // An HTTP error occurred.
                val httpError = cause as HttpDataSourceException
                // This is the request for which the error occurred.
                Log.d("VideoPlayerManager", "HttpDataSourceException")
                val requestDataSpec = httpError.dataSpec
                // It's possible to find out more about the error both by casting and by querying the cause.
                if (httpError is InvalidResponseCodeException) {
                    Log.d("VideoPlayerManager", "InvalidResponseCodeException")
                    // Cast to InvalidResponseCodeException and retrieve the response code, message and headers.
                } else {
                    // Try calling httpError.getCause() to retrieve the underlying cause, although note that it may be null.
                }
            }
        }
    }

    override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
        super.onPlayWhenReadyChanged(playWhenReady, reason)
        Log.d("VideoPlayerManager", "onPlayWhenReadyChanged playWhenReady: $playWhenReady reason: $reason")
    }

    override fun onPlaybackStateChanged(state: Int) {
        super.onPlaybackStateChanged(state)
        when (state) {
            Player.STATE_IDLE -> {
                Log.d("VideoPlayerManager", "onPlaybackStateChanged state: STATE_IDLE")
            }
            Player.STATE_BUFFERING -> {
                Log.d("VideoPlayerManager", "onPlaybackStateChanged state: STATE_BUFFERING")
                listener?.isBuffering()
            }
            Player.STATE_READY -> {
                Log.d("VideoPlayerManager", "onPlaybackStateChanged state: STATE_READY")
                listener?.isReady()
            }
            Player.STATE_ENDED -> {
                Log.d("VideoPlayerManager", "onPlaybackStateChanged state: STATE_ENDED")
            }
        }
    }
}