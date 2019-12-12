package com.wanandroid.zhangtianzhu.kotlinproject.activity

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.animation.addListener
import com.wanandroid.zhangtianzhu.kotlinproject.R
import kotlinx.android.synthetic.main.activity_animator_xml.*

class AnimatorXMLActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animator_xml)
        startXMLAnim.setOnClickListener {
            //            testAnim1()
//            testAnim2()
//            testAnim3()
            testAnim4()
        }
    }

    private fun testAnim1() {
        val valueAnimator = AnimatorInflater.loadAnimator(this, R.animator.animator) as ValueAnimator
        valueAnimator.addUpdateListener { valueAnimator ->
            val curValue = valueAnimator.animatedValue as Int
            tvXMLAnim.layout(curValue, curValue, tvXMLAnim.width + curValue, tvXMLAnim.height + curValue)
        }
        valueAnimator.start()
    }

    private fun testAnim2() {
        val animator = AnimatorInflater.loadAnimator(this, R.animator.objectanimator) as ValueAnimator
        //设置目标控件
        animator.setTarget(tvXMLAnim)
        animator.start()
    }

    private fun testAnim3() {
        val animator = AnimatorInflater.loadAnimator(this, R.animator.objectanimatorcolor) as ValueAnimator
        animator.setTarget(tvXMLAnim)
        animator.start()
    }

    private fun testAnim4() {
        //同时改变x与y轴坐标
        val animatorSet = AnimatorInflater.loadAnimator(this, R.animator.objectanimset) as AnimatorSet
        animatorSet.setTarget(tvXMLAnim)
        animatorSet.start()
    }
}
