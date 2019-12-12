package com.wanandroid.zhangtianzhu.kotlinproject.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.wanandroid.zhangtianzhu.kotlinproject.R
import kotlinx.android.synthetic.main.activity_animator_xmlmenu.*


class AnimatorXMLMenuActivity : AppCompatActivity() {
    private var mIsMenuOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animator_xmlmenu)
        setOnClick()
    }

    private fun setOnClick() {
        menu.setOnClickListener {
            if (mIsMenuOpen) {
                mIsMenuOpen = false
                doAnimateClose(item1, 0, 5, 300)
                doAnimateClose(item2, 1, 5, 300)
                doAnimateClose(item3, 2, 5, 300)
                doAnimateClose(item4, 3, 5, 300)
                doAnimateClose(item5, 4, 5, 300)
            } else {
                mIsMenuOpen = true
                doOpenMenu(item1, 0, 5, 300)
                doOpenMenu(item2, 1, 5, 300)
                doOpenMenu(item3, 2, 5, 300)
                doOpenMenu(item4, 3, 5, 300)
                doOpenMenu(item5, 4, 5, 300)
            }
        }
        item1.setOnClickListener {
            Toast.makeText(this, "item1 click", Toast.LENGTH_SHORT).show()
        }
        item2.setOnClickListener {
            Toast.makeText(this, "item2 click", Toast.LENGTH_SHORT).show()
        }
        item3.setOnClickListener {
            Toast.makeText(this, "item3 click", Toast.LENGTH_SHORT).show()
        }
        item4.setOnClickListener {
            Toast.makeText(this, "item4 click", Toast.LENGTH_SHORT).show()
        }
        item5.setOnClickListener {
            Toast.makeText(this, "item5 click", Toast.LENGTH_SHORT).show()
        }
    }

    private fun doOpenMenu(view: View, index: Int, total: Int, radius: Int) {
        if (view.visibility != View.VISIBLE) {
            view.visibility = View.VISIBLE
        }

        //拿到每个menu对应的弧度值
        val degree = Math.toRadians(90.0) / (total - 1) * index
        val translationX = -(Math.sin(degree) * radius)
        val translationY = -(Math.cos(degree) * radius)

        val set = AnimatorSet()
        //包含平移、缩放和透明度动画
        set.playTogether(
            ObjectAnimator.ofFloat(view, "translationX", 0f, translationX.toFloat()),
            ObjectAnimator.ofFloat(view, "translationY", 0f, translationY.toFloat()),
            ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f),
            ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f),
            ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        )
        //动画周期为500ms
        set.duration = 500
        set.start()
    }

    private fun doAnimateClose(
        view: View, index: Int, total: Int,
        radius: Int
    ) {
        if (view.visibility != View.VISIBLE) {
            view.visibility = View.VISIBLE
        }
        //与上面公式一样 Math.PI不仅表示圆周率，也表示180度所对应的弧度。
        //所以Math.toRadians(90)就等于Math.PI/2，这样，这两个公式就是完全一样的了
        val degree = Math.PI * index / ((total - 1) * 2)
        //sin 输入的是弧度值，所有需要将角度转换成弧度值
        val translationX = -(radius * Math.sin(degree)).toInt()
        val translationY = -(radius * Math.cos(degree)).toInt()
        val set = AnimatorSet()
        //包含平移、缩放和透明度动画
        set.playTogether(
            ObjectAnimator.ofFloat(view, "translationX", translationX.toFloat(), 0f),
            ObjectAnimator.ofFloat(view, "translationY", translationY.toFloat(), 0f),
            ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.1f),
            ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.1f),
            ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
        )

        set.setDuration((1 * 500).toLong()).start()
    }
}
