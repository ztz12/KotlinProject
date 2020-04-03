package com.wanandroid.zhangtianzhu.kotlinproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import com.wanandroid.zhangtianzhu.kotlinproject.R
import kotlinx.android.synthetic.main.activity_client.*
import org.jetbrains.anko.startActivity

class ClientActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)
//        Debug.startMethodTracing("traceClient")
        startDifferentActivity()
    }

    private fun startDifferentActivity() {

        //valueAnimator 界面
        valueAnimatorTest.setOnClickListener {
            startActivity<ValueAnimatorActivity>()
        }

        //objectAnimator 界面
        objectAnimatorTest.setOnClickListener {
            startActivity<ObjectAnimatorActivity>()
        }

        //gridAnimator 界面
        gridAnimatorTest.setOnClickListener {
            startActivity<GridAnimActivity>()
        }

        //layoutAnimator 界面
        layoutAnimatorTest.setOnClickListener {
            startActivity<LayoutAnimActivity>()
        }

        //layoutTransactionAnimator 界面
        layoutTransactionAnimatorTest.setOnClickListener {
            startActivity<LayoutTransactionActivity>()
        }

        //listView 进入动画界面
        listViewAnimatorTest.setOnClickListener {
            startActivity<ListViewAnimActivity>()
        }

        //心跳界面
        heartTest.setOnClickListener {
            startActivity<XfermodeActivity>()
        }

        //阴影界面
        shapeTest.setOnClickListener {
            startActivity<ShaderActivity>()
        }

        //Bitmap 添加阴影界面
        bitmapShaderTest.setOnClickListener {
            startActivity<ExtractAlphaActivity>()
        }

        //望远镜界面
        telescopeTest.setOnClickListener {
            startActivity<BitmapShaderActivity>()
        }

        //不规则头像界面
        avatarTest.setOnClickListener {
            startActivity<BitmapAvatarActivity>()
        }

        //闪动文字界面
        gradientTest.setOnClickListener {
            startActivity<LinearGradientActivity>()
        }

        //水波纹效果界面
        rippleTest.setOnClickListener {
            startActivity<RadialGradientActivity>()
        }

        //贝塞尔曲线双波浪效果界面
        waveTest.setOnClickListener {
            startActivity<CircleWaveProgressActivity>()
        }

        //QQ红点拖动删除界面
        redPointTest.setOnClickListener {
            startActivity<CanvasActivity>()
        }

        //流式布局
        flowTest.setOnClickListener {
            startActivity<SelfViewGroupActivity>()
        }

        //瀑布流容器实现
        waterfallTest.setOnClickListener {
            startActivity<WaterFallActivity>()
        }

        //RecyclerView 画廊实现
        galleryTest.setOnClickListener {
            startActivity<GalleryActivity>()
        }
    }

    override fun onStop() {
        super.onStop()
    }
}
