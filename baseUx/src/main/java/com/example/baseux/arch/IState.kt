package com.example.baseux.arch

import androidx.lifecycle.MutableLiveData

open class IState{
    val uiTrans = MutableLiveData<Int>()
}