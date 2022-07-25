package com.example.openeye.ui.mine

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.openeye.R
import com.example.openeye.ui.mine.feedback.FeedbackActivity
import com.example.openeye.ui.mine.history.HistoryWatchActivity


private const val ARG_PARAM1 = "param1"


class MineFragment : Fragment() {
    lateinit var mIvPortrait: ImageView
    lateinit var mTvHistoryWatch: TextView
    lateinit var mTvMessage: TextView
    lateinit var mTvFeedback: TextView

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTvHistoryWatch = view.findViewById(R.id.mine_tv_history_watch)
        mTvFeedback = view.findViewById(R.id.mine_tv_feedback)
        mIvPortrait = view.findViewById(R.id.mine_iv_portrait)
        mTvHistoryWatch.setOnClickListener {
            val intent = Intent(context, HistoryWatchActivity::class.java)
            startActivity(intent)
        }

        mTvFeedback.setOnClickListener {
            val intent = Intent(context, FeedbackActivity::class.java)
            // 这里用共享元素动画会有奇怪的bug
//            val options =
//                ActivityOptions.makeSceneTransitionAnimation(
//                    activity,
//                    mIvPortrait,
//                    mIvPortrait.transitionName
//                )
            startActivity(intent)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            MineFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}