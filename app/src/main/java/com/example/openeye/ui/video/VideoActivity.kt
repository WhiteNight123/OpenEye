package com.example.openeye.ui.video

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.example.openeye.R
import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

class VideoActivity : GSYBaseActivityDetail<StandardGSYVideoPlayer>() {

    lateinit var videoPlayer: StandardGSYVideoPlayer
    lateinit var orientationUtils: OrientationUtils
    lateinit var button: ImageView
    private val url =
        "http://baobab.kaiyanapp.com/api/v1/playUrl?vid=106404&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss&udid="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        videoPlayer = findViewById(R.id.video_player)
        button = findViewById(R.id.video_iv_animator)
        button.setOnClickListener {
            (button.drawable as Animatable).start()
        }
        videoPlayer.titleTextView.visibility = View.GONE
        videoPlayer.backButton.visibility = View.GONE
        initVideoBuilderMode()
    }


    override fun getGSYVideoPlayer(): StandardGSYVideoPlayer {
        return videoPlayer
    }

    override fun getGSYVideoOptionBuilder(): GSYVideoOptionBuilder {
        //内置封面可参考SampleCoverVideo
        val imageView = ImageView(this)
        //loadCover(imageView, url)
        return GSYVideoOptionBuilder()
            .setThumbImageView(imageView)
            .setUrl(url)
            .setCacheWithPlay(true)
            .setVideoTitle(" ")
            .setIsTouchWiget(true)
            .setRotateViewAuto(false)
            .setLockLand(false)
            .setShowFullAnimation(false)
            .setNeedLockFull(true)
            .setSeekRatio(1f)
    }

    override fun clickForFullScreen() {
    }

    override fun getDetailOrientationRotateAuto(): Boolean {
        return true
    }
}