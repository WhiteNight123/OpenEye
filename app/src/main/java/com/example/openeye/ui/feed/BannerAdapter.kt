package com.example.openeye.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openeye.R
import com.example.openeye.logic.model.VideoDetailsBean

class BannerAdapter(
    private val data: ArrayList<VideoDetailsBean>,
    private val onClick: (view: View, videoBean: VideoDetailsBean) -> Unit
) : RecyclerView.Adapter<BannerAdapter.InnerHolder>() {
    inner class InnerHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mIvCover: ImageView = view.findViewById(R.id.banner_iv_picture)

        init {
            mIvCover.setOnClickListener {
                onClick(mIvCover, data[absoluteAdapterPosition])
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false)
        return InnerHolder(view)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        holder.apply {
            Glide.with(mIvCover).load(data[position].videoCover).centerCrop()
                .into(mIvCover)
        }
    }

    override fun getItemCount(): Int = data.size
}