package com.example.openeye.ui.explore.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openeye.R
import com.example.openeye.logic.model.CommunityData

/**
 * @author WhiteNight(1448375249@qq.com)
 * @date 2022-07-23
 * @description
 */
class CommunityRecyclerAdapter(
    private val data: ArrayList<CommunityData>,
    private val onClick: (view: View, communityData: CommunityData) -> Unit
) : RecyclerView.Adapter<CommunityRecyclerAdapter.InnerHolder>() {
    inner class InnerHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mIvCover: ImageView = view.findViewById(R.id.community_iv_cover)
        val mTvTitle: TextView = view.findViewById(R.id.community_tv_title)
        val mTvAuthor: TextView = view.findViewById(R.id.community_tv_author)
        val mTvLike: TextView = view.findViewById(R.id.community_tv_like)
        val mIvAuthor: ImageView = view.findViewById(R.id.community_iv_author)

        init {
            mIvCover.setOnClickListener {
                onClick(mIvCover, data[absoluteAdapterPosition])
            }
            mTvTitle.setOnClickListener {
                if (mTvTitle.maxLines < 10) {
                    mTvTitle.maxLines = 10
                } else {
                    mTvTitle.maxLines = 2
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycler_community, parent, false)
        return InnerHolder(view)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        holder.apply {
            mTvTitle.text = data[position].pictureDescription
            mTvAuthor.text = data[position].authorName
            mTvLike.text = data[position].likeCount.toString()
            Glide.with(mIvCover).load(data[position].videoCover).centerCrop()
                .into(mIvCover)
            Glide.with(mIvAuthor).load(data[position].authorIcon).centerCrop().into(mIvAuthor)
        }
    }

    override fun getItemCount(): Int = data.size
}