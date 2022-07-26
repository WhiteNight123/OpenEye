package com.example.openeye.ui.rank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openeye.App.Companion.appContext
import com.example.openeye.R
import com.example.openeye.logic.model.VideoDetailData
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

class RankDetailRvAdapter(
    private val data: ArrayList<VideoDetailData>,
) : RecyclerView.Adapter<RankDetailRvAdapter.InnerHolder>() {

    companion object {
        const val TAG = "RankRecyclerViewVideo"
    }

    inner class InnerHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mTvTitle: TextView = view.findViewById(R.id.rank_tv_title)
        val mTvLabel: TextView = view.findViewById(R.id.rank_tv_label)
        var mVideoPlayer: StandardGSYVideoPlayer = view.findViewById(R.id.rank_video_detail)
        var mIvAuthor: ImageView = view.findViewById(R.id.rank_iv_author)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InnerHolder {
        return InnerHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycler_rank_detail, parent, false)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        holder.apply {
            mVideoPlayer.setUpLazy(data[position].videoUrl, true, null, null, null);
            val imageView = ImageView(appContext)
            Glide.with(appContext)
                .load(data[position].videoCover)
                .centerCrop()
                .into(imageView);
            mVideoPlayer.thumbImageView = imageView
            mVideoPlayer.thumbImageViewLayout.visibility = View.VISIBLE
            //设置返回键
            mVideoPlayer.backButton.visibility = View.GONE;
            mVideoPlayer.playTag = TAG;

            mTvTitle.text = data[position].videoTitle
            mTvLabel.text = data[position].authorName
            Glide.with(mIvAuthor).load(data[position].authorIcon).centerCrop()
                .into(mIvAuthor)
        }
    }
}
