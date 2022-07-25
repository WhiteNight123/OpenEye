package com.example.openeye.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openeye.R
import com.example.openeye.logic.model.VideoDetailData

class BannerAdapter(
    private val data: ArrayList<VideoDetailData>,
    private val onClick: (view: View, videoBean: VideoDetailData) -> Unit
) : RecyclerView.Adapter<BannerAdapter.InnerHolder>() {
    inner class InnerHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mIvCover: ImageView = view.findViewById(R.id.feed_iv_banner_picture)
        val mTvTitle: TextView = view.findViewById(R.id.feed_tv_banner_title)

        init {
            mIvCover.setOnClickListener {
                onClick(mIvCover, data[absoluteAdapterPosition])
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_banner_detail, parent, false)
        return InnerHolder(view)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        holder.apply {
            mTvTitle.text = data[position].videoTitle
            Glide.with(mIvCover).load(data[position].videoCover).centerCrop()
                .into(mIvCover)
        }
    }

    override fun getItemCount(): Int = data.size
}