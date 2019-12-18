package com.wanandroid.zhangtianzhu.kotlinproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wanandroid.zhangtianzhu.kotlinproject.R
import kotlinx.android.synthetic.main.activity_shader.*

class ShaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shader)
        setListener()
    }

    private fun setListener(){
        changeRadius.setOnClickListener {
            shaderView.changeRadius()
        }

        changeDy.setOnClickListener {
            shaderView.changeDy()
        }

        changeDx.setOnClickListener {
            shaderView.changeDx()
        }

        clearShader.setOnClickListener {
            shaderView.clearShader()
        }

        showShader.setOnClickListener {
            shaderView.setShader()
        }
    }
}
