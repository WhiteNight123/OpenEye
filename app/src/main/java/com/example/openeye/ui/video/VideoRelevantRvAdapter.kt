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
import com.example.openeye.logic.model.VideoDetailsBean

class VideoRelevantRvAdapter(
    private val data: ArrayList<VideoDetailsBean>,
    private val onClick: (view: View, videoBean: VideoDetailsBean) -> Unit
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
        var mTvTitle: TextView
        val mTvAuthor: TextView
        var mTvTime: TextView
        var mIvCover: ImageView
        var mConstraintLayout: ConstraintLayout

        init {
            mTvTitle = view.findViewById(R.id.video_tv_relevant_detail_title)
            mTvAuthor = view.findViewById(R.id.video_tv_relevant_detail_author)
            mTvTime = view.findViewById(R.id.video_tv_relevant_detail_time)
            mIvCover = view.findViewById(R.id.video_iv_relevant_detail_icon)
            mConstraintLayout = view.findViewById(R.id.video_constraint_layout_detail)
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
                    R.layout.item_recycle_video_relevant_title,
                    null
                )
            )
            DETAIL -> return DetailHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recycler_item_relevant_detail, parent, false)
            )
            else -> return TitleHolder(
                View.inflate(
                    appContext,
                    R.layout.item_recycle_video_relevant_title,
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
    override fun getItemViewType(position: Int): Int {
        return if (position % 6 == 0) {
            TITLE
        } else {
            DETAIL
        }
    }
}