package com.wanandroid.zhangtianzhu.kotlinproject.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import com.wanandroid.zhangtianzhu.kotlinproject.R

class ListViewAdapter(
    private val context: Context,
    private val lv: ListView,
    private var drawableList: MutableList<Drawable>,
    private var length: Int
) : BaseAdapter() {
    private val animation = AnimationUtils.loadAnimation(context, R.anim.bottom_in_anim)
    private var mFirstTop = 0
    private var mFirstPosition = 0
    private var mCanScroll = false

    init {
        val scrollListener = object : AbsListView.OnScrollListener {
            //firstVisibleItem 表示当前第一个可见item在列表中所有的索引，这里是指的整个列表，firstVisibleItem是指在整个ListView中的位置。而getChildAt(int position)中参数position传的是当前屏幕显示区域中item的索引，屏幕中第一个item的view可以通过getChildAt(0)得到。 
            //第三个参数int visibleItemCount：表示当前屏幕中可见的有几条item 
            //第四个参数int totalItemCount：表示当前listview总共有多少条item，得到的值与adapter.getCount()的值相同
            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleCount: Int, totalcount: Int) {
                val firstChild = view?.getChildAt(0) ?: return
                val top = firstChild.top
                /**
                 * 向上移动包含两种情况：
                 * 屏幕中第一个item或前几个item一起移出屏幕，在这种情况下，我们只需要判断firstVisibleItem是否比上次的值大即可。即第一个显示的item是不是已经向上移了
                 * 可能用户并没有一次性移一整条item，而是仅让当前item向上移了一点点。这里，由于当前可见的第一个item的位置仍然是firstVisibleItem，只是它的top值变了。
                 * firstVisibleItem>mFirstPosition 表示向下滑动一整个item
                 * mFirstTop>top 表示当前item滑动
                 */
                mCanScroll = firstVisibleItem > mFirstPosition || mFirstTop > top
                mFirstPosition = firstVisibleItem
                mFirstTop = top
            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
            }

        }
        lv.setOnScrollListener(scrollListener)
    }

    override fun getView(position: Int, contentView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        val view: View
        if (contentView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_lv, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = contentView
            holder = view.tag as ViewHolder
        }
        //清除当前区域所有显示的item动画
        for (i in 0 until lv.childCount) {
            val v = lv.getChildAt(i)
            v.clearAnimation()
        }
        //然后给当前item添加动画
        if (mCanScroll) {
            view.startAnimation(animation)
        }
        holder.iv.setImageDrawable(drawableList[position % drawableList.size])
        holder.tv.text = position.toString()
        return view
    }


    /**
     * BaseAdapter 本身并没有使用getItem函数，重新该函数是为了让我们获取想要的实例
     * 这里循环获取图片资源
     * 类似于listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    //关键在这里哦
    Drawable drawable = (Drawable) adapter.getItem(position);
    }
    });
     */
    override fun getItem(position: Int): Any = drawableList[position % drawableList.size]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = length

    inner class ViewHolder(view: View) {
        val tv: TextView = view.findViewById(R.id.lv_tv)
        val iv: ImageView = view.findViewById(R.id.lv_iv)
    }
}