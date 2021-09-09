package com.example.youtubeplayertest

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.baseux.components.LayoutId
import com.example.baseux.rigger.FragmentRigger
import com.example.youtubeplayertest.DisplayUtility.convertDpToPixel
import com.example.youtubeplayertest.databinding.FragmentPlayerBinding
import com.example.youtubeplayertest.stream_video_widget.StreamVideoView.Companion.checkMobileRotationIsLandScape
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
        mBinding.btnPlayPause.setOnClickListener {
//            if (mBinding.videoView.checkPlayerPlaying() == true) {
//                mBinding.videoView.pausePlayer()
//            } else {
//                mBinding.videoView.startPlayer()
//            }
            setFullScreen()
        }
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

    fun setFullScreen() {
        if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            requireActivity().requestedOrientation =
                ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    fun checkIsFullScreen(): Boolean {
        return requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    private fun landView(newConfig: Configuration) {
        val videoViewParams: ConstraintLayout.LayoutParams =
            mBinding.videoViewContainer.layoutParams as ConstraintLayout.LayoutParams
        if (checkMobileRotationIsLandScape(newConfig, this.activity as AppCompatActivity)) {
            videoViewParams.height = resources.displayMetrics.heightPixels
        } else {
            videoViewParams.height = convertDpToPixel(250f, this.requireContext()).toInt()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mBinding.videoMotionLayout.getConstraintSet(R.id.expanded)
                ?.let { expandedConstraintSet ->
                    // You can set the width and height here as well
                    expandedConstraintSet.constrainWidth(
                        R.id.videoViewContainer,
                        ConstraintSet.MATCH_CONSTRAINT
                    )
                    expandedConstraintSet.constrainHeight(
                        R.id.videoViewContainer,
                        convertDpToPixel(
                            resources.displayMetrics.heightPixels.toFloat(),
                            this.requireContext()
                        ).toInt()
                    )
                }
            mBinding.videoMotionLayout.enableSwipe = false
        } else {
            mBinding.videoMotionLayout.getConstraintSet(R.id.expanded)
                ?.let { expandedConstraintSet ->
                    // You can set the width and height here as well
                    expandedConstraintSet.constrainWidth(
                        R.id.videoViewContainer,
                        ConstraintSet.MATCH_CONSTRAINT
                    )
                    expandedConstraintSet.constrainHeight(
                        R.id.videoViewContainer,
                        convertDpToPixel(
                            250f,
                            this.requireContext()
                        ).toInt()
                    )
                }
            mBinding.videoMotionLayout.enableSwipe = true
        }

        mBinding.videoView.autoChangeVideoDirection(
            newConfig,
            this.activity as AppCompatActivity
        )

//        mBinding.videoMotionLayout.enableTransition(R.id.collapsed, false)

//        val constraints = ConstraintSet()
//        constraints.clone(mBinding.videoViewContainer)
//        constraints.constrainDefaultHeight(R.id.videoViewContainer, ConstraintSet.MATCH_CONSTRAINT_SPREAD)
//        constraints.constrainDefaultWidth(R.id.videoViewContainer, ConstraintSet.MATCH_CONSTRAINT_SPREAD)
//        constraints.applyTo(mBinding.videoViewContainer)

//        if (checkMobileRotationIsLandScape(newConfig, requireActivity() as AppCompatActivity)) {
//            mBinding.videoViewContainer.layoutParams = ConstraintLayout.LayoutParams(
//                ConstraintLayout.LayoutParams.MATCH_PARENT,
//                ConstraintLayout.LayoutParams.MATCH_PARENT
//            )
//            mBinding.content.visibility = View.GONE
//        } else {
//            mBinding.videoViewContainer.layoutParams = ConstraintLayout.LayoutParams(
//                ConstraintLayout.LayoutParams.MATCH_PARENT,
//                convertDpToPixel(250f, this.requireContext()).toInt()
//            )
//            mBinding.content.visibility = View.VISIBLE
//        }
    }

}