package com.example.openeye.ui.explore.topic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openeye.R
import com.example.openeye.logic.model.TopicData

class TopicRecyclerAdapter(
    private val data: ArrayList<TopicData>,
    private val onClick: (view: View, videoBean: TopicData) -> Unit
) : RecyclerView.Adapter<TopicRecyclerAdapter.InnerHolder>() {
    inner class InnerHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mIvCover: ImageView = view.findViewById(R.id.topic_iv_cover)

        init {
            mIvCover.setOnClickListener {
                onClick(mIvCover, data[absoluteAdapterPosition])
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_topic, parent, false)
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