package com.example.openeye.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openeye.R
import com.example.openeye.logic.model.VideoDetailsBean

class FeedRecyclerAdapter(
    private val data: ArrayList<VideoDetailsBean>,
    private val onClick: (view: View, videoBean: VideoDetailsBean) -> Unit
) : RecyclerView.Adapter<FeedRecyclerAdapter.InnerHolder>() {
    inner class InnerHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mTvTitle: TextView
        val mTvLable: TextView
        var mTvTime: TextView
        var mIvCover: ImageView
        var mIvAuthor: ImageView

        init {
            mTvTitle = view.findViewById(R.id.feed_tv_title)
            mTvLable = view.findViewById(R.id.feed_tv_label)
            mTvTime = view.findViewById(R.id.feed_tv_time)
            mIvAuthor = view.findViewById(R.id.feed_iv_author)
            mIvCover = view.findViewById(R.id.feed_iv_cover)
            mIvCover.setOnClickListener {
                onClick(mIvCover, data[absoluteAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycle_feed, parent, false)
        return InnerHolder(view)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {

        holder.apply {
            holder.mTvTitle.text = data[position].videoTitle
            mTvLable.text = data[position].authorName
            mTvTime.text = data[position].videoDuration
            Glide.with(mIvCover).load(data[position].videoCover).centerCrop()
                .into(mIvCover)
            Glide.with(mIvAuthor).load(data[position].authorIcon).centerCrop()
                .into(mIvAuthor)
        }
    }

    override fun getItemCount() = data.size
}