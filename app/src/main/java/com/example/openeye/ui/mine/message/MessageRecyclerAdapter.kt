package com.example.openeye.ui.mine.message

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.openeye.R
import com.example.openeye.logic.model.MessageBean

/**
 * @author WhiteNight(1448375249@qq.com)
 * @date 2022-07-22
 * @description
 */
class MessageRecyclerAdapter(private val data: ArrayList<MessageBean.Message>) :
    RecyclerView.Adapter<MessageRecyclerAdapter.InnerHolder>() {
    inner class InnerHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mTvTitle: TextView = view.findViewById(R.id.message_tv_title)
        val mTvContent: TextView = view.findViewById(R.id.message_tv_content)
        val mTvTime: TextView = view.findViewById(R.id.message_tv_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycler_message, parent, false)
        return InnerHolder(view)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        holder.apply {
            mTvTitle.text = data[position].title
            mTvContent.text = data[position].content
            mTvTime.text = data[position].date.toString()
        }
    }

    override fun getItemCount(): Int = data.size
}