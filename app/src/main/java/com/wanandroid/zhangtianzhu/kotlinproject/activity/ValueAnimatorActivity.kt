package com.wanandroid.zhangtianzhu.kotlinproject.activity

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.widget.Toast
import com.wanandroid.zhangtianzhu.kotlinproject.R
import com.wanandroid.zhangtianzhu.kotlinproject.utils.CharEvaluator
import kotlinx.android.synthetic.main.activity_value_animator.*

class ValueAnimatorActivity : AppCompatActivity() {
    private lateinit var valueAnimator: ValueAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_value_animator)
        startAnim.setOnClickListener {
            valueAnimator = startAnim()
            myPoint.doAnimator()
        }

        cancelAnim.setOnClickListener {
            valueAnimator.cancel()
        }

        tvAnim.setOnClickListener {
            Toast.makeText(this, "tvAnim click", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("UseValueOf")
    private fun startAnim(): ValueAnimator {
        val animator = ValueAnimator.ofObject(CharEvaluator(), Character('A'), Character('Z'))
        animator.duration = 15000
        animator.addUpdateListener { valueAnimator ->
            val curValue = valueAnimator.animatedValue as Char
            tvAnim.text = curValue.toString()
        }
        animator.interpolator = AccelerateInterpolator()
        animator.start()
        return animator
    }
}
