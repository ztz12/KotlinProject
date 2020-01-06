package com.wanandroid.zhangtianzhu.kotlinproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wanandroid.zhangtianzhu.kotlinproject.R
import androidx.appcompat.widget.AppCompatButton
import android.R.attr.y
import android.graphics.PixelFormat
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import kotlinx.android.synthetic.main.activity_test_wms.*


class TestWmsActivity : AppCompatActivity() {
    private val TAG = "TestWmsActivity"
    private lateinit var view: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_wms)
        view = MyButton(this)
        windowManager.addView(view, getWindowParams())
        btnRemove.setOnClickListener {
            try {
                view.visibility = View.GONE
                windowManager.removeView(view)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getWindowParams(): WindowManager.LayoutParams {
        val params = WindowManager.LayoutParams()
        params.format = PixelFormat.TRANSLUCENT// 支持透明
//        params.flags = params.flags or  WindowManager.LayoutParams.FLAG_SPLIT_TOUCH// 接受窗口外事件
        params.flags = params.flags or  WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        params.width = 490//窗口的宽和高
        params.height = 160
        params.x = 500//窗口位置的偏移量
        params.y = 0
        params.title = "MyDialog"
        return params
    }

    fun dismiss() {
        windowManager.removeView(view)
    }

    internal inner class MyButton(private val activity: TestWmsActivity) : AppCompatButton(activity) {

        override fun onTouchEvent(event: MotionEvent): Boolean {
            Log.d(TAG, "onTouchEvent: " + event.action)
            activity.dismiss()
            return true

        }

        override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
            Log.d(TAG, "onKeyDown: $keyCode")
            activity.dismiss()
            return true
        }
    }
}
