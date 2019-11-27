package com.wanandroid.zhangtianzhu.kotlinproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wanandroid.zhangtianzhu.kotlinproject.R
import com.wanandroid.zhangtianzhu.kotlinproject.view.CircleWaveProgressView
import kotlinx.android.synthetic.main.activity_circle_wave_progress.*
import java.text.DecimalFormat

class CircleWaveProgressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle_wave_progress)
        btn_reset_circle.setOnClickListener {
            waveProgress.reset()
        }
        waveProgress.setTextView(tv_circle)
        waveProgress.setUpdateTextListener(object : CircleWaveProgressView.UpdateTextListener {
            override fun updateText(interpolatedTime: Float, currentProgress: Float, maxProgress: Float): String {
                //取一位整数，并且保留两位小数
                val decimalFormat = DecimalFormat("0.00")
                return decimalFormat.format(interpolatedTime * currentProgress / maxProgress * 100) + "%"
            }
        })
        waveProgress.setCanvasSecondWave(true)
        waveProgress.setProgress(50f, 2500)
    }
}
