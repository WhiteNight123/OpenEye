package com.example.openeye.ui.explore.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openeye.App
import com.example.openeye.R
import com.github.chrisbanes.photoview.PhotoView

/**
 * @author WhiteNight(1448375249@qq.com)
 * @date 2022-07-25
 * @description
 */
class PictureViewpagerAdapter(
    private val data: ArrayList<String>,
) : RecyclerView.Adapter<PictureViewpagerAdapter.InnerHolder>() {

    inner class InnerHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photoView: PhotoView = view.findViewById(R.id.picture_photo_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view = LayoutInflater.from(App.appContext)
            .inflate(R.layout.item_recycler_picture, parent, false)
        return InnerHolder(view)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        holder.apply {
            Glide.with(photoView).load(data[position]).into(photoView)
        }
    }

    override fun getItemCount() = data.size
}