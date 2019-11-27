package com.wanandroid.zhangtianzhu.kotlinproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.wanandroid.zhangtianzhu.kotlinproject.R
import kotlinx.android.synthetic.main.activity_test_view_group.*
import java.util.*

class TestViewGroupActivity : AppCompatActivity() {

    private val mRandom = Random()

    private val INFO = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_view_group)
        createData()
    }

    private fun createData() {
        simpleLayout.removeAllViews()
        for (i in 0 until 10) {
            createSingleView()
        }

        simpleLayout.invalidate()
    }

    private fun createSingleView() {
        INFO.add("Hello World!")
        INFO.add("猛猛的小盆友")
        INFO.add("掘")
        INFO.add("金金金")
        INFO.add("PHP是最好的语言")
        INFO.add("大Android")
        INFO.add("IOS")
        INFO.add("JAVA")
        INFO.add("Python")
        INFO.add("这是一个很长很长很长很长很长很长很长很长超级无敌长的句子")

        val index = mRandom.nextInt(INFO.size)
        val itemContent = INFO[index]

        val item = layoutInflater
            .inflate(R.layout.item_single_tag, simpleLayout, false) as TextView
        item.text = itemContent
        item.setOnClickListener {
            Toast.makeText(
                this@TestViewGroupActivity, "text:$itemContent",
                Toast.LENGTH_SHORT
            ).show()
        }

        simpleLayout.addView(item)
    }
}
