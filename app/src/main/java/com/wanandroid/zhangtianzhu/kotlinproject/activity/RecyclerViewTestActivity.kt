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
            //LinearLayoutManager可以进行回收复用view
//            layoutManager = LinearLayoutManager(this@RecyclerViewTestActivity)
            //而此时CustomLayoutManager没有进行回收复用处理，不会调用 onBindViewHolder 进行回收复用view，也就是一次性创建所有的item并加入到列表中，滚动并没有调用
            //onCreateViewHolder与onBindViewHolder进行回收复用
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
