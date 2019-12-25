package com.wanandroid.zhangtianzhu.kotlinproject.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.wanandroid.zhangtianzhu.kotlinproject.R

import java.util.ArrayList

class GalleryAdapter(private val mContext: Context, private val mDatas: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mCreatedHolder = 0
    private val mPics =
        intArrayOf(R.mipmap.item1, R.mipmap.item2, R.mipmap.item3, R.mipmap.item4, R.mipmap.item5, R.mipmap.item6)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mCreatedHolder++
        Log.d("onCreateViewHolder", "onCreateViewHolder num:$mCreatedHolder")
        val inflater = LayoutInflater.from(mContext)
        return NormalHolder(inflater.inflate(R.layout.item_coverflow, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val normalHolder = holder as NormalHolder
        normalHolder.mTV.text = mDatas[position]
        normalHolder.mImg.setImageDrawable(mContext.resources.getDrawable(mPics[position % mPics.size]))
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    inner class NormalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mTV: TextView = itemView.findViewById<View>(R.id.text) as TextView
        var mImg: ImageView

        init {

            mTV.setOnClickListener { Toast.makeText(mContext, mTV.text, Toast.LENGTH_SHORT).show() }

            mImg = itemView.findViewById<View>(R.id.img) as ImageView
            mImg.setOnClickListener { Toast.makeText(mContext, mTV.text, Toast.LENGTH_SHORT).show() }

        }
    }
}
