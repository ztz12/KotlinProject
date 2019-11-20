package com.wanandroid.zhangtianzhu.kotlinproject.adapter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wanandroid.zhangtianzhu.kotlinproject.R

class MyRefreshAdapter constructor(
    private val context: Context,
    private var datas: MutableList<String>,
    private var hasMore: Boolean = true
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //正常item
    private var normType = 0
    //底部item
    private var footerType = 1
    //是否隐藏底部提示，默认不隐藏提示
    private var fadeTips = false
    private val mHandler: Handler = Handler(Looper.getMainLooper())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> NormalHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false))
            else -> FootHolder(LayoutInflater.from(context).inflate(R.layout.footview, parent, false))
        }
    }

    //由于存在底部item，所有整个item加一
    override fun getItemCount(): Int = datas.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NormalHolder) {
            holder.textView.text = datas[position]
        } else {
            (holder as FootHolder).tips.visibility = View.VISIBLE
            //如果有更多数据就显示正在加载更多，否则显示没有更多数据
            if (hasMore) {
                fadeTips = false
                if (datas.size > 0) {
                    holder.tips.text = "正在加载更多..."
                }
            } else {
                if (datas.size > 0) {
                    holder.tips.text = "没有更多数据了"
                    //模拟网络请求耗时，在500ms内执行
                    mHandler.postDelayed({
                        holder.tips.visibility = View.GONE
                        fadeTips = true
                        hasMore = true
                    }, 500)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            itemCount - 1 -> footerType
            else -> normType
        }
    }

    /**
     *  获取最后一个数据源的位置，不计入footerView
     */
    fun getLastRealLastPosition(): Int = datas.size

    /**
     * 改变fadeTips方法
     */
    fun isFadeTips(): Boolean {
        return fadeTips
    }

    /**
     * 将数据源置空
     */
    fun resetData() {
        datas.clear()
    }

    /**
     * 添加新的数据集,并修改hasMore状态，有新的数据设置为true，否则为false
     */
    fun updateData(newDatas: MutableList<String>?, hasMore: Boolean) {
        if (newDatas != null) {
            datas.addAll(newDatas)
        }
        this.hasMore = hasMore
        notifyDataSetChanged()
    }

    internal inner class NormalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.tv) as TextView

    }

    internal inner class FootHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tips: TextView = itemView.findViewById(R.id.tips) as TextView

    }
}