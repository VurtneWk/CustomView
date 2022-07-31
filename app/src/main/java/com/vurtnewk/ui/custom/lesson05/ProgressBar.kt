package com.vurtnewk.ui.custom.lesson05

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.vurtnewk.ui.custom.R

/**
 * 圆形进度条
 */
class ProgressBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val outerPaint: Paint = Paint()


    init {
        context.obtainStyledAttributes(defStyleAttr, R.styleable.ProgressBar).apply {
            outerPaint.color = this.getColor(R.styleable.ProgressBar_outerBackground, Color.RED)
            outerPaint.isAntiAlias = true
            outerPaint.style = Paint.Style.STROKE
            outerPaint.strokeWidth = this.getDimension(R.styleable.ProgressBar_roundWidth,10F)
            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        //只保证是正方形
        setMeasuredDimension(width.coerceAtMost(height), width.coerceAtMost(height))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(width / 2F, height / 2F, width / 2F, outerPaint)
    }

}