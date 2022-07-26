package com.example.openeye.ui.video

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openeye.App.Companion.appContext
import com.example.openeye.R
import com.example.openeye.logic.model.VideoDetailData

class VideoRelevantRvAdapter(
    private val data: ArrayList<VideoDetailData>,
    private val onClick: (view: View, videoBean: VideoDetailData) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TITLE = 1
        const val DETAIL = 2
    }

    inner class TitleHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mTvTitle: TextView
        init {
            mTvTitle = view.findViewById(R.id.video_tv_relevant_title)
        }
    }

    inner class DetailHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mTvTitle: TextView = view.findViewById(R.id.history_tv_watch_title)
        val mTvAuthor: TextView = view.findViewById(R.id.history_tv_watch_author)
        var mTvTime: TextView = view.findViewById(R.id.history_tv_watch_duration)
        var mIvCover: ImageView = view.findViewById(R.id.history_iv_watch_cover)
        var mConstraintLayout: ConstraintLayout =
            view.findViewById(R.id.video_constraint_layout_detail)
        init {
            mConstraintLayout.setOnClickListener {
                onClick(mIvCover, data[absoluteAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            TITLE -> return TitleHolder(
                View.inflate(
                    appContext,
                    R.layout.item_recycler_video_relevant_header,
                    null
                )
            )
            DETAIL -> return DetailHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recycler_video_relevant_detail, parent, false)
            )
            else -> return TitleHolder(
                View.inflate(
                    appContext,
                    R.layout.item_recycler_video_relevant_header,
                    null
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TitleHolder) {
            holder.mTvTitle.text = data[position].videoTitle
        } else {
            holder as DetailHolder
            holder.apply {
                mTvTitle.text = data[position].videoTitle
                mTvAuthor.text = data[position].authorName
                mTvTime.text = data[position].videoDuration
                mIvCover.transitionName = "video_cover$position"
                Glide.with(mIvCover).load(data[position].videoCover).centerCrop()
                    .into(mIvCover)
            }
        }
    }

    override fun getItemCount() = data.size

    // 第一个是头标题,下面5个是具体数据
    override fun getItemViewType(position: Int): Int {
        return if (position % 6 == 0) {
            TITLE
        } else {
            DETAIL
        }
    }
}