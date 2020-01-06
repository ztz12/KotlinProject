package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton

class CustomButtonOne(context: Context, attributeSet: AttributeSet):AppCompatButton(context,attributeSet) {
    private val TAG = "onTouchEventTest"
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.actionMasked){
            MotionEvent.ACTION_DOWN ->{
                Log.d(TAG," CustomButtonOne ACTION_DOWN 触发")
            }

            MotionEvent.ACTION_MOVE ->{
                Log.d(TAG,"CustomButtonOne ACTION_MOVE 触发")
            }

            MotionEvent.ACTION_UP ->{
                Log.d(TAG,"CustomButtonOne ACTION_UP 触发")
            }
            //多指触摸操作会调用，其他手指进行点击操作
            MotionEvent.ACTION_POINTER_DOWN->{
                Log.d(TAG,"CustomButtonOne ACTION_POINTER_DOWN 触发")
            }

            //多指触摸操作调用，其他手指进行抬起触发
            MotionEvent.ACTION_POINTER_UP->{
                Log.d(TAG,"CustomButtonOne ACTION_POINTER_UP 触发")
            }
        }
        return super.onTouchEvent(event)
    }
}