package com.example.youtubeplayertest

import android.os.Bundle
import android.view.View
import com.example.baseux.components.LayoutId
import com.example.baseux.rigger.FragmentRigger
import com.example.youtubeplayertest.databinding.FragmentOtherBinding

@LayoutId(R.layout.fragment_other)
class OtherPageFragment : FragmentRigger() {

    private val mBinding: FragmentOtherBinding
        get() = binding as FragmentOtherBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.tvOtherPage.text = when ((this.activity as MainActivity).getNowPage()) {
            R.id.nav_matchListFragment -> getString(R.string.nav1)
            R.id.nav_homeFragment -> getString(R.string.nav3)
            R.id.nav_spotlightFragment -> getString(R.string.nav4)
            R.id.nav_soccer_database -> getString(R.string.nav5)
            else -> getString(R.string.nav3)
        }
    }

    private fun replaceVideoFragment(it: String) {
        (this.activity as MainActivity).replaceVideoFragment(it)
    }

}