package com.example.youtubeplayertest

import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    val tabClickListener = SingleLiveEvent<Int>()

    companion object {
        fun getInstance() =
            MainActivityViewModel()
    }

}