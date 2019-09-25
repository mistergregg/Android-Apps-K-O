package com.gbreed.kotlinfun

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        count = 0
        var textView = findViewById<TextView>(R.id.textCount)
        textView.setText(count.toString())
    }

    fun resetNum(view : View)
    {
        count = 0
        textCount.setText(count.toString())
    }

    fun addNum(view : View)
    {
        count += 1
        textCount.setText(count)
    }
}
