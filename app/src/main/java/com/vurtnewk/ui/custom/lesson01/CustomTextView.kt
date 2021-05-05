package com.vurtnewk.ui.custom.lesson01

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.vurtnewk.ui.custom.R

/**
 * Created by VurtneWk on 2021/5/2
 */
@Suppress("unused")
class CustomTextView : AppCompatTextView {

    private val mPaint: Paint = Paint()
    private var mText: String? = null
    private var mColor: Int = Color.BLACK
    private var mTextSize = 15

    //代码中在使用new CustomTextView(this)调用
    constructor(context: Context) : super(context)

    //layout中创建时调用
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    //layout中创建时 且使用了 style 属性时调用
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ) {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView)
        mText = typedArray.getString(R.styleable.CustomTextView_text)
        mColor = typedArray.getColor(R.styleable.CustomTextView_textColor, mColor)
        //15 15px 15sp?
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.CustomTextView_textSize, mTextSize)
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
        val width = MeasureSpec.getSize(widthMeasureSpec)

        //2.给的是wrap_content 需要计算
        when (widthMode) {
            MeasureSpec.AT_MOST -> {
                //计算的宽度 与 字体长度、大小有关，使用画笔测量

            }


        }

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
    }
}