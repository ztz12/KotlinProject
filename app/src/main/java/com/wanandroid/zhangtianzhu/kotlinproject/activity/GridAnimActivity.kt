package com.wanandroid.zhangtianzhu.kotlinproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.wanandroid.zhangtianzhu.kotlinproject.R
import kotlinx.android.synthetic.main.activity_grid_anim.*
import android.widget.TextView
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.GridLayoutAnimationController


class GridAnimActivity : AppCompatActivity() {
    private val mGrideAdapter: GridAdapter = GridAdapter()
    private val mDatas = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid_anim)
        testGridAnim()
    }

    private fun testGridAnim() {
//        startGridAnim.setOnClickListener {
//            addData()
//        }
        mDatas.addAll(getData())
        grid.adapter = mGrideAdapter

        //代码中动态添加gridLayoutAnimation
        val animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_left)
        val controller = GridLayoutAnimationController(animation)
        controller.rowDelay = 0.7f
        controller.columnDelay = 0.6f
        controller.directionPriority = GridLayoutAnimationController.PRIORITY_NONE
        controller.direction =
            GridLayoutAnimationController.DIRECTION_BOTTOM_TO_TOP or GridLayoutAnimationController.DIRECTION_LEFT_TO_RIGHT
        grid.layoutAnimation = controller
        grid.startLayoutAnimation()
    }

    private fun getData(): List<String> {

        val data = ArrayList<String>()
        for (i in 1..34) {
            data.add("DATA $i")
        }
        return data
    }


    private fun addData() {
        mDatas.addAll(mDatas)
        mGrideAdapter.notifyDataSetChanged()
    }


    inner class GridAdapter : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view: View
            val holder: ViewHolder
            if (convertView == null) {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
                holder = ViewHolder(view)
                view.tag = holder
            } else {
                view = convertView
                holder = view.tag as ViewHolder
            }
            holder.tv.text = mDatas[position]
            return view
        }

        override fun getCount(): Int {
            return mDatas.size
        }

        override fun getItem(position: Int): Any? {
            return mDatas[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        inner class ViewHolder(view: View) {
            val tv: TextView = view.findViewById(R.id.tv)
        }
    }
}
