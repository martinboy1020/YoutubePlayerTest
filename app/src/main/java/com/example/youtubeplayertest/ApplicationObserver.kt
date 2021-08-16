package com.example.youtubeplayertest

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * 觀察應用程式目前是處於前景還是後景
 */
class ApplicationObserver : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForeground() {
        // App goes to foreground
        // 當判斷進入前景時 重新連線WebSocket
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackground() {
        // App goes to background
    }
}