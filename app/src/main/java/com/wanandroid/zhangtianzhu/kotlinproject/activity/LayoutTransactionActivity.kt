package com.wanandroid.zhangtianzhu.kotlinproject.activity

import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.wanandroid.zhangtianzhu.kotlinproject.R
import kotlinx.android.synthetic.main.activity_layout_transaction.*
import android.animation.Keyframe
import android.view.View

class LayoutTransactionActivity : AppCompatActivity() {
    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_transaction)
        initTranslation()
    }

    private fun initTranslation() {
        val translation = LayoutTransition()
        //入场动画，视图在布局中出现时候触发动画
        val inAnim = ObjectAnimator.ofFloat(null, "rotationY", 0f, 360f, 0f)
        translation.setAnimator(LayoutTransition.APPEARING, inAnim)

        val outAnim = ObjectAnimator.ofFloat(null, "rotation", 0f, 90f, 0f)
        translation.setAnimator(LayoutTransition.DISAPPEARING, outAnim)

//        在添加控件时，除了被添加控件本身的入场动画以外，其它需要移动位置的控件，在移动位置时，也被添加上了动画（left点位移动画），
//        这些除了被添加控件以外的其它需要移动位置的控件组合，所对应的动画就是LayoutTransition.CHANGE_APPEARING
//        同样，在移除一个控件时，因为移除了一个控件，而其它所有需要改变位置的控件组合所对应的动画就是LayoutTransition.CHANGE_DISAPPEARING

        //必须使用PropertyValuesHolder 构造动画才能有效，left与top属性必须写，属性值第一个与最后一个必须相同，不然动画将无效
        val pvLeft = PropertyValuesHolder.ofInt("left", 0, 200, 0)
        val pvTop = PropertyValuesHolder.ofInt("top", 10, 10)
        val pvScale = PropertyValuesHolder.ofFloat("ScaleX", 1f, 9f, 1f)
        val animation = ObjectAnimator.ofPropertyValuesHolder(llTranslation, pvLeft, pvTop, pvScale)
        translation.setAnimator(LayoutTransition.CHANGE_APPEARING, animation)

        val outLeft = PropertyValuesHolder.ofInt("left", 0, 0)
        val outTop = PropertyValuesHolder.ofInt("top", 0, 0)

        val frame0 = Keyframe.ofFloat(0f, 0f)
        val frame1 = Keyframe.ofFloat(0.1f, -20f)
        val frame2 = Keyframe.ofFloat(0.2f, 20f)
        val frame3 = Keyframe.ofFloat(0.3f, -20f)
        val frame4 = Keyframe.ofFloat(0.4f, 20f)
        val frame5 = Keyframe.ofFloat(0.5f, -20f)
        val frame6 = Keyframe.ofFloat(0.6f, 20f)
        val frame7 = Keyframe.ofFloat(0.7f, -20f)
        val frame8 = Keyframe.ofFloat(0.8f, 20f)
        val frame9 = Keyframe.ofFloat(0.9f, -20f)
        val frame10 = Keyframe.ofFloat(1f, 0f)
        val mPropertyValuesHolder = PropertyValuesHolder.ofKeyframe(
            "rotation",
            frame0,
            frame1,
            frame2,
            frame3,
            frame4,
            frame5,
            frame6,
            frame7,
            frame8,
            frame9,
            frame10
        )
        val changeAnimation =
            ObjectAnimator.ofPropertyValuesHolder(llTranslation, outLeft, outTop, mPropertyValuesHolder)
        translation.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changeAnimation)
        llTranslation.layoutTransition = translation

//        各transitionType的取值对应为：APPEARING = 2、CHANGE_APPEARING = 0、DISAPPEARING = 3、CHANGE_DISAPPEARING = 1
//        所以在添加控件时，先是start回调，再是end回调；APPEARING事件所对应的View是控件，而CHANGE_APPEARING所对应的控件是容器。删除控件时，原理相同。
//        这是因为，在添加控件时，APPEARING事件只针对当前被添加的控件做动画，所以返回的View是当前被添加的控件。
//        而CHANGE_APPEARING是对容器中所有已存在的控件做动画，所以返回的View是容器
        translation.addTransitionListener(object : LayoutTransition.TransitionListener {
            override fun startTransition(
                p0: LayoutTransition?,
                container: ViewGroup?,
                view: View,
                transitionType: Int
            ) {
            }

            override fun endTransition(p0: LayoutTransition?, p1: ViewGroup?, p2: View?, p3: Int) {
            }
        })


        addWidget.setOnClickListener {
            addWidget()
        }

        removeWidget.setOnClickListener {
            removeWidget()
        }
    }

    private fun addWidget() {
        i++
        val button = Button(this)
        button.text = "button$i"
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        button.layoutParams = params
        llTranslation.addView(button)
    }

    private fun removeWidget() {
        if (i > 0) {
            llTranslation.removeViewAt(0)
        }
        i--
    }
}
