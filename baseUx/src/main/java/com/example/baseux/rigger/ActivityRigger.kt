package com.example.baseux.rigger

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.Navigation
import com.example.baseux.components.AnnotationParse
import com.example.baseux.permission.PermissionManager

open class ActivityRigger : AppCompatActivity() {
    private val TAG = "Activity Rigger"

    val binding by lazy {
        val viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(this),
            AnnotationParse.getAnnotatedLayout(this),
            null,
            false
        )
        viewDataBinding.lifecycleOwner = this
        viewDataBinding
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(this.binding.root)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionManager.onRequestPermissionResult(this, requestCode, permissions, grantResults)
    }

    fun transActivity(id: Int, args: Bundle? = null) {
        try {
            val navHostId = AnnotationParse.getAnnotatedNavHost(this)
            Navigation.findNavController(this, navHostId).navigate(id, args)
        } catch (e: RuntimeException) {
            Log.e(TAG, "Can't not find navhostFragment ${e.message}")
        }
    }

    fun transFragment(id: Int, bundle: Bundle? = null) {
        try {
            val navHostId = AnnotationParse.getAnnotatedNavHost(this)
            // 為了配合未來BottomNavigation的需求 在切換Fragment時需要先做navigateUp再切換
            // 避免已經載入過的Fragment重複被載入而發生cannot be found from the current destination錯誤
            Navigation.findNavController(this, navHostId).navigateUp()
            Navigation.findNavController(this, navHostId).navigate(id, bundle)
        } catch (e: RuntimeException) {
            Log.e(TAG, "Can't not find NavigationController ${e.message}")
        }
    }

    fun popBackFragment() {
        this.supportFragmentManager.popBackStack()
    }
}