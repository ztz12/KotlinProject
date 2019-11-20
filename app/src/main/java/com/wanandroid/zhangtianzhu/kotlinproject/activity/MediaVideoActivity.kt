package com.wanandroid.zhangtianzhu.kotlinproject.activity

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.SurfaceHolder
import android.widget.SeekBar
import com.wanandroid.zhangtianzhu.kotlinproject.R
import kotlinx.android.synthetic.main.activity_media_video.*
import org.jetbrains.anko.startActivity

/**
 * @author yif
 * @Tips MediaPlayer使用Surface进行视频播放
 * 只支持mp4,avi,3pg视频格式
 * 可以播放网络视频
 */
class MediaVideoActivity : AppCompatActivity(), SurfaceHolder.Callback {

    private lateinit var surfaceHolder: SurfaceHolder
    private val mediaPlayer by lazy { MediaPlayer() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("onCreate", "MediaVideoActivity onCreate执行")
        setContentView(R.layout.activity_media_video)
        intMediaVideo()
        btnJump.setOnClickListener {
            startActivity<VideoViewActivity>()
        }
    }

    private fun intMediaVideo() {
        surfaceHolder = surfaceView.holder
        surfaceHolder.addCallback(this)
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        try {
            val surface = holder.surface
            mediaPlayer.setSurface(surface)
            mediaPlayer.setDataSource("http://static.waiyutong.org/book/micro_lecture/mp4/4bd325cc46b2aa256f5a2a67539a7c3f.mp4")
            //进行异步播放
            mediaPlayer.prepareAsync()
            //维护视频播放内容
            mediaPlayer.setDisplay(holder)
            mediaPlayer.setOnPreparedListener { player ->
                player.start()
            }
            val duration = mediaPlayer.duration
            seekBar.max = duration
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    val progress = seekBar.progress
                    mediaPlayer.seekTo(progress)
                }

            })
            Thread {
                setSeekBar()
            }.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setSeekBar() {
        while (seekBar.progress <= seekBar.max) {
            val currentPosition = mediaPlayer.currentPosition
            seekBar.progress = currentPosition
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("onStart", "MediaVideoActivity onStart执行")
    }

    override fun onResume() {
        super.onResume()
        Log.d("onResume", "MediaVideoActivity onResume执行")
    }

    override fun onPause() {
        super.onPause()
        Log.d("onPause", "MediaVideoActivity onPause执行")
    }

    override fun onStop() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
        super.onStop()
        Log.d("onStop", "MediaVideoActivity onStop执行")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("onRestart", "MediaVideoActivity onRestart执行")
    }

    override fun onDestroy() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
        super.onDestroy()
        Log.d("onDestroy", "MediaVideoActivity onDestroy执行")
    }
}
