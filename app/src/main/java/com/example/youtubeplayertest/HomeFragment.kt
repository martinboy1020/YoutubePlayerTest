package com.example.youtubeplayertest

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseux.components.LayoutId
import com.example.baseux.rigger.FragmentRigger
import com.example.youtubeplayertest.databinding.FragmentHomeBinding

@LayoutId(R.layout.fragment_home)
class HomeFragment : FragmentRigger() {

    private val mBinding: FragmentHomeBinding
        get() = binding as FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.rvVideoList.apply {
            adapter = SimpleAdapter(layoutResId = R.layout.view_item_main,
                callback = { replaceVideoFragment(it) })
            layoutManager = LinearLayoutManager(context)
        }

    }

    private fun replaceVideoFragment(it: String) {
        (this.activity as MainActivity).replaceVideoFragment(it)
    }

}