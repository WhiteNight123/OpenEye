package com.example.openeye.ui.video

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openeye.R
import com.example.openeye.logic.model.VideoDetailsBean
import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer


class VideoActivity : GSYBaseActivityDetail<StandardGSYVideoPlayer>() {
    val viewModel by lazy { ViewModelProvider(this).get(VideoActivityViewModel::class.java) }
    lateinit var videoPlayer: StandardGSYVideoPlayer
    lateinit var mTvTitle: TextView
    lateinit var mTvDescribe: TextView
    lateinit var mBtnUnlike: ImageView
    lateinit var mBtnLike: ImageView
    lateinit var mTvLikeCount: TextView
    lateinit var mTvShareCount: TextView
    lateinit var mtvReplyCount: TextView
    lateinit var mIvAuthor: ImageView
    lateinit var mTvAuthor: TextView
    lateinit var mTvAuthorDescribe: TextView
    lateinit var mRvVideoRelevant: RecyclerView
    lateinit var adapter: VideoRelevantRvAdapter
    private val videoData by lazy {
        intent.getSerializableExtra("videoDetail") as VideoDetailsBean
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.BLACK

        setContentView(R.layout.activity_video)
        mBtnUnlike = findViewById(R.id.video_iv_unlike)
        mBtnLike = findViewById(R.id.video_iv_like)
        mTvTitle = findViewById(R.id.video_tv_title)
        mTvDescribe = findViewById(R.id.video_tv_describe)
        mTvLikeCount = findViewById(R.id.video_tv_like)
        mTvShareCount = findViewById(R.id.video_tv_share)
        mtvReplyCount = findViewById(R.id.video_tv_reply)
        mIvAuthor = findViewById(R.id.video_iv_author)
        mTvAuthor = findViewById(R.id.video_tv_author)
        mTvAuthorDescribe = findViewById(R.id.video_tv_author_describe)
        mRvVideoRelevant = findViewById(R.id.video_rv_recent)

        mTvTitle.text = videoData.videoTitle
        mTvDescribe.text = videoData.videoDescription
        mTvLikeCount.text = videoData.likeCount.toString()
        mTvShareCount.text = videoData.shareCount.toString()
        mtvReplyCount.text = videoData.replyCount.toString()
        mTvAuthor.text = videoData.authorName
        mTvAuthorDescribe.text = videoData.authorDescription
        Glide.with(this).load(videoData.authorIcon).into(mIvAuthor)
        mRvVideoRelevant.layoutManager = LinearLayoutManager(this)
        adapter = VideoRelevantRvAdapter(viewModel.videoData) { view, videoBean -> }
        mRvVideoRelevant.adapter = adapter
        viewModel.getVideoRelevant(311490)
        viewModel.relevantVideo.observe(this) {
            viewModel.videoData.clear()
            viewModel.videoData.addAll(it)
            adapter.notifyItemRangeChanged(0, viewModel.videoData.size)
            Log.d("TAG", "onCreate: $it")
        }

        mTvDescribe.setOnClickListener {
            if (mTvDescribe.maxLines < 10) {
                mTvDescribe.maxLines = 10
            } else {
                mTvDescribe.maxLines = 2
            }
        }
        mBtnUnlike.setOnClickListener {
            mBtnUnlike.visibility = View.INVISIBLE
            mBtnLike.visibility = View.VISIBLE
        }
        mBtnLike.setOnClickListener {
            mBtnUnlike.visibility = View.VISIBLE
            mBtnLike.visibility = View.INVISIBLE
        }


        videoPlayer = findViewById(R.id.video_player)

        videoPlayer.titleTextView.visibility = View.VISIBLE
        videoPlayer.titleTextView.textSize = 16f
        videoPlayer.backButton.visibility = View.VISIBLE
        initVideoBuilderMode()
//        lifecycleScope.launch {
//            // 延迟播放
//            delay(1500)
//            videoPlayer.startPlayLogic()
//        }
    }


    override fun getGSYVideoPlayer(): StandardGSYVideoPlayer {
        return videoPlayer
    }

    override fun getGSYVideoOptionBuilder(): GSYVideoOptionBuilder {
        //内置封面可参考SampleCoverVideo
        val imageView = ImageView(this)
        Glide.with(this)
            .load(videoData.videoCover)
            .into(imageView);
        //loadCover(imageView, url)
        return GSYVideoOptionBuilder()
            .setThumbImageView(imageView)
            .setThumbImageView(imageView)
            .setUrl(videoData.videoUrl)
            .setCacheWithPlay(true)
            .setVideoTitle(videoData.videoTitle)
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