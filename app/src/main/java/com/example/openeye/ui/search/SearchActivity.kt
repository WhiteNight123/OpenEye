package com.example.openeye.ui.search

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.openeye.R
import com.example.openeye.ui.base.BaseActivity
import com.example.openeye.utils.toast
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class SearchActivity : BaseActivity() {
    //lateinit var flowLayout: FlowLayout1
    lateinit var chipGroup: ChipGroup
    val viewModel by lazy { ViewModelProvider(this).get(SearchActivityViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        //flowLayout = findViewById(R.id.search_flow_layout)
        chipGroup = findViewById(R.id.chipGroup)
        viewModel.getHotSearch()
        viewModel.hotSearch.observe {
            if (it.isNotEmpty()) {
                for (i in it) {
                    val chip = Chip(this@SearchActivity)
                    chip.text = i
                    chip.setOnClickListener {
                        i.toast()
                    }
                    chipGroup.addView(chip)
                }
            }
        }


    }
}




