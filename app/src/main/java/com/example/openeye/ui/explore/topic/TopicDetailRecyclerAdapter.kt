package com.example.openeye.ui.explore.topic

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openeye.App
import com.example.openeye.R
import com.example.openeye.logic.model.VideoDetailData2
import com.example.openeye.utils.toast
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

class TopicDetailRecyclerAdapter(
    val context: Context,
    private val data: ArrayList<VideoDetailData2>,
) : RecyclerView.Adapter<TopicDetailRecyclerAdapter.InnerHolder>() {
    companion object {
        const val TAG = "TopicDetailRecyclerViewVideo"
    }

    inner class InnerHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mIvAuthor: ImageView = view.findViewById(R.id.topic_iv_detail_author)
        val mTvTitle: TextView = view.findViewById(R.id.topic_tv_detail_title2)
        val mTvAuthor: TextView = view.findViewById(R.id.topic_tv_detail_author)
        val mTvTime: TextView = view.findViewById(R.id.topic_tv_detail_time)
        val mTvDescribe: TextView = view.findViewById(R.id.topic_tv_detail_describe)
        val mChipGroup: ChipGroup = view.findViewById(R.id.topic_chip_group_tag)
        val videoPlayer: StandardGSYVideoPlayer = view.findViewById(R.id.topic_video_detail)
        val mTvLikeCount: TextView = view.findViewById(R.id.topic_tv_detail_like)
        val mTVReplyCount: TextView = view.findViewById(R.id.topic_tv_detail_reply)

        init {
//            mIvCover.setOnClickListener {
//                onClick(mIvCover, data[absoluteAdapterPosition])
//            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycler_topic_detail, parent, false)
        return InnerHolder(view)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        holder.apply {
            videoPlayer.setUpLazy(data[position].videoUrl, true, null, null, null);
            val imageView = ImageView(App.appContext)
            Glide.with(App.appContext)
                .load(data[position].videoCover)
                .centerCrop()
                .into(imageView);
            videoPlayer.thumbImageView = imageView
            videoPlayer.thumbImageViewLayout.visibility = View.VISIBLE
            //设置返回键
            videoPlayer
                .backButton.visibility = View.GONE;
            videoPlayer.playTag = TAG
            for (i in data[position].tags) {
                val chip = Chip(context)
                Log.e("TAG", "onBindViewHolder: $i")
                chip.text = i
                chip.setOnClickListener {
                    i.toast()
                }
                mChipGroup.addView(chip)
            }
            mTvTitle.text = data[position].videoTitle
            mTvAuthor.text = data[position].authorName
            mTvTime.text = data[position].videoDuration
            mTvDescribe.text = data[position].videoDescription
            mTvLikeCount.text = data[position].likeCount.toString()
            mTVReplyCount.text = data[position].replyCount.toString()
            Glide.with(mIvAuthor).load(data[position].authorIcon).centerCrop()
                .into(mIvAuthor)
        }
    }

    override fun getItemCount(): Int = data.size
}