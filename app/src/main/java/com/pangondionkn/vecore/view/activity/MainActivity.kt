package com.pangondionkn.vecore.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pangondionkn.vecore.databinding.ActivityMainBinding
import com.pangondionkn.vecore.model.data_class.ReportResponse
import com.pangondionkn.vecore.view.adapter.ListReportAdapter
import com.pangondionkn.vecore.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = MainActivity::class.java.simpleName
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mainViewModel.isLoading.observe(this, {
            setUpProgressBar(it)
        })

        mainViewModel.getReportAll()
        mainViewModel.listReport.observe(this, {listReport ->
            setUpListReport(listReport)
        })


    }

    private fun setUpProgressBar(isLoading: Boolean){
        if (isLoading) binding.pbMain.visibility = View.VISIBLE else binding.pbMain.visibility = View.GONE
    }

    private fun setUpListReport(listReport: List<ReportResponse>){
        binding.rvItemReport.setHasFixedSize(true)
        binding.rvItemReport.layoutManager = LinearLayoutManager(this)

        val adapter = ListReportAdapter(listReport)

        binding.rvItemReport.adapter = adapter
    }
}