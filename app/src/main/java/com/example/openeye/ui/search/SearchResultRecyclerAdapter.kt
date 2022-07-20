package com.example.openeye.ui.search


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openeye.App.Companion.appContext
import com.example.openeye.R
import com.example.openeye.logic.model.VideoDetailsBean


class SearchResultRecyclerAdapter(
    private val data: ArrayList<VideoDetailsBean>,
    private val onClick: (view: View, videoBean: VideoDetailsBean) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val NORMAL = 1
        const val FOOT = 2
    }

    var fadeTip = false

    inner class NormalHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mTvTitle: TextView = view.findViewById(R.id.search_tv_result_title)
        val mTvAuthor: TextView = view.findViewById(R.id.search_tv_result_author)
        var mTvTime: TextView = view.findViewById(R.id.search_tv_result_time)
        var mIvCover: ImageView = view.findViewById(R.id.search_iv_result_icon)
        var mConstraintLayout: ConstraintLayout =
            view.findViewById(R.id.search_constraint_layout_detail)

        init {
            mConstraintLayout.setOnClickListener {
                onClick(mIvCover, data[absoluteAdapterPosition])
            }
        }
    }

    inner class FootHolder(view: View) : RecyclerView.ViewHolder(view) {
        val progressBar: ProgressBar = view.findViewById(R.id.feed_pb_load_more)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            FOOT -> return FootHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recycler_feed_load_more, parent, false)
            )
            NORMAL -> return NormalHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recycler_search_result, parent, false)
            )
            else -> return FootHolder(
                View.inflate(
                    appContext,
                    R.layout.item_recycler_feed_load_more,
                    null
                )
            )
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is NormalHolder) {
            holder.apply {
                mTvTitle.text = data[position].videoTitle
                mTvAuthor.text = data[position].authorName
                mTvTime.text = data[position].videoDuration
                mIvCover.transitionName = "video_cover$position"
                Glide.with(mIvCover).load(data[position].videoCover).centerCrop()
                    .into(mIvCover)
            }
        } else {
            holder as FootHolder
            holder.apply {
                if (!fadeTip) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.INVISIBLE
                }
            }
        }
    }

    fun getRealLastPosition() = data.size
    override fun getItemCount() = data.size + 1
    override fun getItemViewType(position: Int): Int {
        return when (position) {
            itemCount - 1 -> {
                FOOT
            }
            else -> {
                NORMAL
            }
        }
    }
}