package com.example.youtubeplayertest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.baseux.components.LayoutId
import com.example.baseux.rigger.FragmentRigger
import com.example.youtubeplayertest.databinding.FragmentPlayerBinding

@LayoutId(R.layout.fragment_player)
class PlayerFragment : FragmentRigger() {

    private val mBinding: FragmentPlayerBinding
        get() = binding as FragmentPlayerBinding

    companion object {

        private const val URL = "url"

        fun newInstance(url: String): PlayerFragment = PlayerFragment().also { f ->
            f.arguments = Bundle().also { b ->
                b.putString(URL, url)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.videoView.setVideoPath("https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4")
        mBinding.videoView.start()

//        mBinding.recyclerView.apply {
//            adapter = SimpleAdapter(layoutResId = R.layout.view_item_player)
//            layoutManager = LinearLayoutManager(activity)
//        }

        mBinding.videoMotionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
//                (activity as MainActivity).also {
//                    it.mainMotionLayout.progress = abs(progress)
//                }
            }
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {}
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }
        })
        mBinding.videoMotionLayout.transitionToEnd()
    }

    fun getVideoMotionLayout(): SingleViewTouchableMotionLayout {
        return mBinding.videoMotionLayout
    }

    fun refreshNewVideo() {
        mBinding.videoView.setVideoPath("https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4")
        mBinding.videoView.start()
    }

}