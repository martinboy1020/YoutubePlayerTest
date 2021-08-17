package com.example.youtubeplayertest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.baseux.extension.default

class MainActivityViewModel : ViewModel() {

    val tabClickListener = SingleLiveEvent<Int>()
    val nowPage = MutableLiveData<Int>().default(1)

    companion object {
        fun getInstance() =
            MainActivityViewModel()
    }

}