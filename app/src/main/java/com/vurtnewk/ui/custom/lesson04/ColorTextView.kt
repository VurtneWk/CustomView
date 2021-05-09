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

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ) {

        val typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.ColorTextView)

        originColor = typedArray.getColor(R.styleable.ColorTextView_originColor, originColor)
        changeColor = typedArray.getColor(R.styleable.ColorTextView_changeColor, changeColor)
        typedArray.recycle()

        mOriginPaint.apply {
            color = originColor
            isAntiAlias = true
            textSize = this@ColorTextView.textSize
            isDither = true//防抖动？
        }

        mChangePaint.apply {
            color = changeColor
            isAntiAlias = true
            textSize = this@ColorTextView.textSize
            isDither = true//防抖动？
        }
    }

    /**
     * clipRect API可以裁剪,左边一个画笔,右边一个画笔 ,不断改变中间值
     */
    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas) 自己画
        canvas ?: return

        //根据进度计算中间值
        val middle = mCurrentProgress * width

        //画布裁剪
        canvas.save()
        canvas.clipRect(0F, 0F, middle, height.toFloat())
        //获取字体的宽度
        mOriginPaint.getTextBounds(text.toString(), 0, text.length, bounds)
        //文字起始坐标X
        val x = width / 2 - bounds.width().toFloat() / 2
        //文字的y坐标
//        val baseline =
//            height / 2 - ((mChangePaint.fontMetrics.bottom - mChangePaint.fontMetrics.top) / 2 - mChangePaint.fontMetrics.bottom)
        val baseline = mChangePaint.baseline(height)
        Logger.d("baseline:$baseline , height:$height")
        canvas.drawText(text.toString(), x, baseline, mOriginPaint)
        canvas.restore()
        //绘制变色区域

        //画布裁剪
        canvas.save()
        canvas.clipRect(middle, 0F, width.toFloat(), height.toFloat())
        canvas.drawText(text.toString(), x, baseline, mChangePaint)
        canvas.restore()
    }


    fun drawText(canvas: Canvas, paint: Paint, start: Float, baseline: Float) {
        canvas.save()
        canvas.drawText(text.toString(), start, baseline, paint)
        canvas.restore()
    }

}

fun Paint.baseline(height: Int): Float {
    return height / 2 - ((this.fontMetrics.bottom - this.fontMetrics.top) / 2 - this.fontMetrics.bottom)
}











