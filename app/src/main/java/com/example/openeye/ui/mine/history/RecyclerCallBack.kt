package com.example.openeye.ui.mine.history

import android.util.Log
import androidx.annotation.NonNull
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


/**
 * @author WhiteNight(1448375249@qq.com)
 * @date 2022-07-25
 * @description
 */
class Callback(mAdapter: HistoryWatchRecyclerAdapter) : ItemTouchHelper.Callback() {
    private val mAdapter: HistoryWatchRecyclerAdapter

    /**
     * 设置上下左右动作
     * 只有在此处打开了指定方向的设置 , 才可以应用具体方向的拖动
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {

        // 设置拖动方向, 此处设置上下拖动事件
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        // 设置滑动方向, 此处设置左右侧滑事件
        val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        // 应用 拖动 和 滑动 设置
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return false
    }
    /**
     * 监听滑动事件
     * 滑动分 水平 / 垂直 两个方向
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {

        // 拖动后交换数据, 该方法中交换 Adapter 中的数据, 并刷新界面
        Log.i(TAG, "触发拖动交换条目")
        return false
    }

    /**
     * 是否启用滑动操作
     * @return 是否启用 true 启用, false 不启用
     */
    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    /**
     * 用户滑动距离, 设置的是比例值, 返回值为 0.5 , 就意味着滑动宽度/高度的一半, 才触发侧滑 onSwiped 方法
     * @param viewHolder
     * @return
     */
    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.3f
    }

    /**
     * 滑动判定速度, 每秒移动的像素个数, 达到该速度后, 才可以被判定为滑动
     * @param defaultValue
     * @return
     */
    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return 5000f
    }

    /**
     * 手指离开后的动画持续时间
     */
    override fun getAnimationDuration(
        recyclerView: RecyclerView,
        animationType: Int,
        animateDx: Float,
        animateDy: Float
    ): Long {
        return 200L
    }

    /**
     * 滑动时的回调操作
     * @param viewHolder
     * @param direction
     */
    override fun onSwiped(@NonNull viewHolder: RecyclerView.ViewHolder, direction: Int) {
        Log.i(TAG, "触发侧滑删除条目")
        // 删除指定条目, 并刷新界面
        mAdapter.deleteItem(viewHolder.absoluteAdapterPosition)
    }

    companion object {
        private const val TAG = "Callback"
    }

    init {
        this.mAdapter = mAdapter
    }
}