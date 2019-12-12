package com.wanandroid.zhangtianzhu.kotlinproject.utils

import android.animation.TypeEvaluator
import com.wanandroid.zhangtianzhu.kotlinproject.bean.Point

class PointEvaluator : TypeEvaluator<Point> {
    override fun evaluate(fraction: Float, startValue: Point, endValue: Point): Point {
        return Point(startValue.getRadius() + fraction * (endValue.getRadius() - startValue.getRadius()))
    }
}