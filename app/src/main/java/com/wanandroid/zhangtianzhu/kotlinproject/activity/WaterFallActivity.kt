package com.wanandroid.zhangtianzhu.kotlinproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.wanandroid.zhangtianzhu.kotlinproject.R
import com.wanandroid.zhangtianzhu.kotlinproject.view.WaterfallLayout
import kotlinx.android.synthetic.main.activity_water_fall.*
import kotlin.random.Random

class WaterFallActivity : AppCompatActivity() {

    private val IMAGE_COUNT = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_fall)
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
            0 -> imageView.setImageResource(R.drawable.iv_eye_3)
            1 -> imageView.setImageResource(R.drawable.iv_eye_4)
            2 -> imageView.setImageResource(R.drawable.iv_eye_5)
            3 -> imageView.setImageResource(R.drawable.iv_eye_6)
            4 -> imageView.setImageResource(R.mipmap.pic_4)
        }
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        waterFall.addView(imageView, layoutParams)
        waterFall.setOnItemClickListener { view, index ->
            Toast.makeText(this, "item= " + index, Toast.LENGTH_SHORT).show()
        }
    }
}
