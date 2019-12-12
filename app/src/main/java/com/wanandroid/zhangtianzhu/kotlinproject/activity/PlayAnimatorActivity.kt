package com.wanandroid.zhangtianzhu.kotlinproject.activity

import android.animation.AnimatorSet
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wanandroid.zhangtianzhu.kotlinproject.R
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import kotlinx.android.synthetic.main.activity_play_animator.*


class PlayAnimatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_animator)
        startPlay.setOnClickListener {
            doPlaySequentially()
        }
    }

    private fun doPlaySequentially() {
        val tv1BgAnimator = ObjectAnimator.ofInt(tvOne, "BackgroundColor", -0xff01, -0x100, -0xff01)
        tv1BgAnimator.duration = 500000000
        val tv1TranslateY = ObjectAnimator.ofFloat(tvOne, "translationY", 0f, 400f, 0f)
        //设置开始延迟2秒
        tv1TranslateY.startDelay = 2000
//        tv1TranslateY.repeatCount = ValueAnimator.INFINITE
        val tv2TranslateY = ObjectAnimator.ofFloat(tvTwo, "translationY", 0f, 400f, 0f)
        tv2TranslateY.startDelay = 2000
        val set = AnimatorSet()
        //依次播放，这里设置了文本1无线循环播放，而文本2需要等到文本1播放完之后才会执行，这就导致文本2永远不会执行
//        set.playSequentially(tv1BgAnimator,tv1TranslateY,tv2TranslateY)
        //一起播放
//        set.playTogether(tv1BgAnimator,tv1TranslateY,tv2TranslateY)
        //使用builder对象逐个添加动画
//        val builder = set.play(tv1TranslateY)
//        builder.with(tv2TranslateY)
//        //在播放背景改变之后再播放位移动画
//        builder.after(tv1BgAnimator)
        //串行执行
//        set.play(tv1TranslateY).with(tv2TranslateY).after(tv1BgAnimator)
        //由于AnimatorSet设置了时长，单个动画时长无效，加速器也一样
        set.duration = 2000
        set.startDelay = 2000
        //给目标控件设置动画，之前设置的控件动画将无效，这里设置文本2，那么文本1所有动画都无效，所有动画都发生在文本2上
//        set.setTarget(tvTwo)
//        当AnimatorSet所拥有的函数与单个动画所拥有的函数冲突时，就以AnimatorSet设置为准。但唯一的例外就是setStartDelay。
//        setStartDelay函数不会覆盖单个动画的延时，而且仅针对性的延长AnimatorSet的激活时间，单个动画的所设置的setStartDelay仍对单个动画起作用。

        //AnimatorSet.startDelay+第一个动画.startDelay也就是 2000+2000为4秒，又是串行执行，文本1又要延时2秒
        set.play(tv2TranslateY).with(tv1TranslateY)
//        AnimatorSet的延时是仅针对性的延长AnimatorSet激活时间的，对单个动画的延时设置没有影响。
//        AnimatorSet真正激活延时 = AnimatorSet.startDelay+第一个动画.startDelay
//        在AnimatorSet激活之后，第一个动画绝对是会开始运行的，后面的动画则根据自己是否延时自行处理。
        set.start()
    }
}
