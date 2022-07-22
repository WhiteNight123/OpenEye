package com.example.openeye.ui.explore.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openeye.R
import com.example.openeye.logic.model.CategoryData

/**
 * @author WhiteNight(1448375249@qq.com)
 * @date 2022-07-22
 * @description
 */
class CategoryRecyclerAdapter(
    private val data: ArrayList<CategoryData>,
    private val onClick: (view: View, categoryData: CategoryData) -> Unit
) : RecyclerView.Adapter<CategoryRecyclerAdapter.InnerHolder>() {
    inner class InnerHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mIvCover: ImageView = view.findViewById(R.id.category_iv_cover)
        val mTvTitle: TextView = view.findViewById(R.id.category_tv_title)

        init {
            mIvCover.setOnClickListener {
                onClick(mIvCover, data[absoluteAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycler_category, parent, false)
        return InnerHolder(view)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        holder.apply {
            mTvTitle.text = data[position].title
            Glide.with(mIvCover).load(data[position].cover).centerCrop()
                .into(mIvCover)
        }
    }

    override fun getItemCount(): Int = data.size
}