package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

class MyTextView constructor(context: Context,attributeSet: AttributeSet):TextView(context,attributeSet){
    fun setCharText(character: Char){
        text = character.toString()
    }
}