package com.vurtnewk.ui.custom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.vurtnewk.ui.custom.lesson03.ImitationQQStepNumberActivity
import com.vurtnewk.ui.custom.lesson03.ImitationQQStepNumberView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.mBtn03).setOnClickListener {
            ImitationQQStepNumberActivity.start(this)
        }
    }
}