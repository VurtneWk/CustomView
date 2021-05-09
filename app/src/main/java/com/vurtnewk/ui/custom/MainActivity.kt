package com.vurtnewk.ui.custom

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.vurtnewk.ui.custom.lesson03.ImitationQQStepNumberActivity
import com.vurtnewk.ui.custom.lesson04.Lesson04Activity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.mBtn03).setOnClickListener {
            ImitationQQStepNumberActivity.start(this)
        }
        findViewById<Button>(R.id.mBtn04).setOnClickListener {
            Lesson04Activity.start(this)
        }
    }
}