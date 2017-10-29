package com.example.fly.level_view_demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.levelviewlibray.LevelView
import com.example.levelviewlibray.LevelViewBaseData

class MainActivity : AppCompatActivity(), View.OnClickListener {


    private var btn0: Button? = null
    private var btn1: Button? = null
    private var btn2: Button? = null
    private var btn3: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn0 = findViewById(R.id.button0)
        btn1 = findViewById(R.id.button1)
        btn2 = findViewById(R.id.button2)
        btn3 = findViewById(R.id.button3)
        btn0!!.setOnClickListener(this)
        btn1!!.setOnClickListener(this)
        btn2!!.setOnClickListener(this)
        btn3!!.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.button0 -> ShowActivity.gotoShowActivity(this@MainActivity, 0)
            R.id.button1 -> ShowActivity.gotoShowActivity(this@MainActivity, 1)
            R.id.button2 -> ShowActivity.gotoShowActivity(this@MainActivity, 2)
            R.id.button3 -> ShowActivity.gotoShowActivity(this@MainActivity, 3)
        }
    }
}
