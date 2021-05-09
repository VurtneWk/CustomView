package com.vurtnewk.ui.custom.lesson04

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.orhanobut.logger.Logger
import com.vurtnewk.ui.custom.R

/**
 * Created by VurtneWk on 2021/5/9
 *
 * extends TextView  :  onMeasure()不需要实现 textColor颜色   textSize字体大小  会少很多逻辑
 * 1. 一个文字两种颜色   两个画笔去画
 * 2. 能够从左到右，从右到左
 * 3. 整合到ViewPager
 */
class ColorTextView : AppCompatTextView {

    private var originColor = Color.BLACK
    private var changeColor = Color.BLACK
    private val mOriginPaint = Paint()
    private val mChangePaint = Paint()
    private val bounds = Rect()
    private var mCurrentProgress = 0.55F

    //属性只在第一次被访问的时候才会计算，之后则会将之前的计算结果缓存起来供后续调用
    private val baseline by lazy(LazyThreadSafetyMode.NONE) {
        paint.baseline(height)
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ) {

        context.obtainStyledAttributes(attrs, R.styleable.ColorTextView).apply {
            originColor = getColor(R.styleable.ColorTextView_originColor, originColor)
            changeColor = getColor(R.styleable.ColorTextView_changeColor, changeColor)
            recycle()
        }

        mOriginPaint.apply {
            color = originColor
            isAntiAlias = true
            textSize = this@ColorTextView.textSize
            isDither = true//防抖动
        }

        mChangePaint.apply {
            color = changeColor
            isAntiAlias = true
            textSize = this@ColorTextView.textSize
            isDither = true//防抖动
        }

    }

    /**
     * clipRect API可以裁剪,左边一个画笔,右边一个画笔 ,不断改变中间值
     */
    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas) 自己画
        canvas ?: return
        //获取字体的宽度
        paint.getTextBounds(text.toString(), 0, text.length, bounds)

        //根据进度计算中间值
        val middle = mCurrentProgress * width
        //绘制未变色区域 从0到计算出的middle值
        drawCustomText(canvas, mOriginPaint, 0F, middle)
        //绘制变色区域 从middle值到文本宽度 (这里传的值还有问题.)
        drawCustomText(canvas, mChangePaint, middle, width.toFloat())
    }

    /**
     * @param start 裁剪画布的起始位置
     * @param end 裁剪画布的结束位置
     */
    private fun drawCustomText(canvas: Canvas, paint: Paint, start: Float, end: Float) {
        //开始绘制
        canvas.save()
        canvas.clipRect(start, 0F, end, height.toFloat())
        canvas.drawText(text.toString(), width / 2 - bounds.width().toFloat() / 2, baseline, paint)
        canvas.restore()
    }

}

fun Paint.baseline(height: Int): Float {
    return height / 2 + ((this.fontMetrics.bottom - this.fontMetrics.top) / 2 - this.fontMetrics.bottom)
}











