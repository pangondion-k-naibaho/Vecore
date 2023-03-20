package com.pangondionkn.vecore.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pangondionkn.vecore.R
import com.pangondionkn.vecore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}