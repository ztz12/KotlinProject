package com.wanandroid.zhangtianzhu.kotlinproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.wanandroid.zhangtianzhu.kotlinproject.R
import com.wanandroid.zhangtianzhu.kotlinproject.adapter.MyRefreshAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var mAdapter: MyRefreshAdapter
    private val mHandler by lazy { Handler(Looper.getMainLooper()) }
    private val PAGE_ITEM_COUNT = 10
    private var mLastVisibleItem = 0
    private var list: MutableList<String> = ArrayList()
    private val mLayoutManager by lazy { LinearLayoutManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intData()
        initRefreshLayout()
        initRecyclerView()
    }

    /**
     * 初始化数据
     */
    private fun intData() {
        for (i in 1..40) {
            list.add("条目$i")
        }
    }

    private fun initRefreshLayout() {
        refreshLayout.setColorSchemeResources(
            android.R.color.holo_blue_light, android.R.color.holo_red_light,
            android.R.color.holo_orange_light, android.R.color.holo_green_light
        )
        refreshLayout.setOnRefreshListener(this)
    }

    private fun initRecyclerView() {
        mAdapter = MyRefreshAdapter(this, getData(0, PAGE_ITEM_COUNT), getData(0, PAGE_ITEM_COUNT).size > 0)
        rl.run {
            layoutManager = mLayoutManager
            adapter = mAdapter
            itemAnimator = DefaultItemAnimator()
        }

        //实现上拉加载步骤，需要设置滑动监听器，RecyclerView自带的ScrollListener
        rl.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //当滑动到最底部时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //如果没有隐藏footerView ，由于可见item位置是从0开始，所以最后一个条目就比getItemCount少1
                    if (!mAdapter.isFadeTips() && mLastVisibleItem + 1 == mAdapter.itemCount) {
                        mHandler.postDelayed({
                            updateRecyclerView(
                                mAdapter.getLastRealLastPosition(),
                                mAdapter.getLastRealLastPosition() + PAGE_ITEM_COUNT
                            )
                        }, 500)
                    }

                    //如果隐藏了底部提示条，最后条目就比getItemCount少2
                    if (mAdapter.isFadeTips() && mLastVisibleItem + 2 == mAdapter.itemCount) {
                        mHandler.postDelayed({
                            updateRecyclerView(
                                mAdapter.getLastRealLastPosition(),
                                mAdapter.getLastRealLastPosition() + PAGE_ITEM_COUNT
                            )
                        }, 500)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //在滑动完成后，拿到最后一个可见item
                mLastVisibleItem = mLayoutManager.findLastVisibleItemPosition()
            }
        })
    }

    /**
     * 获取数据
     */
    private fun getData(firstIndex: Int, lastIndex: Int): MutableList<String> {
        val newData: MutableList<String> = ArrayList()
        for (i in firstIndex..lastIndex) {
            if (i < list.size) {
                newData.add(list[i])
            }
        }
        return newData
    }

    /**
     * 刷新recyclerView列表
     */
    private fun updateRecyclerView(firstIndex: Int, lastIndex: Int) {
        val newData: MutableList<String> = getData(firstIndex, lastIndex)
        if (newData.size > 0) {
            mAdapter.updateData(newData, true)
        } else {
            mAdapter.updateData(null, false)
        }
    }

    override fun onRefresh() {
        refreshLayout.isRefreshing = true
        mAdapter.resetData()
        updateRecyclerView(0, PAGE_ITEM_COUNT)
        //模拟网络操作，1秒后结束下拉刷新
        mHandler.postDelayed({
            refreshLayout.isRefreshing = false
        }, 1000)
    }


    override fun onBackPressed() {
        Toast.makeText(this, "onBackPressed", Toast.LENGTH_LONG).show()
        super.onBackPressed()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "onKeyDown", Toast.LENGTH_LONG).show()
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "onKeyUp", Toast.LENGTH_LONG).show()
        }
        return super.onKeyUp(keyCode, event)
    }

    override fun onUserInteraction() {
        Toast.makeText(this, "onUserInteraction", Toast.LENGTH_LONG).show()
        super.onUserInteraction()
    }

    //手机按键操作首先从dispatchKeyEvent开始触发
    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "dispatchKeyEvent", Toast.LENGTH_LONG).show()
            //这里进行返回false或者true，那么onUserInteraction，onKeyDown或者onKeyUp方法都不会执行
            return false
        }
        return super.dispatchKeyEvent(event)
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null)
    }
}
