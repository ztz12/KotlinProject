package com.wanandroid.zhangtianzhu.kotlinproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wanandroid.zhangtianzhu.kotlinproject.R

class SelfViewGroupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_self_view_group)
//        runBlocking {
//            launch {
//                //运行在主协程中
//                tv.text = "xsy"
//                Toast.makeText(this@SelfViewGroupActivity, "年后", Toast.LENGTH_SHORT).show()
//            }
//        }
    }
}
