package com.pangondionkn.vecore.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pangondionkn.vecore.R
import com.pangondionkn.vecore.databinding.ActivityMainBinding
import com.pangondionkn.vecore.databinding.LayoutBottomsheetBinding
import com.pangondionkn.vecore.model.data_class.DataVehicleResponse
import com.pangondionkn.vecore.model.data_class.ReportResponse
import com.pangondionkn.vecore.view.adapter.ItemDropdownAdapter
import com.pangondionkn.vecore.view.adapter.ListReportAdapter
import com.pangondionkn.vecore.view.advanced_ui.InputDropdownLayout
import com.pangondionkn.vecore.view.fragment.BottomSheetFragment
import com.pangondionkn.vecore.viewmodel.MainViewModel
import kotlin.math.log

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

        getListVehicle()


    }

    private fun setUpProgressBar(isLoading: Boolean){
        if (isLoading) binding.pbMain.visibility = View.VISIBLE else binding.pbMain.visibility = View.GONE
    }

    private fun setUpListReport(listReport: List<ReportResponse>){
        binding.rvItemReport.setHasFixedSize(true)
        binding.rvItemReport.layoutManager = LinearLayoutManager(this)
        binding.rvItemReport.isNestedScrollingEnabled = false

        val adapter = ListReportAdapter(listReport)

        binding.rvItemReport.adapter = adapter
    }

    private fun getListVehicle(){
        binding.btnMakeReport.setOnClickListener {
            mainViewModel.getListVehicle()
            mainViewModel.listVehicle.observe(this, {listVehicle ->
                setUpBottomSheet(listVehicle)
            })
        }
    }

    private fun setUpBottomSheet(listVehicle: List<DataVehicleResponse>){
        Log.d(TAG, "List of Vehicle: $listVehicle")
        val bottomsheetDialog = BottomSheetDialog(this)
        val mBottomSheetDialog = LayoutBottomsheetBinding.inflate(layoutInflater, null, false)
        bottomsheetDialog.setContentView(mBottomSheetDialog.root)

//        val adapter = ItemDropdownAdapter(ArrayList(listVehicle), object: ItemDropdownAdapter.CustomListener {
//            override fun onClick(customItem: DataVehicleResponse) {
//                var selectedVehicle = customItem.type
//            }
//
//        })
//
//        mBottomSheetDialog.ddlReportForm.setAdapter(adapter)
        mBottomSheetDialog.ddlReportForm.apply {
            Log.d(TAG, "list vehicle_e1: ${ArrayList(listVehicle)}")
            setData(ArrayList(listVehicle))
            setListener(object: InputDropdownLayout.DropdownListener{
                override fun onItemSelected(position: Int, item: String) {
                    Log.d(TAG, "Selected Item: $item")
                }
            })
        }

        bottomsheetDialog.show()
//        supportFragmentManager.beginTransaction().replace(R.id.main_activity, BottomSheetFragment.newInstance(listVehicle)).commit()
    }
}