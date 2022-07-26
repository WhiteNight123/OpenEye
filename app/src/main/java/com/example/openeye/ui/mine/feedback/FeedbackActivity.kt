package com.example.openeye.ui.mine.feedback

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.openeye.R
import com.example.openeye.ui.base.BaseActivity
import com.example.openeye.utils.toast


class FeedbackActivity : BaseActivity() {
    lateinit var toolbar: Toolbar
    lateinit var mTvQQ: TextView
    lateinit var mTvGithub: TextView
    lateinit var mTvEmail: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        mTvQQ = findViewById(R.id.feedback_tv_qq_num)
        toolbar = findViewById(R.id.feedback_toolbar)
        mTvGithub = findViewById(R.id.feedback_github_url)
        mTvEmail = findViewById(R.id.feedback_tv_email_add)
        toolbar.title = "意见反馈"
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true);
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mTvQQ.setOnClickListener {
            startQQ(mTvQQ.text.toString())
        }
        mTvGithub.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mTvGithub.text.toString())))
        }
        mTvEmail.setOnClickListener {
            val clipboardManager: ClipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val mClipData = ClipData.newPlainText("email", mTvEmail.text.toString())
            clipboardManager.setPrimaryClip(mClipData)
            "成功复制到剪贴板".toast()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    // 跳转QQ聊天界面
    private fun startQQ(qq: String) {
        try {
            val url = "mqqwpa://im/chat?chat_type=wpa&uin=$qq" //uin是发送过去的qq号码
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (e: Exception) {
            e.printStackTrace()
            "请检查是否安装QQ".toast()
        }
    }
}