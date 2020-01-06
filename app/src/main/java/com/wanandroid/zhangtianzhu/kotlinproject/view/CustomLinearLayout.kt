package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.LinearLayout

class CustomLinearLayout(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {
    private val TAG = "onTouchEventTest"

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(TAG, " CustomLinearLayout ACTION_DOWN 触发")
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                Log.d(TAG, "CustomLinearLayout ACTION_MOVE 触发")
            }

            MotionEvent.ACTION_UP -> {
                Log.d(TAG, "CustomLinearLayout ACTION_UP 触发")
            }
            //多指触摸操作会调用，其他手指进行点击操作
            MotionEvent.ACTION_POINTER_DOWN -> {
                Log.d(TAG, "CustomLinearLayout ACTION_POINTER_DOWN 触发")
            }

            //多指触摸操作调用，其他手指进行抬起触发
            MotionEvent.ACTION_POINTER_UP -> {
                Log.d(TAG, "CustomLinearLayout ACTION_POINTER_UP 触发")
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
    }
}