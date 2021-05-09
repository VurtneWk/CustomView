package com.vurtnewk.ui.custom.lesson03

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.vurtnewk.ui.custom.R

class ImitationQQStepNumberActivity : AppCompatActivity() {


    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, ImitationQQStepNumberActivity::class.java)
            context.startActivity(starter)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imitation_step_number)

        val mImitationQQStepNumberView =
            findViewById<ImitationQQStepNumberView>(R.id.mImitationQQStepNumberView)
        mImitationQQStepNumberView.setMaxStep(100F)
        var mCurrentStep = 20F
        mImitationQQStepNumberView.setCurrentStep(mCurrentStep)
        findViewById<Button>(R.id.mBtnAdd).setOnClickListener {
            mImitationQQStepNumberView.setCurrentStep(mCurrentStep++)
        }
    }
}