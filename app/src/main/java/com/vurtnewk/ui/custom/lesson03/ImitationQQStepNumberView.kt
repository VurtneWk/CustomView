package com.vurtnewk.ui.custom.lesson03

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log.d
import com.orhanobut.logger.Logger
import android.view.View
import com.vurtnewk.ui.custom.R

/**
 * Created by VurtneWk on 2021/5/7
 */
class ImitationQQStepNumberView : View {

    private var mOuterColor = Color.RED
    private var mInnerColor = Color.BLUE
    private var mBorderWidth = 2F
    private var mStepTextSize = 15
    private var mStepTextColor = Color.BLACK
    private val mOuterPaint: Paint = Paint()
    private val mInnerPaint: Paint = Paint()
    private val mTextPaint: Paint = Paint()
    private lateinit var rectF: RectF
    private var mMaxStep = 0F
    private var mCurrentStep = 0F
    private val bounds = Rect()


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        //1.分析效果
        //2.确定自定义属性，编写attrs.xml
        //3.在布局中使用
        //4.在自定义view中获取自定义属性
        //5.onMeasure()
        //6.画外圆弧、内圆弧、文字

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImitationQQStepNumberView)
        //颜色
        mOuterColor = typedArray.getColor(R.styleable.ImitationQQStepNumberView_outerColor, mOuterColor)
        mInnerColor = typedArray.getColor(R.styleable.ImitationQQStepNumberView_innerColor, mInnerColor)
        //
        mBorderWidth = typedArray.getDimension(R.styleable.ImitationQQStepNumberView_borderWidth,
                mBorderWidth)
        //字体大小和颜色
        mStepTextSize = typedArray.getDimension(R.styleable.ImitationQQStepNumberView_textSize,
                mStepTextSize.toFloat()).toInt()
        mStepTextColor = typedArray.getColor(R.styleable.ImitationQQStepNumberView_textColor,
                mStepTextColor)
        typedArray.recycle()

        Logger.d("mOuterColor:$mOuterColor, mInnerColor:$mInnerColor, mBorderWidth:$mBorderWidth," +
                " mStepTextSize:$mStepTextSize, mStepTextColor:$mStepTextColor")

        //同一个画笔也能处理，不过每次都需要重新设置画笔颜色等.
        mOuterPaint.apply {
            isAntiAlias = true //抗锯齿
            color = mOuterColor
            strokeWidth = mBorderWidth //粗细
            //STROKE 画线模式(描边) ○ ， Fill 填充模式 ●
            style = Paint.Style.STROKE
            //Cap指定了对描边线和路径的开始和结束的处理
            strokeCap = Paint.Cap.ROUND
        }

        mInnerPaint.apply {
            isAntiAlias = true
            color = mInnerColor
            strokeWidth = mBorderWidth
            //STROKE 画线模式 ,
            style = Paint.Style.STROKE
            //Cap指定了对描边线和路径的开始和结束的处理
            strokeCap = Paint.Cap.ROUND
        }

        mTextPaint.apply {
            isAntiAlias = true
            color = mStepTextColor
            textSize = mStepTextSize.toFloat()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //调用者在布局文件中 可能是wrap_content , 或 宽度高度不一致
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        //宽度高度不一直取最小值,确保正方形
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)

        //如果是warp_content
        if(widthMode == MeasureSpec.AT_MOST){
            width = 280
        }
        if(heightMode == MeasureSpec.AT_MOST){
            height = 280
        }
        setMeasuredDimension(width.coerceAtMost(height), width.coerceAtMost(height))
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rectF = RectF(mBorderWidth / 2, mBorderWidth / 2,
                width - mBorderWidth / 2, width - mBorderWidth / 2)
        Logger.d("rectF-> $rectF")
    }

    //6.画外圆弧、内圆弧、文字

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        val center = width / 2
//        val radius = width / 2 - mBorderWidth / 2
        //外圆弧 （要预留位置.如果是0,这弧形显示不全）
        /**
         * rectF: 给一个矩形,在这个矩形的坐标系内画图
         * startAngle:起始位置, 十  右X轴为0,顺时针为针
         * sweepAngle:弧形扫过的角度,
         * useCenter为true 在这里是一个封闭的图形
         * mPaint: 画笔
         */
        canvas.drawArc(rectF, 135F, 270F, false, mOuterPaint)


        //内圆弧 百分比 使用者设置
        if (mMaxStep == 0F) return
        val sweepAngle = mCurrentStep / mMaxStep * 270F
        canvas.drawArc(rectF, 135F, sweepAngle, false, mInnerPaint)

        //写文字 mCurrentStep.toInt().toString()
        //计算文字长度
        val str = mCurrentStep.toInt().toString()
        mTextPaint.getTextBounds(str, 0, str.length, bounds)
        //起点X 坐标
        val x = (width - bounds.width()) / 2.toFloat()
        //高度上baseline
        val baseline = (mTextPaint.fontMetricsInt.bottom - mTextPaint.fontMetricsInt.top) / 2 -
                mTextPaint.fontMetricsInt.bottom + height / 2
        canvas.drawText(str, x, baseline.toFloat(), mTextPaint)
    }

    fun setMaxStep(maxStep: Float) {
        this.mMaxStep = maxStep
    }

    fun setCurrentStep(currentStep: Float) {
        if (currentStep > this.mMaxStep) {
            return
        }
        this.mCurrentStep = currentStep
        /**
         * invalidate 源码分析
         * 一路往parent调用 invalidateChildInParent
         * 最终再由最外层view调用draw()-> dispatchDraw()直到当前调用invalidate的View的onDraw()方法
         * 牵连整个layout布局中的View
         */
        invalidate()
    }
}