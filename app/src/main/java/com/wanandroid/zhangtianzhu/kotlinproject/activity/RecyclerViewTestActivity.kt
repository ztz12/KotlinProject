package com.wanandroid.zhangtianzhu.kotlinproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wanandroid.zhangtianzhu.kotlinproject.R
import com.wanandroid.zhangtianzhu.kotlinproject.adapter.RecyclerAdapter
import com.wanandroid.zhangtianzhu.kotlinproject.utils.CustomLayoutManager
import com.wanandroid.zhangtianzhu.kotlinproject.utils.LinearItemDecoration
import kotlinx.android.synthetic.main.activity_recycler_view_test.*
import java.util.ArrayList

class RecyclerViewTestActivity : AppCompatActivity() {
    private val arrayList:ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_test)
        generateData()
        val adapterRl = RecyclerAdapter(this,arrayList)
        //初始化分割线
        val itemDivider = DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
        rl.run {
            adapter = adapterRl
//            layoutManager = LinearLayoutManager(this@RecyclerViewTestActivity)
            layoutManager = CustomLayoutManager()
        }
//        rl.addItemDecoration(itemDivider)
    }

    private fun generateData(){
        for(i in 0 until 50){
            arrayList.add("第$i 个 item")
        }
    }
}
