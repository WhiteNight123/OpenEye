package com.example.openeye.ui.mine.message

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeye.R
import com.example.openeye.ui.base.BaseActivity

class MessageActivity : BaseActivity() {
    private val viewModel by lazy { ViewModelProvider(this)[MessageActivityViewModel::class.java] }
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MessageRecyclerAdapter
    lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        toolbar = findViewById(R.id.message_toolbar)
        recyclerView = findViewById(R.id.message_rv)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = "我的消息"
        initRecyclerView()
        viewModel.getMessage()
        viewModel.messageBean.observe(this) {
            viewModel.messageData.clear()
            viewModel.messageData.addAll(it.messageList)
            adapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.layoutAnimation = // 入场动画
            LayoutAnimationController(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.recycler_view_fade_in
                )
            )
        adapter = MessageRecyclerAdapter(viewModel.messageData)
        recyclerView.adapter = adapter
    }
}