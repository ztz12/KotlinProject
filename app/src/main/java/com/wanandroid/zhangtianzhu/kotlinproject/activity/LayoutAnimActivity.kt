package com.wanandroid.zhangtianzhu.kotlinproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ArrayAdapter
import com.wanandroid.zhangtianzhu.kotlinproject.R
import kotlinx.android.synthetic.main.activity_layout_anim.*
import kotlin.collections.ArrayList


class LayoutAnimActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_anim)
        testLayoutAnim()
    }

    /**
     * layoutAnimation 中的参数
     * delay 每个item动画的延时时间，这里为1，表示下一个item将会在上一个item出现后，延迟1000ms中触发
     * animationOrder:指viewGroup中的控件动画开始顺序，取值有normal(正序)、reverse(倒序)、random(随机)
     */
    private fun testLayoutAnim() {
        val mAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getData())
        lv_layout.adapter = mAdapter
//        startListAnim.setOnClickListener {
//            mAdapter.addAll(getData())
//        }

        //在代码中添加LayoutAnimation
        val animation = AnimationUtils.loadAnimation(this,R.anim.slide_in_left)
        val controller = LayoutAnimationController(animation)
        //设置控件顺序
        controller.order = LayoutAnimationController.ORDER_REVERSE
        //设置间隔时间
        controller.delay = 0.3f
        lv_layout.layoutAnimation = controller
        lv_layout.startLayoutAnimation()
    }

    private fun getData(): List<String> {
        val data = ArrayList<String>()
        data.add("测试数据1")
        data.add("测试数据2")
        data.add("测试数据3")
        data.add("测试数据4")
        return data
    }
}
