package com.wanandroid.zhangtianzhu.kotlinproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.wanandroid.zhangtianzhu.kotlinproject.R
import com.wanandroid.zhangtianzhu.kotlinproject.adapter.RefreshHeaderAdapter
import com.wanandroid.zhangtianzhu.kotlinproject.utils.OnRefreshListener
import kotlinx.android.synthetic.main.activity_arrow_refresh_header.*

class ArrowRefreshHeaderActivity : AppCompatActivity() {

    private val mHandler by lazy { Handler(Looper.getMainLooper()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arrow_refresh_header)
        init()
    }

    private fun init() {
        val list: MutableList<String> = ArrayList()
        for (i in 0..15) {
            list.add("")
        }
        val refreshAdapter = RefreshHeaderAdapter(list, this)
        arrowRl.run {
            layoutManager = LinearLayoutManager(this@ArrowRefreshHeaderActivity)
        }
        arrowRl.setRefreshAdapter(refreshAdapter)
        arrowRl.setOnRefreshListener(object : OnRefreshListener {
            override fun onRefresh() {
                mHandler.postDelayed({
                    Toast.makeText(this@ArrowRefreshHeaderActivity, "刷新完成！", Toast.LENGTH_SHORT).show()
                    arrowRl.refreshComplete()
                }, 1500)
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null)
    }
}
