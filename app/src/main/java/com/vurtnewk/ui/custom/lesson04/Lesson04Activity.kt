package com.vurtnewk.ui.custom.lesson04

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.vurtnewk.ui.custom.R

/**
 * 字体左右变色
 */
class Lesson04Activity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, Lesson04Activity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson04)
        val mColorTv: ColorTextView = findViewById(R.id.mColorTv)
        findViewById<Button>(R.id.mBtnChangeOrientation).setOnClickListener {
            mColorTv.rightToLeft = !mColorTv.rightToLeft
        }
    }

}