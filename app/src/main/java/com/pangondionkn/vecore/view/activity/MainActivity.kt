package com.pangondionkn.vecore.view.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pangondionkn.vecore.R
import com.pangondionkn.vecore.databinding.ActivityMainBinding
import com.pangondionkn.vecore.databinding.LayoutBottomsheetBinding
import com.pangondionkn.vecore.model.Utils.IMAGE_UTILS.Companion.bitmapToByteArray
import com.pangondionkn.vecore.model.Utils.IMAGE_UTILS.Companion.reduceBitmap
import com.pangondionkn.vecore.model.Utils.IMAGE_UTILS.Companion.reduceBitmap_e2
import com.pangondionkn.vecore.model.Utils.IMAGE_UTILS.Companion.resizePhoto
import com.pangondionkn.vecore.model.Utils.REQUEST_CODE.Companion.CAMERA_REQUEST
import com.pangondionkn.vecore.model.Utils.TIME_UTILS.Companion.getDateToday
import com.pangondionkn.vecore.model.data_class.request.AddReportRequest
import com.pangondionkn.vecore.model.data_class.request.ReportRequest
import com.pangondionkn.vecore.model.data_class.response.DataVehicleResponse
import com.pangondionkn.vecore.model.data_class.response.ReportResponse
import com.pangondionkn.vecore.view.adapter.ListReportAdapter
import com.pangondionkn.vecore.view.advanced_ui.InputDropdownLayout
import com.pangondionkn.vecore.view.fragment.BottomSheetFragment
import com.pangondionkn.vecore.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = MainActivity::class.java.simpleName
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var imageBitmap: Bitmap
    private val reportRequest = ReportRequest()
    private val addReportRequest= AddReportRequest()

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

        mainViewModel.addReportFailMessage.observe(this, {message ->
            setUpToast(message)
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

        mBottomSheetDialog.ddlReportForm.apply {
            Log.d(TAG, "list vehicle_e1: ${ArrayList(listVehicle)}")
            setHint("Pilih Kendaraan")
            setData(ArrayList(listVehicle))
            setListener(object: InputDropdownLayout.DropdownListener{
                override fun onItemSelected(
                    position: Int,
                    item: String,
                    selectedData: DataVehicleResponse
                ) {
                    Log.d(TAG, "Selected Item: $item")
                    reportRequest.vehicleId = selectedData.vehicleId
                }
            })
        }

        mBottomSheetDialog.etDatePicker.hint = getDateToday()

        mBottomSheetDialog.btnUploadImage.setOnClickListener {
            val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            this@MainActivity.startActivityForResult(cameraIntent, CAMERA_REQUEST)
        }

        mainViewModel.isBitmapEmpty.observe(this, {
            when(it){
                true ->{}
                false ->{
                    mainViewModel.imageBitmap.observe(this, { bitmapImage->
                        Glide.with(bottomsheetDialog.context)
                            .load(bitmapImage)
                            .into(mBottomSheetDialog.ivUploadImage)
                        reportRequest.photo = reduceBitmap_e2(bitmapImage)
                    })
                }
            }
        })

        mBottomSheetDialog.btnSendReport.setOnClickListener {
            val noteRetrieved = mBottomSheetDialog.inlReportForm.getText()
            reportRequest.note = noteRetrieved
            Log.d(TAG, "reportRequest: $reportRequest")
            bottomsheetDialog.dismiss()
            setUpPostReport(reportRequest)
        }

        bottomsheetDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            val photo = data!!.extras!!.get("data")
            imageBitmap = photo as Bitmap
            Log.d(TAG, "photo: $imageBitmap")
            mainViewModel.setImageBitmap(photo)
        }
    }

    private fun setUpPostReport(reportNeedToPost: ReportRequest){
        mainViewModel.postReport(reportNeedToPost)
    }

    private fun setUpToast(message:String){
        when(!message.isEmpty()|| message != ""){
            true ->{
                Toast.makeText(this@MainActivity, "${message}", Toast.LENGTH_SHORT).show()
            }
            false ->{}
        }
    }
}