package com.wanandroid.zhangtianzhu.kotlinproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wanandroid.zhangtianzhu.kotlinproject.R
import kotlinx.android.synthetic.main.activity_wave.*

class WaveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wave)
        waveView.startValueAnimator()
        btn_reset.setOnClickListener {
//            waveView.reset()
        }
    }
}
