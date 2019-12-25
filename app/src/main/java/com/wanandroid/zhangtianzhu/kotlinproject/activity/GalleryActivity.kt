package com.wanandroid.zhangtianzhu.kotlinproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wanandroid.zhangtianzhu.kotlinproject.R
import com.wanandroid.zhangtianzhu.kotlinproject.adapter.GalleryAdapter
import com.wanandroid.zhangtianzhu.kotlinproject.utils.GalleryLayoutManager
import kotlinx.android.synthetic.main.activity_gallery.*
import java.util.ArrayList

class GalleryActivity : AppCompatActivity() {
    private val arrayList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        generateData()
        val galleryAdapter = GalleryAdapter(this,arrayList)
        rlGallery.run {
            adapter = galleryAdapter
            layoutManager = GalleryLayoutManager()
        }
    }

    private fun generateData(){
        for(i in 0 until 50){
            arrayList.add("第$i 个 item")
        }
    }
}
