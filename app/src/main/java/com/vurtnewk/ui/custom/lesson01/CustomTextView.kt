package com.vurtnewk.ui.custom.lesson01

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.vurtnewk.ui.custom.R

/**
 * Created by VurtneWk on 2021/5/2
 * 注意:直接继承View的，而不是TextView
 */
@Suppress("unused")
class CustomTextView : View {

    companion object {
        val TAG = CustomTextView::class.java.simpleName.toString()
    }

    private val mPaint: Paint = Paint()
    private var mText: String
    private var mColor: Int = Color.BLACK
    private var mTextSize = 15
    private val bounds: Rect = Rect()

    //代码中在使用new CustomTextView(this)调用
    constructor(context: Context) : this(context, null)

    //layout中创建时调用
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    //layout中创建时 且使用了 style 属性时调用
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ) {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView)
        mText = typedArray.getString(R.styleable.CustomTextView_cus_text) ?: ""
        mColor = typedArray.getColor(R.styleable.CustomTextView_cus_textColor, mColor)
        //15 15px 15sp?
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.CustomTextView_cus_textSize, mTextSize)
        //回收
        typedArray.recycle()
        //配置Paint
        with(mPaint) {
            isAntiAlias = true //抗锯齿
            //设置字体大小和颜色
            textSize = mTextSize.toFloat()
            color = mColor
        }
    }

    /**
     * 测量View大小
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 布局的宽高都是由这个方法指定
        // 指定控件的宽高，需要测量
        // 获取宽高的模式
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        //1.确定的值,不需要计算,给的多少就是多少
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)

        //2.给的是wrap_content 需要计算
        width = when (widthMode) {
            MeasureSpec.AT_MOST -> {
                //计算的宽度 与 字体长度、大小有关，使用画笔测量
                if (TextUtils.isEmpty(mText)) {
                    mText = ""
                }
                mPaint.getTextBounds(mText, 0, mText.length, bounds)
                // + paddingLeft + paddingRight 才能试这个属性生效
                bounds.width() + paddingLeft + paddingRight
            }
            else -> {
                width
            }
        }
        height = when (heightMode) {
            MeasureSpec.AT_MOST -> {
                //计算的宽度 与 字体长度、大小有关，使用画笔测量
                mPaint.getTextBounds(mText, 0, mText.length, bounds)
                bounds.height() + paddingTop + paddingBottom
            }
            else -> {
                height
            }
        }
        //设置控件宽高
        setMeasuredDimension(width, height)
    }

    /**
     * 用于绘制
     * @param canvas
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        /*// 画文本
        canvas.drawText();
        // 画弧
        canvas.drawArc();
        // 画圆
        canvas.drawCircle();*/
        //x 开始的位置
        //y 基线 baseline 具体可看资料data/Paint_FontMetrics
        //dy 中线到baseline的距离  top是一个负值
        //这里的计算参考图片
        Log.d(TAG, "bottom:${mPaint.fontMetricsInt.bottom},top:${mPaint.fontMetricsInt.top}")
        val dy = (mPaint.fontMetricsInt.bottom - mPaint.fontMetricsInt.top) / 2 - mPaint.fontMetricsInt.bottom
        Log.d(TAG, "dy = $dy")
        Log.d(TAG, "height/2 -> ${(height / 2)} and ${(mPaint.fontMetricsInt.bottom - mPaint.fontMetricsInt.top) / 2}")
        val baseline = (height / 2).toFloat() + dy

        canvas.drawText(mText, paddingLeft.toFloat(), baseline, mPaint)

    }
}