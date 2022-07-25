package com.example.openeye.ui.explore.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
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
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        val NORMAL = 1
        val FOOT = 2
    }

    var fadeTip: Boolean = false

    inner class InnerHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mIvCover: ImageView = view.findViewById(R.id.community_iv_cover)
        val mTvTitle: TextView = view.findViewById(R.id.community_tv_title)
        val mTvAuthor: TextView = view.findViewById(R.id.community_tv_author)
        val mTvLike: TextView = view.findViewById(R.id.community_tv_like)
        val mIvAuthor: ImageView = view.findViewById(R.id.community_iv_author)
        val mIvPhoto: ImageView = view.findViewById(R.id.community_iv_photo)

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

    inner class FootHolder(view: View) : RecyclerView.ViewHolder(view) {
        val progressBar: ProgressBar = view.findViewById(R.id.feed_pb_load_more)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == NORMAL) {
            return InnerHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recycler_community, parent, false)
            )
        } else {
            return FootHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recycler_feed_load_more, parent, false)
            )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is InnerHolder) {
            holder.apply {
                if (data[position].pictureList.size > 1) {
                    mIvPhoto.visibility = View.VISIBLE
                }
                mTvTitle.text = data[position].pictureDescription
                mTvAuthor.text = data[position].authorName
                mTvLike.text = data[position].likeCount.toString()
                Glide.with(mIvCover).load(data[position].videoCover).centerCrop()
                    .into(mIvCover)
                Glide.with(mIvAuthor).load(data[position].authorIcon).centerCrop().into(mIvAuthor)
            }
        } else {
            holder as FootHolder

            if (!fadeTip) {
                holder.progressBar.visibility = View.VISIBLE
            } else {
                holder.progressBar.visibility = View.INVISIBLE
            }
        }
    }

    fun isFadeTips(): Boolean {
        return fadeTip
    }

    override fun getItemViewType(position: Int): Int {
        if (position == itemCount - 1) {
            return FOOT
        } else {
            return NORMAL
        }
    }

    override fun getItemCount(): Int = data.size + 1
}