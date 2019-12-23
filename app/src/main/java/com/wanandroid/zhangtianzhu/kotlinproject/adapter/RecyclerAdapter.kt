package com.wanandroid.zhangtianzhu.kotlinproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.wanandroid.zhangtianzhu.kotlinproject.R

import java.util.ArrayList

class RecyclerAdapter(private val mContext: Context, private val mDatas: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val M_SECTION_ITEM_NUM = 10

    enum class ITEM_TYPE {
        ITEM_TYPE_SECTION,
        ITEM_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(mContext)
        return if (viewType == ITEM_TYPE.ITEM_TYPE_ITEM.ordinal) {
            NormalHolder(inflater.inflate(R.layout.item_layout, parent, false))
        } else SectionHolder(inflater.inflate(R.layout.item_selection_layout, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SectionHolder) {
            holder.mSectionTv.text = "第 " + position / M_SECTION_ITEM_NUM + " 组"
        } else if (holder is NormalHolder) {
            holder.mTV.text = mDatas[position]
        }
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % M_SECTION_ITEM_NUM == 0) {
            ITEM_TYPE.ITEM_TYPE_SECTION.ordinal
        } else ITEM_TYPE.ITEM_TYPE_ITEM.ordinal
    }

    inner class NormalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mTV: TextView = itemView.findViewById<View>(R.id.item_tv) as TextView

        init {

            mTV.setOnClickListener { Toast.makeText(mContext, mTV.text, Toast.LENGTH_SHORT).show() }

        }
    }

    inner class SectionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mSectionTv: TextView = itemView.findViewById<View>(R.id.item_section_tv) as TextView

    }
}
