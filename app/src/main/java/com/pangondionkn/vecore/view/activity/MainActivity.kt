package com.pangondionkn.vecore.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.pangondionkn.vecore.R
import com.pangondionkn.vecore.databinding.ActivityMainBinding
import com.pangondionkn.vecore.model.Utils.LIST_REPORT.Companion.getdummyListReport
import com.pangondionkn.vecore.view.adapter.ListReportAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setUpListReport()
    }

    private fun setUpListReport(){
        binding.rvItemReport.setHasFixedSize(true)
        binding.rvItemReport.layoutManager = LinearLayoutManager(this)

        val adapter = ListReportAdapter(getdummyListReport())

        binding.rvItemReport.adapter = adapter
    }
}