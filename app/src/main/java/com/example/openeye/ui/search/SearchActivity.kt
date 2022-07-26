package com.example.openeye.ui.search

import android.os.Bundle
import android.transition.Fade
import android.transition.Slide
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.openeye.R
import com.example.openeye.logic.room.HistorySearchEntity
import com.example.openeye.ui.base.BaseActivity
import com.example.openeye.utils.toast
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.transition.platform.MaterialSharedAxis


class SearchActivity : BaseActivity() {
    lateinit var chipGroupHotSearch: ChipGroup
    lateinit var chipGroupHistory: ChipGroup
    lateinit var mTvCleanHistory: TextView
    lateinit var mEtSearch: EditText
    lateinit var mIvCleanTest: ImageView
    lateinit var mIvBack: ImageView


    val viewModel by lazy { ViewModelProvider(this).get(SearchActivityViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        val enter = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
        }
        window.enterTransition = enter
        // TODO: Configure a return transition in the backwards direction.

        // Allow Activity A’s exit transition to play at the same time as this Activity’s
        // enter transition instead of playing them sequentially.
        window.allowEnterTransitionOverlap = true
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        chipGroupHotSearch = findViewById(R.id.topic_chip_group_tag)
        chipGroupHistory = findViewById(R.id.search_chip_group_history)
        mTvCleanHistory = findViewById(R.id.mine_tv_clean_history)
        mEtSearch = findViewById(R.id.search_et_search)
        mIvCleanTest = findViewById(R.id.search_iv_clean_search)
        mIvBack = findViewById(R.id.search_iv_back)


        viewModel.hotSearch.observe {
            if (it.isNotEmpty()) {
                for (i in it) {
                    val chip = Chip(this@SearchActivity)
                    chip.text = i
                    chip.setOnClickListener {
                        mEtSearch.text.clear()
                        mEtSearch.setText(i)
                        startFragment(mEtSearch.text.toString())

                    }
                    // 动态插入chip
                    chipGroupHotSearch.addView(chip)
                }
            } else {
                "获取热搜失败\n请检查网络T_T".toast()
            }
        }
        viewModel.historySearch.observe {
            for (i in it) {
                val chip = Chip(this@SearchActivity)
                chip.text = i.key
                chip.setOnClickListener {
                    mEtSearch.text.clear()
                    mEtSearch.setText(i.key)
                    startFragment(mEtSearch.text.toString())
                }
                // 动态插入chip
                chipGroupHistory.addView(chip)
            }
        }

        mTvCleanHistory.setOnClickListener {
            viewModel.deleteHistory()
            chipGroupHistory.removeAllViews()
        }
        mIvCleanTest.setOnClickListener {
            mEtSearch.text.clear()
            val imm = this
                .getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(mEtSearch.windowToken, 0)
        }
        mEtSearch.setOnEditorActionListener { textView, actionId, event ->
            //viewModel.getSearch(textView.text.toString())
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (textView.text != null) {
                    Log.d("TAG", "onCreate: ${textView.text}")
                    startFragment(textView.text.toString())
                }
            }
            false
        }
        mEtSearch.addTextChangedListener {
            if (mEtSearch.text != null) {
                mIvCleanTest.visibility = View.VISIBLE
            } else {
                mIvCleanTest.visibility = View.INVISIBLE
            }
        }
        mIvBack.setOnClickListener {
            finish()
        }
    }

    private fun startFragment(key: String) {
        // 将数据存入本地历史
        viewModel.insertHistory(HistorySearchEntity(key))
        val fragment = SearchResultFragment()
        val bundle = Bundle()
        bundle.putString("key", key)
        // 转场动画
        fragment.arguments = bundle
        fragment.sharedElementEnterTransition = Fade()
        window.exitTransition = Slide()
        fragment.sharedElementEnterTransition = Slide()
        fragment.sharedElementReturnTransition = Fade()
        supportFragmentManager.beginTransaction().replace(R.id.search_constraint_layout, fragment)
            .commit()
    }
}




