package com.gbreed.kotlinfun2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IntegerRes
import kotlinx.android.synthetic.main.activity_main.*
import android.view.View

class MainActivity : AppCompatActivity()
{

    var amount = 0

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewCount.setText(amount.toString())
    }

    fun reset(view : View)
    {
        amount = 0
        textViewCount.setText(amount.toString())
    }

    fun add(view : View)
    {
        amount+=1
        textViewCount.setText(amount.toString())
    }
}
