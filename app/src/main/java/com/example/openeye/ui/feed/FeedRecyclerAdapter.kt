package com.example.openeye.ui.feed

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.openeye.App.Companion.appContext
import com.example.openeye.R
import com.example.openeye.logic.model.VideoDetailData
import com.example.openeye.ui.widge.BannerTransformer


class FeedRecyclerAdapter(
    private val data: ArrayList<VideoDetailData>,
    private val bannerData: ArrayList<VideoDetailData>,
    private val onClick: (view: View, videoBean: VideoDetailData) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var bannerAdapter: BannerAdapter
    companion object {
        const val HEADER = 0
        const val NORMAL = 1
        const val FOOT = 2
    }

    // 是否隐藏底部Progressbar
    var fadeTip = false

    inner class NormalHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mTvTitle: TextView = view.findViewById(R.id.feed_tv_title)
        val mTvLabel: TextView = view.findViewById(R.id.feed_tv_author)
        var mTvTime: TextView = view.findViewById(R.id.feed_tv_time)
        var mIvCover: ImageView = view.findViewById(R.id.feed_iv_cover)
        var mIvAuthor: ImageView = view.findViewById(R.id.feed_iv_author)

        init {
            mIvCover.setOnClickListener {
                onClick(mIvCover, data[absoluteAdapterPosition])
            }
        }
    }

    inner class FootHolder(view: View) : RecyclerView.ViewHolder(view) {
        val progressBar: ProgressBar = view.findViewById(R.id.feed_pb_load_more)
    }

    fun getVHolder(position: Int, call: VHolderCallback) {
        // 调用两个参数的这个刷新
        notifyItemChanged(position, call)
    }

    fun interface VHolderCallback {
        fun call(holder: HeaderHolder)
    }

    inner class HeaderHolder(view: View) : RecyclerView.ViewHolder(view) {
        val banner: ViewPager2 = view.findViewById(R.id.banner_viewpager)
        init {
            setIsRecyclable(false)
            bannerAdapter = BannerAdapter(bannerData) { view, videoBean ->
                onClick(view, videoBean)
            }
            banner.adapter = bannerAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            FOOT -> return FootHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recycler_feed_load_more, parent, false)
            )
            NORMAL -> return NormalHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recycler_feed, parent, false)
            )
            HEADER -> return HeaderHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false)
            )
            else -> return FootHolder(
                View.inflate(appContext, R.layout.item_recycler_feed_load_more, null)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NormalHolder) {
            holder.apply {
                holder.mTvTitle.text = data[position].videoTitle
                mTvLabel.text = data[position].authorName
                mTvTime.text = data[position].videoDuration
                Glide.with(mIvCover).load(data[position].videoCover).centerCrop()
                    .into(mIvCover)
                Glide.with(mIvAuthor).load(data[position].authorIcon).centerCrop()
                    .into(mIvAuthor)
            }
        } else if (holder is FootHolder) {
            holder.apply {
                if (!fadeTip) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.INVISIBLE
                }
            }
        } else {
            holder as HeaderHolder
            holder.apply {
                banner.clipChildren = false
                banner.setPageTransformer(BannerTransformer())
                banner.offscreenPageLimit = 1
                banner.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        banner.currentItem = position
                    }
                    override fun onPageScrollStateChanged(state: Int) {
                        //只有在空闲状态，才让自动滚动
                        if (state == ViewPager2.SCROLL_STATE_IDLE) {
                            Log.d("tag", "(FeedRecyclerAdapter.kt:175) -> 是否滚动")
                            if (banner.currentItem == 0) {
                                banner.setCurrentItem(bannerAdapter.itemCount - 2, false)
                            } else if (banner.currentItem == bannerAdapter.itemCount - 1) {
                                banner.setCurrentItem(1, false)
                            }
                        }
                    }
                })
                val mLooper = object : Runnable {
                    override fun run() {
                        banner.currentItem = ++banner.currentItem
                        banner.postDelayed(this, 2000)
                    }
                }
                banner.postDelayed(mLooper, 2000)
            }
        }
    }

    // Rv 得到某个 item 的实例
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            payloads.forEach {
                if (it is VHolderCallback) {
                    if (holder is HeaderHolder) {
                        it.call(holder)
                    }
                }
            }
        }
    }

    fun isFadeTips(): Boolean {
        return fadeTip
    }

    // 因为有尾布局,要+1
    override fun getItemCount() = data.size + 1
    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> 0
            itemCount - 1 -> FOOT
            else -> NORMAL
        }
    }
}