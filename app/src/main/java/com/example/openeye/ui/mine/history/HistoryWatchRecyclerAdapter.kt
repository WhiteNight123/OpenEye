package com.example.openeye.ui.mine.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openeye.R
import com.example.openeye.logic.model.VideoDetailData

/**
 * @author WhiteNight(1448375249@qq.com)
 * @date 2022-07-22
 * @description
 */
class HistoryWatchRecyclerAdapter(
    private val data: ArrayList<VideoDetailData>,
    private val onClick: (view: View, videoBean: VideoDetailData) -> Unit,
) : RecyclerView.Adapter<HistoryWatchRecyclerAdapter.InnerHolder>() {

    inner class InnerHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mIvCover: ImageView = view.findViewById(R.id.history_iv_watch_cover)
        val mTvTitle: TextView = view.findViewById(R.id.history_tv_watch_title)
        val mTvAuthor: TextView = view.findViewById(R.id.history_tv_watch_author)
        val mTvTime: TextView = view.findViewById(R.id.history_tv_watch_time)
        val mTvDuration: TextView = view.findViewById(R.id.history_tv_watch_duration)

        init {
            mIvCover.setOnClickListener {
                onClick(mIvCover, data[absoluteAdapterPosition])
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycler_watch_history, parent, false)
        return InnerHolder(view)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        holder.apply {
            mTvTitle.text = data[position].videoTitle
            mTvAuthor.text = data[position].authorName
            mTvTime.text = data[position].nextPageUrl
            mTvDuration.text = data[position].videoDuration
            Glide.with(mIvCover).load(data[position].videoCover).centerCrop()
                .into(mIvCover)
        }
    }

    override fun getItemCount(): Int = data.size
}