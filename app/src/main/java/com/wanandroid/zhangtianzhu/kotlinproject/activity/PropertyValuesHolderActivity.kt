package com.wanandroid.zhangtianzhu.kotlinproject.activity

import android.animation.Keyframe
import android.animation.PropertyValuesHolder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wanandroid.zhangtianzhu.kotlinproject.R
import kotlinx.android.synthetic.main.activity_property_values_holder.*
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.animation.AccelerateInterpolator
import com.wanandroid.zhangtianzhu.kotlinproject.utils.CharEvaluator


class PropertyValuesHolderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_values_holder)
        startPropertyAnim.setOnClickListener {
            //            propertyIntTest()
//            changeTextViewContent()
            frameChangeText()
        }
    }

    private fun propertyIntTest() {
        //PropertyValuesHolder保存动画过程中操作的值以及属性值，通过ofFloat来构造动画
        val rotationHolder = PropertyValuesHolder.ofFloat("rotation", 60f, -60f, 40f, -40f, 20f, -20f, 10f, -10f, 0f)
        val colorHolder = PropertyValuesHolder.ofInt("BackgroundColor", -0x1, -0xff01, -0x100, -0x1)
        val animator = ObjectAnimator.ofPropertyValuesHolder(tvPropertyAnim, rotationHolder, colorHolder)
        animator.duration = 2000
        animator.interpolator = AccelerateInterpolator()
        animator.start()
    }

    @SuppressLint("UseValueOf")
    private fun changeTextViewContent() {
        val contentHolder = PropertyValuesHolder.ofObject("CharText", CharEvaluator(), Character('A'), Character('Z'))
        val animator = ObjectAnimator.ofPropertyValuesHolder(tvPropertyAnim, contentHolder)
        animator.duration = 3000
        animator.interpolator = AccelerateInterpolator()
        animator.start()
    }

    private fun frameChangeText() {
        //去掉第一个帧或者最后一个帧会以第一个关键帧与最后一个关键帧，至少需要两个或者以上的帧，否则报错
        val frame0 = Keyframe.ofFloat(0f, 0f)
        val frame1 = Keyframe.ofFloat(0.2f, -20f)
        val frame2 = Keyframe.ofFloat(1f, 0f)
        val holder = PropertyValuesHolder.ofKeyframe("rotation", frame0, frame1, frame2)
        val animator = ObjectAnimator.ofPropertyValuesHolder(tvPropertyAnim, holder)
        animator.duration = 2000
        animator.interpolator = AccelerateInterpolator()
        animator.start()
    }
}
