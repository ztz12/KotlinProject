package com.wanandroid.zhangtianzhu.kotlinproject.activity

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wanandroid.zhangtianzhu.kotlinproject.R
import com.wanandroid.zhangtianzhu.kotlinproject.adapter.ListViewAdapter
import kotlinx.android.synthetic.main.activity_list_view_anim.*

class ListViewAnimActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_anim)
        initLv()
    }

    private fun initLv(){
        val list = ArrayList<Drawable>()
        list.add(resources.getDrawable(R.drawable.iv_eye_3))
        list.add(resources.getDrawable(R.drawable.iv_eye_4))
        list.add(resources.getDrawable(R.drawable.iv_eye_5))
        list.add(resources.getDrawable(R.drawable.iv_eye_6))
        val adapter = ListViewAdapter(this,lvAnim,list,300)
        lvAnim.adapter = adapter
    }
}
