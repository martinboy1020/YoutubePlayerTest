package com.example.youtubeplayertest

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDex
//import com.example.youtubeplayertest.di.apiClientScope
//import com.example.youtubeplayertest.di.applicationPreferencesModule
import com.example.youtubeplayertest.di.mainActivityViewModuleScope
//import com.nt.universalscore.datacenter.socket.WebSocketHelper
//import com.nt.universalscore.di.*
//import com.nt.universalscore.ui.SplashActivity
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import java.lang.ref.SoftReference

class BaseApplication : Application() {

    private var mContextWk: SoftReference<Context>? = null
    private var mActivityWk: SoftReference<Activity>? = null

    override fun onCreate() {
        super.onCreate()
        configureKoin()

        ProcessLifecycleOwner.get()
            .lifecycle
            .addObserver(ApplicationObserver())

        if (mContextWk == null) mContextWk = SoftReference(this@BaseApplication)

        this.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(p0: Activity) {

            }

            override fun onActivityStarted(activity: Activity) {
                mContextWk?.clear()
                mContextWk = SoftReference(activity.applicationContext)

                if (mActivityWk == null) mActivityWk = SoftReference(activity)
                mActivityWk?.clear()
                mActivityWk = SoftReference(activity)
            }

            override fun onActivityDestroyed(p0: Activity) {

            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

            }

            override fun onActivityStopped(p0: Activity) {

            }

            override fun onActivityCreated(p0: Activity, p1: Bundle?) {

            }

            override fun onActivityResumed(p0: Activity) {

            }

        })

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    fun getCurrentActivity(): Activity? {
        return mActivityWk?.get()
    }

    private fun configureKoin() {
        startKoin {
            androidContext(this@BaseApplication)
            modules(getKoinModules())
        }
    }

    /**
     * 透過Koin初始化ViewModel, ViewModel設置位置在di/UiModule和ApiModule
     */
    private fun getKoinModules(): MutableList<Module> {
        val modules = mutableListOf<Module>()
//        //ApplicationPreferences Module
//        modules.add(applicationPreferencesModule)
//        //Api Module
//        modules.add(apiClientScope)
        //UI Module
        modules.add(mainActivityViewModuleScope)
        return modules
    }

}