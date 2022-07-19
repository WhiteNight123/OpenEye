package com.example.openeye.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openeye.App.Companion.appContext
import com.example.openeye.R
import com.example.openeye.logic.model.VideoDetailsBean


class FeedRecyclerAdapter(
    private val data: ArrayList<VideoDetailsBean>,
    private val onClick: (view: View, videoBean: VideoDetailsBean) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val NORMAL = 1
        const val FOOT = 2
    }

    var fadeTip = false

    inner class NormalHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mTvTitle: TextView = view.findViewById(R.id.feed_tv_title)
        val mTvLable: TextView = view.findViewById(R.id.feed_tv_label)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            FOOT -> return FootHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recycler_feed_load_more, parent, false)
            )
            NORMAL -> return NormalHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recycle_feed, parent, false)
            )
            else -> return FootHolder(
                View.inflate(
                    appContext,
                    R.layout.item_recycler_feed_load_more,
                    null
                )
            )
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is NormalHolder) {
            holder.apply {
                holder.mTvTitle.text = data[position].videoTitle
                mTvLable.text = data[position].authorName
                mTvTime.text = data[position].videoDuration
                Glide.with(mIvCover).load(data[position].videoCover).centerCrop()
                    .into(mIvCover)
                Glide.with(mIvAuthor).load(data[position].authorIcon).centerCrop()
                    .into(mIvAuthor)
            }
        } else {
            holder as FootHolder
            holder.apply {
                if (!fadeTip) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.INVISIBLE
                }
            }
        }
    }

    // 暴露接口，改变fadeTips的方法
    fun isFadeTips(): Boolean = fadeTip

    fun getRealLastPosition() = data.size
    override fun getItemCount() = data.size + 1
    override fun getItemViewType(position: Int): Int {
        return when (position) {
            itemCount - 1 -> {
                FOOT
            }
            else -> {
                NORMAL
            }
        }
    }
}