package com.jesen.flowcoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jesen.flowcoroutine.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
    }
}