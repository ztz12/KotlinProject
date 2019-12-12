package com.wanandroid.zhangtianzhu.kotlinproject.bean

class Point constructor(private var radius:Float){
    fun setRadius(radius: Float){
        this.radius = radius
    }

    fun getRadius():Float{
        return radius
    }
}