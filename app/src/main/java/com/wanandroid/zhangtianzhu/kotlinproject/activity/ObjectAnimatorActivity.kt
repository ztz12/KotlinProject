package com.wanandroid.zhangtianzhu.kotlinproject.activity

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wanandroid.zhangtianzhu.kotlinproject.R
import kotlinx.android.synthetic.main.activity_object_animator.*

class ObjectAnimatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_object_animator)
        startObjectAnim.setOnClickListener {
//            val animator = ObjectAnimator.ofFloat(tvObjectAnim,"alpha",1f,0f,1f)
            //ObjectAnimator 做动画，并不是根据xml属性来的，是根据对应属性的set方法来改变
//            1、要使用ObjectAnimator来构造对画，要操作的控件中，必须存在对应的属性的set方法
//            2、setter 方法的命名必须以骆驼拼写法命名，即set后每个单词首字母大写，其余字母小写，即类似于setPropertyName所对应的属性为propertyName
            //rotation 是围绕Z轴进行旋转
//            val animator = ObjectAnimator.ofFloat(tvObjectAnim,"rotationY",0f,180f,0f)
//            val animator = ObjectAnimator.ofFloat(tvObjectAnim,"translationX",0f,200f,-200f,0f)
//            val animator = ObjectAnimator.ofFloat(tvObjectAnim,"scaleY",0f,3f,1f)
            val animator = ObjectAnimator.ofInt(tvObjectAnim, "BackgroundColor", -0xff01, -0x100, -0xff01)
            animator.duration = 2000
            animator.start()
            doObjectAnimator()
        }
    }

    private fun doObjectAnimator(){
        val animator = ObjectAnimator.ofFloat(objectPoint,"pointRadius",100f)
        animator.duration = 2000
        animator.start()
    }
}
