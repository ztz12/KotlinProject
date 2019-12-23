package com.wanandroid.zhangtianzhu.kotlinproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.wanandroid.zhangtianzhu.kotlinproject.R
import com.wanandroid.zhangtianzhu.kotlinproject.view.WaterfallLayout
import kotlinx.android.synthetic.main.activity_self_view_group.*
import kotlinx.coroutines.*
import kotlin.random.Random

class SelfViewGroupActivity : AppCompatActivity() {

    private val IMAGE_COUNT = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_self_view_group)
        runBlocking {
            launch {
                //运行在主协程中
                tv.text = "xsy"
                Toast.makeText(this@SelfViewGroupActivity, "年后", Toast.LENGTH_SHORT).show()
            }
        }
        btnAddPic.setOnClickListener {
            testWaterFall()
        }
    }

    private fun testWaterFall() {
        val random = Random.nextInt()
        val layoutParams = WaterfallLayout.WaterfallLayoutParams(
            WaterfallLayout.WaterfallLayoutParams.WRAP_CONTENT,
            WaterfallLayout.WaterfallLayoutParams.WRAP_CONTENT
        )
        val num = Math.abs(random)
        val imageView = ImageView(this)
        when (num % IMAGE_COUNT) {
            0 -> imageView.setImageResource(R.mipmap.pic_1)
            1 -> imageView.setImageResource(R.mipmap.pic_2)
            2 -> imageView.setImageResource(R.mipmap.pic_3)
            3 -> imageView.setImageResource(R.mipmap.pic_4)
            4 -> imageView.setImageResource(R.mipmap.pic_5)
        }
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        waterFall.addView(imageView, layoutParams)
        waterFall.setOnItemClickListener { view, index ->
            Toast.makeText(this, "item= " + index, Toast.LENGTH_SHORT).show()
        }
    }
}
