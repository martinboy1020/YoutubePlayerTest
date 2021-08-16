package com.example.youtubeplayertest

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.baseux.components.LayoutId
import com.example.baseux.rigger.FragmentRigger
import com.example.youtubeplayertest.databinding.FragmentPlayerBinding
import com.example.youtubeplayertest.stream_video_widget.VideoPlayerManager

@LayoutId(R.layout.fragment_player)
class PlayerFragment : FragmentRigger() {

    private val mBinding: FragmentPlayerBinding
        get() = binding as FragmentPlayerBinding

    private var mVideoPlayerManager: VideoPlayerManager? = null

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
//        mVideoPlayerManager = VideoPlayerManager(this.requireContext(), mBinding.videoView)
//        mVideoPlayerManager?.initializeExoplayer("https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4")
        mBinding.videoView.setStreamVideo("https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4")
//        mBinding.videoView.setVideoPath("https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4")
//        mBinding.videoView.start()

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
                if (progress < 0.1f) {
                    (activity as MainActivity).hideShowBottomNavigation(true)
                    mBinding.videoView.changeViewHeight(55)
                } else {
                    (activity as MainActivity).hideShowBottomNavigation(false)
                    mBinding.videoView.changeViewHeight(250)
                }
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                if (motionLayout != null && motionLayout.currentState == R.id.collapsed) {
                    mBinding.videoView.changeViewHeight(55)
                } else {
                    mBinding.videoView.changeViewHeight(250)
                }
            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }
        })
        mBinding.videoMotionLayout.transitionToEnd()
    }

    fun getVideoMotionLayout(): SingleViewTouchableMotionLayout {
        return mBinding.videoMotionLayout
    }

    fun releaseVideoPlayer() {
        mBinding.videoView.releasePlayer()
//        mVideoPlayerManager?.releasePlayer()
    }

    fun refreshNewVideo() {
//        mVideoPlayerManager?.changeVideoResource("https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4")
        mBinding.videoView.changeVideoSource("https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4")
//        mBinding.videoView.setVideoPath("https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4")
//        mBinding.videoView.start()
    }

}