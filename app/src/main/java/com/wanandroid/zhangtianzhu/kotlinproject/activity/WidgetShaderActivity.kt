package com.wanandroid.zhangtianzhu.kotlinproject.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wanandroid.zhangtianzhu.kotlinproject.R
import kotlinx.android.synthetic.main.activity_widget_shader.*

class WidgetShaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widget_shader)
        et_shader.setShadowLayer(5f,10f,10f,Color.GREEN)
    }
}
