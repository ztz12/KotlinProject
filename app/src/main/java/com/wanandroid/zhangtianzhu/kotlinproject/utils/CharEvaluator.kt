package com.wanandroid.zhangtianzhu.kotlinproject.utils

import android.animation.TypeEvaluator

class CharEvaluator :TypeEvaluator<Char>{
    override fun evaluate(fraction: Float, startValue: Char, endValue: Char): Char {
        //val temp = (char)65 得到tmp值就是A
        //val tmp = 'A',val num = tmp as Int 得到值就是65
        val startInt = startValue.toInt()
        val endInt = endValue.toInt()
        return (startInt + fraction * (endInt - startInt)).toChar()
    }

}