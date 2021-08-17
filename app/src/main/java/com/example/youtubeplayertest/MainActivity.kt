package com.example.youtubeplayertest

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.baseux.components.LayoutId
import com.example.baseux.components.NavHosttId
import com.example.baseux.rigger.ActivityRigger
import com.example.youtubeplayertest.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

@LayoutId(R.layout.activity_main)
@NavHosttId(R.id.mainActivityNavHostFragment)
class MainActivity : ActivityRigger() {

    val mainActivityViewModel by viewModel<MainActivityViewModel>()

    private val mBinding: ActivityMainBinding
        get() = binding as ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBottomNavigationBar(binding as ActivityMainBinding)
    }

    private fun initBottomNavigationBar(binding: ActivityMainBinding) {
        binding.bottomNavigationView.selectedItemId = R.id.nav_homeFragment
        mainActivityViewModel.nowPage.value = R.id.nav_homeFragment
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            if (it.isChecked) return@setOnNavigationItemSelectedListener true
            mainActivityViewModel.nowPage.value = it.itemId
            when (it.itemId) {
                R.id.nav_matchListFragment -> {
                    transFragment(R.id.action_to_matchListFragment)
                    binding.bottomNavigationView.menu.getItem(0).isChecked = true
                    binding.tvToolbarTitle.text = resources.getString(R.string.nav1)
                    mainActivityViewModel.tabClickListener.call()
                }
//                R.id.nav2 -> {
//                binding.bottomNavigationView.menu.getItem(1).isChecked = true
//                    binding.tvToolbarTitle.text = resources.getString(R.string.nav2)
//                mainActivityViewModel.tabClickListener.call()
//                }
                R.id.nav_homeFragment -> {
                    transFragment(R.id.action_to_homeFragment)
                    binding.bottomNavigationView.menu.getItem(1).isChecked = true
                    binding.tvToolbarTitle.text = resources.getString(R.string.nav3)
                    mainActivityViewModel.tabClickListener.call()
                }
                R.id.nav_spotlightFragment -> {
                    transFragment(R.id.action_to_spotlightFragment)
                    binding.bottomNavigationView.menu.getItem(2).isChecked = true
                    binding.tvToolbarTitle.text = resources.getString(R.string.nav4)
                    mainActivityViewModel.tabClickListener.call()
                }
                R.id.nav_soccer_database -> {
                    transFragment(R.id.action_to_soccer_database)
                    binding.bottomNavigationView.menu.getItem(3).isChecked = true
                    binding.tvToolbarTitle.text = resources.getString(R.string.nav5)
                    mainActivityViewModel.tabClickListener.call()
                }
            }
            false
        }
    }

    fun getNowPage(): Int? {
        return mainActivityViewModel.nowPage.value
    }

    fun hideShowBottomNavigation(isShow: Boolean) {
        Log.d("tag111111", "hideShowBottomNavigation(): $isShow")
        mBinding.bottomNavigationView.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    override fun onBackPressed() {
        Log.d("tag111111", "checkVideoFragmentIsExpend(): " + checkVideoFragmentIsExpend())
//        if (checkVideoFragmentIsExpend()) {
//            (supportFragmentManager.findFragmentById(R.id.fragment_video_player_view)).also {
//                if (it != null) {
//                    (it as PlayerFragment).getVideoMotionLayout().transitionToStart()
//                }
//            }
//        } else {
            Log.d("tag111111", "supportFragmentManager.backStackEntryCount: " + supportFragmentManager.backStackEntryCount)
            if (supportFragmentManager.backStackEntryCount == 0) {
                val navHostFragment: Fragment? =
                    this.supportFragmentManager.findFragmentById(R.id.mainActivityNavHostFragment)
                val fragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
                (supportFragmentManager.findFragmentById(R.id.fragment_video_player_view)).also {
                    if (it != null) {
                        (it as PlayerFragment).releaseVideoPlayer()
                        supportFragmentManager.beginTransaction().remove(it)
                            .commit()
                        hideShowBottomNavigation(true)
                    } else {
                        finishAffinity()
                    }
                }
            } else {
//                (supportFragmentManager.findFragmentById(R.id.fragment_video_player_view)).also {
//                    if (it != null) {
//                        (it as PlayerFragment).releaseVideoPlayer()
//                        supportFragmentManager.beginTransaction().remove(it)
//                            .commit()
//                    }
//                }
                super.onBackPressed()
            }
//        }
    }

    fun replaceVideoFragment(url: String) {
        (supportFragmentManager.findFragmentById(R.id.fragment_video_player_view)).also {
            if (it != null) {
                it as PlayerFragment
                it.refreshNewVideo()
                if (it.getVideoMotionLayout().currentState == R.id.collapsed)
                    it.getVideoMotionLayout().transitionToEnd()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_video_player_view, PlayerFragment.newInstance(url))
                    .commit()
            }
        }
    }

    fun checkVideoFragmentIsExpend(): Boolean {
        (supportFragmentManager.findFragmentById(R.id.fragment_video_player_view)).also {
            return if (it != null) {
                it as PlayerFragment
                it.getVideoMotionLayout().currentState == R.id.expanded
            } else {
                false
            }
        }
    }

}