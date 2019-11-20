package com.wanandroid.zhangtianzhu.kotlinproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wanandroid.zhangtianzhu.kotlinproject.R
import com.wanandroid.zhangtianzhu.kotlinproject.utils.IRefreshHeader

class RefreshHeaderAdapter(private val mLists: List<String>, private val mContext: Context) :
    RecyclerView.Adapter<RefreshHeaderAdapter.RefreshViewHolder>() {
    private val mInflater: LayoutInflater
    private var mRefreshHeader: IRefreshHeader? = null

    init {
        mInflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RefreshViewHolder {
        return if (viewType == TYPE_REFRESH_HEADER) {
            RefreshViewHolder(mRefreshHeader!!.getHeadView())
        } else RefreshViewHolder(mInflater.inflate(R.layout.normal_item, parent, false))

    }

    override fun onBindViewHolder(holder: RefreshViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return mLists.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_REFRESH_HEADER
        } else TYPE_NORMAL
    }

    class RefreshViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun setRefreshHeader(header: IRefreshHeader?) {
        if (header != null) {
            mRefreshHeader = header
        }
    }

    companion object {
        private val TYPE_REFRESH_HEADER = 100000
        private val TYPE_NORMAL = 10001
    }
}
