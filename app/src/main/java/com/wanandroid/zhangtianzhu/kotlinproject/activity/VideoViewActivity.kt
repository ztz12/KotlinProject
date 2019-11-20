package com.wanandroid.zhangtianzhu.kotlinproject.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import com.wanandroid.zhangtianzhu.kotlinproject.R
import kotlinx.android.synthetic.main.activity_video_view.*

/**
 * @author yif
 * @Tips 使用VideoView来进行播放视频，支持的格式单一，只支持mp4,avi,3gpg的格式
 * 使用的是MediaPlayer对视频文件进行控制，可以播放网络视频
 */
class VideoViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("onCreate","VideoViewActivity onCreate执行")
        setContentView(R.layout.activity_video_view)
        initVideoView()
    }

    private fun initVideoView(){
        videoView.setVideoURI(Uri.parse("http://static.waiyutong.org/book/micro_lecture/mp4/4bd325cc46b2aa256f5a2a67539a7c3f.mp4"))
        videoView.start()
        val mediaController = MediaController(this@VideoViewActivity)
        videoView.setMediaController(mediaController)
        mediaController.setMediaPlayer(videoView)
    }

    override fun onStart() {
        super.onStart()
        Log.d("onStart","VideoViewActivity onStart执行")
    }

    override fun onResume() {
        super.onResume()
        Log.d("onResume","VideoViewActivity onResume执行")
    }

    override fun onPause() {
        super.onPause()
        Log.d("onPause","VideoViewActivity onPause执行")
    }

    override fun onStop() {
        super.onStop()
        Log.d("onStop","VideoViewActivity onStop执行")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("onRestart","VideoViewActivity onRestart执行")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroy","VideoViewActivity onDestroy执行")
    }
}
