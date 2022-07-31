package com.vurtnewk.ui.custom.lesson03

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.vurtnewk.ui.custom.R

/**
 * 防QQ步数
 */
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

        mImitationQQStepNumberView.setMaxStep(4000F)

        var mCurrentStep = 1000F
        // 属性动画 后面讲的内容
        val valueAnimator = ObjectAnimator.ofFloat(0f, mCurrentStep)
        valueAnimator.duration = 2000
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.addUpdateListener { animation ->
            val currentStep = animation.animatedValue as Float
            mImitationQQStepNumberView.setCurrentStep(currentStep)
        }
        valueAnimator.start()
        //

        findViewById<Button>(R.id.mBtnAdd).setOnClickListener {
//            Toast.makeText(this, "已取消setOnClickListener", Toast.LENGTH_SHORT).show()
            mCurrentStep += 100
            mImitationQQStepNumberView.setCurrentStep(mCurrentStep)
        }


    }
}