package com.example.baseux.rigger

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.example.baseux.rigger.ActivityRigger

open class ViewModelRigger constructor(application: Application, val navController: NavController): AndroidViewModel(application){

    val context: Context = application.applicationContext

    fun transFragment(id: Int){
        this.navController.navigate(id) }

    fun transFragment(node: NavDirections){ this.navController.navigate(node) }

    fun transActivity(id: Int, args: Bundle? = null){ (this.context as ActivityRigger).transActivity(id, args) }
}