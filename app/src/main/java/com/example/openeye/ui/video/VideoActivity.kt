package com.example.openeye.ui.video

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openeye.App.Companion.appContext
import com.example.openeye.R
import com.example.openeye.logic.model.VideoDetailData
import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer


class VideoActivity : GSYBaseActivityDetail<StandardGSYVideoPlayer>() {
    val viewModel by lazy { ViewModelProvider(this).get(VideoActivityViewModel::class.java) }
    lateinit var videoPlayer: StandardGSYVideoPlayer
    lateinit var mTvNetError: TextView
    lateinit var mIVNetError: ImageView
    lateinit var mNsDetail: NestedScrollView
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
        intent.getSerializableExtra("videoDetail") as VideoDetailData
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.BLACK

        setContentView(R.layout.activity_video)
        // 延迟加载动画
        postponeEnterTransition()
        startPostponedEnterTransition()

        mBtnUnlike = findViewById(R.id.video_iv_unlike)
        mBtnLike = findViewById(R.id.topic_iv_detail_like)
        mTvTitle = findViewById(R.id.video_tv_title)
        mTvDescribe = findViewById(R.id.topic_tv_detail_describe)
        mTvLikeCount = findViewById(R.id.topic_tv_detail_like)
        mTvShareCount = findViewById(R.id.video_tv_share)
        mtvReplyCount = findViewById(R.id.topic_tv_detail_reply)
        mIvAuthor = findViewById(R.id.video_iv_author)
        mTvAuthor = findViewById(R.id.video_tv_author)
        mTvAuthorDescribe = findViewById(R.id.video_tv_author_describe)
        mRvVideoRelevant = findViewById(R.id.video_rv_recent)
        videoPlayer = findViewById(R.id.video_player)
        mIVNetError = findViewById(R.id.video_iv_net_error)
        mTvNetError = findViewById(R.id.video_tv_net_error)
        mNsDetail = findViewById(R.id.video_ns_detail)

        videoPlayer.transitionName = intent.getStringExtra("transitionName")
        mTvTitle.text = videoData.videoTitle
        mTvDescribe.text = videoData.videoDescription
        mTvLikeCount.text = videoData.likeCount.toString()
        mTvShareCount.text = videoData.shareCount.toString()
        mtvReplyCount.text = videoData.replyCount.toString()
        mTvAuthor.text = videoData.authorName
        mTvAuthorDescribe.text = videoData.authorDescription
        Glide.with(this).load(videoData.authorIcon).into(mIvAuthor)
        mRvVideoRelevant.layoutManager = LinearLayoutManager(this)
        adapter = VideoRelevantRvAdapter(viewModel.videoData) { view1, videoBean ->
            viewModel.insertHistory(videoBean)
            startActivity(view1, videoBean)
        }
        mRvVideoRelevant.adapter = adapter
        mRvVideoRelevant.layoutAnimation = // 入场动画
            LayoutAnimationController(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.recycler_view_slide_from_left_to_right_in
                )
            )
        viewModel.getVideoRelevant(videoData.videoId)

        viewModel.videoData1.observe(this) {
            viewModel.videoData.clear()
            viewModel.videoData.addAll(it)
            adapter.notifyItemRangeChanged(0, viewModel.videoData.size)
        }
        viewModel.isFreshSuccess.observe(this) {
            if (it) {
                mTvNetError.visibility = View.INVISIBLE
                mIVNetError.visibility = View.INVISIBLE
                mNsDetail.visibility = View.VISIBLE
            } else {
                mTvNetError.visibility = View.VISIBLE
                mIVNetError.visibility = View.VISIBLE
                mNsDetail.visibility = View.INVISIBLE
            }
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


        videoPlayer.titleTextView.visibility = View.VISIBLE
        videoPlayer.titleTextView.textSize = 16f
        videoPlayer.backButton.visibility = View.VISIBLE
        videoPlayer.backButton.setOnClickListener { onBackPressed() }
        initVideoBuilderMode()
//        lifecycleScope.launch {
//            // 延迟播放
//            delay(1500)
//            videoPlayer.startPlayLogic()
//        }
    }

    private fun startActivity(view: View, videoDetail: VideoDetailData) {
        val intent = Intent(appContext, VideoActivity::class.java)
        intent.putExtra("videoDetail", videoDetail)
        intent.putExtra("transitionName", view.transitionName)
        val options = ActivityOptions.makeSceneTransitionAnimation(this, view, view.transitionName)
        startActivity(intent, options.toBundle())
        //finish()
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