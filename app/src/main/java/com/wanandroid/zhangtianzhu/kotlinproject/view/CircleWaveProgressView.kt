package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.Transformation
import android.widget.TextView
import com.wanandroid.zhangtianzhu.kotlinproject.R

/**
 * 贝塞尔曲线绘制双波浪效果
 */
class CircleWaveProgressView : View {

    private val wavePaint = Paint()
    private val wavePath = Path()

    //波浪宽度
    private var waveLength = 0f
    //波浪高度
    private var waveHeight = 0f
    //波浪数量，一个波浪是一低一高
    private var waveNumber = 0
    //自定义view波浪宽高
    private var waveDefaultSize = 250f
    //自定义view最大高度，就是比波浪高一点
    private var maxWaveHeight = 300f
    //waveActualSize 是实际的宽高
    private var waveActualSize = 0f

    //当前进度占比
    var currentPercent = 0f
    private var currentProgress = 0f
    private var maxProgress = 100f
    private val waveAnimation = WaveAnimator()

    //波浪平移距离
    private var moveDistance = 0f

    //绘制圆形背景
    private val circlePaint = Paint()
    private lateinit var circleBitmap: Bitmap
    private lateinit var bitmapCanvas: Canvas

    //波浪颜色
    private var wave_color: Int = 0
    //圆形背景进度框颜色
    private var circle_bgcolor: Int = 0

    //重置恢复之前时间
    private var resetTime = 0L

    //进度显示文本
    private var tv: TextView? = null

    //进度监听
    private var updateTextListener: UpdateTextListener? = null

    //是否绘制双波浪效果
    private var isCanvasSecondWave = false
    private val secondPaint = Paint()
    private var secondWaveColor = 0

    /**
     * 声明 constructor 为类的次构造函数，通过this 调用第二个 constructor 方法
     */
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        //获取attrs文件下配置属性
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleWaveProgressView)
        //获取波浪宽度 第二个参数，如果xml设置这个属性，则会取设置的默认值 也就是说xml没有指定wave_length这个属性，就会取Density.dip2px(context,25)
        waveLength = typedArray.getDimension(R.styleable.CircleWaveProgressView_wave_length, 85f)
        //获取波浪高度
        waveHeight = typedArray.getDimension(R.styleable.CircleWaveProgressView_wave_height, 25f)
        //获取波浪颜色
        wave_color = typedArray.getColor(R.styleable.CircleWaveProgressView_wave_color, Color.parseColor("#ff7c9e"))
        //圆形背景颜色
        circle_bgcolor = typedArray.getColor(R.styleable.CircleWaveProgressView_circlebg_color, Color.GRAY)
        //当前进度
        currentProgress = typedArray.getFloat(R.styleable.CircleWaveProgressView_currentProgress, 50f)
        //最大进度
        maxProgress = typedArray.getFloat(R.styleable.CircleWaveProgressView_maxProgress, 100f)
        //第二层波浪颜色
        secondWaveColor =
            typedArray.getColor(R.styleable.CircleWaveProgressView_second_color, Color.RED)
        //记得把TypedArray回收
        //程序在运行时维护了一个 TypedArray的池，程序调用时，会向该池中请求一个实例，用完之后，调用 recycle() 方法来释放该实例，从而使其可被其他模块复用。
        //那为什么要使用这种模式呢？答案也很简单，TypedArray的使用场景之一，就是上述的自定义View，会随着 Activity的每一次Create而Create，
        //因此，需要系统频繁的创建array，对内存和性能是一个不小的开销，如果不使用池模式，每次都让GC来回收，很可能就会造成OutOfMemory。
        //这就是使用池+单例模式的原因，这也就是为什么官方文档一再的强调：使用完之后一定 recycle,recycle,recycle
        typedArray.recycle()

        wavePaint.color = wave_color
        wavePaint.isAntiAlias = true
        wavePaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

        secondPaint.color = secondWaveColor
        secondPaint.isAntiAlias = true
        //要覆盖在第一层波浪上，所以选SRC_ATOP模式，第二层波浪完全显示，并且第一层非交集部分显示。
        secondPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)

        circlePaint.color = Color.GRAY
        circlePaint.isAntiAlias = true
    }

    /**
     * init 为类的主构造函数，优先于 constructor 次构造函数执行
     */
    init {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val height = measureSize(waveDefaultSize.toInt(), heightMeasureSpec)
        val width = measureSize(waveDefaultSize.toInt(), widthMeasureSpec)
        //获取view最长边的大小
        val maxSize = Math.max(width, height)
        setMeasuredDimension(maxSize, maxSize)
        waveActualSize = maxSize.toFloat()
        //调整波浪数量，就是view能够容纳下几个波浪，使用ceil让view完全被波浪占满，为循环做准备，分母越小越精准
        //Math.ceil(a)返回求不小于a的最小整数
        // Math.ceil(125.9)=126.0
        // Math.ceil(0.4873)=1.0
        // Math.ceil(-0.65)=-0.0
        waveNumber = Math.ceil((waveActualSize / waveLength / 2).toDouble()).toInt()
    }

    private fun measureSize(defaultSize: Int, measureSpec: Int): Int {
        var resultSize = defaultSize
        val specModel = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        resultSize = if (specModel == MeasureSpec.EXACTLY) {
            specSize
        } else {
            Math.min(resultSize, specSize)
        }
        return resultSize
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //使用缓存，根据参数创建新位图
        circleBitmap = Bitmap.createBitmap(waveActualSize.toInt(), waveActualSize.toInt(), Bitmap.Config.ARGB_8888)
        //以bitmap 为底 创建画布
        bitmapCanvas = Canvas(circleBitmap)
        bitmapCanvas.drawCircle(waveActualSize / 2, waveActualSize / 2, waveActualSize / 2 - 8, circlePaint)
        //绘制波浪形
        bitmapCanvas.drawPath(drawWavePath(), wavePaint)

        //是否绘制第二层波浪
        if (isCanvasSecondWave) {
            bitmapCanvas.drawPath(canvasSecondWave(), secondPaint)
        }
        canvas.drawBitmap(circleBitmap, 0f, 0f, null)
//        canvas.drawPath(drawWavePath(), wavePaint)
    }

    fun setCanvasSecondWave(isCanvasSecondWave: Boolean) {
        this.isCanvasSecondWave = isCanvasSecondWave
        invalidate()
    }

    /**
     * 绘制波浪线
     * 第一层波浪，从右往左的波浪
     */
    private fun drawWavePath(): Path {
        wavePath.reset()
//        wavePath.moveTo(0f, waveLength - waveDefaultSize)
        //高度随着进度的变化而改变，并且波浪往左边移动
        wavePath.moveTo(-moveDistance, (1 - currentPercent) * waveActualSize)
        //这里波浪数量乘以2为了水平的平移
        for (i in 0 until waveNumber * 2) {
            wavePath.rQuadTo(waveLength / 2, waveHeight, waveLength, 0f)
            wavePath.rQuadTo(waveLength / 2, -waveHeight, waveLength, 0f)
        }
        wavePath.lineTo(waveActualSize, waveActualSize)
        wavePath.lineTo(0f, waveActualSize)
        wavePath.lineTo(0f, (1 - currentPercent) * waveActualSize)
        wavePath.close()
        return wavePath
    }

    /**
     * 绘制第二层波浪，从左往右的波浪
     */
    private fun canvasSecondWave(): Path {
        val secondWaveHeight = waveHeight
        wavePath.reset()
        wavePath.moveTo(waveActualSize + moveDistance, (1 - currentPercent) * waveActualSize)
        for (i in 0 until waveNumber * 2) {
            wavePath.rQuadTo(-waveLength / 2, secondWaveHeight, -waveLength, 0f)
            wavePath.rQuadTo(-waveLength / 2, -secondWaveHeight, -waveLength, 0f)
        }
        wavePath.lineTo(0f, waveActualSize)
        wavePath.lineTo(waveActualSize, waveActualSize)
        wavePath.lineTo(waveActualSize, (1 - currentPercent) * waveActualSize)
        wavePath.close()
        return wavePath
    }

    inner class WaveAnimator : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            //在绘制动画的过程中会反复的调用applyTransformation函数，
            // 每次调用参数interpolatedTime值都会变化，该参数从0渐 变为1，当该参数为1时表明动画结束
            super.applyTransformation(interpolatedTime, t)
            //更新占比，波浪达到最高就无需循环，只需要左右平移
            if (currentPercent < currentProgress / maxProgress) {
                currentPercent = interpolatedTime * currentProgress / maxProgress
                tv?.text = updateTextListener?.updateText(interpolatedTime, currentProgress, maxProgress)
            }
            //左右平移距离随着动画进度而改变
            moveDistance = interpolatedTime * waveNumber * waveLength * 2
            invalidate()
        }
    }

    fun setProgress(currentProgress: Float, time: Long) {
        this.currentProgress = currentProgress
        resetTime = time
        //从0开始变化
        currentPercent = 0f
        waveAnimation.duration = time
        waveAnimation.repeatCount = Animation.INFINITE
        //动画匀速播放，避免出现卡顿
        waveAnimation.interpolator = LinearInterpolator()
        waveAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
                //动画达到最高，速率发生改变，以7000毫秒时间运行，变慢了
                if (currentPercent == currentProgress / maxProgress) {
                    waveAnimation.duration = 7000
                }
            }

            override fun onAnimationEnd(p0: Animation?) {
            }

            override fun onAnimationStart(p0: Animation?) {
            }

        })
        this.startAnimation(waveAnimation)
    }

    /**
     * 定义文本变化的数值监听
     */
    interface UpdateTextListener {
        /**
         * 提供接口 给外部修改数值样式 等
         * @param interpolatedTime 这个值是动画的 从0变成1
         * @param currentProgress 进度条的数值
         * @param maxProgress 进度条的最大数值
         * @return
         */
        fun updateText(interpolatedTime: Float, currentProgress: Float, maxProgress: Float): String
    }

    fun setUpdateTextListener(updateTextListener: UpdateTextListener) {
        this.updateTextListener = updateTextListener
    }

    fun setTextView(tv: TextView) {
        this.tv = tv
    }

    /**
     * 动画重新开始
     */
    fun reset() {
        waveAnimation.reset()
        currentPercent = 0f
        waveAnimation.duration = resetTime
        this.startAnimation(waveAnimation)
        invalidate()
    }
}