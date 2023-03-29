package com.pangondionkn.vecore.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pangondionkn.vecore.model.DispatchGroup
import com.pangondionkn.vecore.model.data_class.request.ReportRequest
import com.pangondionkn.vecore.model.data_class.response.AddReportResponse
import com.pangondionkn.vecore.model.data_class.response.DataVehicleResponse
import com.pangondionkn.vecore.model.data_class.response.ReportResponse
import com.pangondionkn.vecore.model.networking.ApiConfig
import retrofit2.Call
import retrofit2.Response
import android.os.Handler
import android.os.Looper

class MainViewModel: ViewModel() {
    private val TAG = MainViewModel::class.java.simpleName

    private var _listReport = MutableLiveData<List<ReportResponse>>()
    val listReport: LiveData<List<ReportResponse>> = _listReport

    private var _listVehicle = MutableLiveData<List<DataVehicleResponse>>()
    val listVehicle: LiveData<List<DataVehicleResponse>> = _listVehicle

    private var _imageBitmap = MutableLiveData<Bitmap>()
    val imageBitmap: LiveData<Bitmap> = _imageBitmap

    private var _isBitmapEmpty = MutableLiveData<Boolean>()
    val isBitmapEmpty: LiveData<Boolean> = _isBitmapEmpty

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isFail = MutableLiveData<Boolean>()
    val isFail: LiveData<Boolean> = _isFail

    private var _addReportResponse = MutableLiveData<AddReportResponse>()
    val addReportResponse: LiveData<AddReportResponse> = _addReportResponse

    private  var _addReportFailMessage = MutableLiveData<String>()
    val addReportFailMessage: LiveData<String> = _addReportFailMessage

    fun getReportAll(){
        _isLoading.value = true
        val client1 = ApiConfig.getApiService().getListReport()
        client1.enqueue(object: retrofit2.Callback<List<ReportResponse>>{
            override fun onResponse(
                call: Call<List<ReportResponse>>,
                response: Response<List<ReportResponse>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _listReport.value = response.body()
                    Log.d(TAG, "Response Success Earned")
                }else{
                    _isFail.value = true
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ReportResponse>>, t: Throwable) {
                _isLoading.value = false
                _isFail.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getListVehicle(){
        _isLoading.value = true
        val client1 = ApiConfig.getApiService().getListVehicle()
        client1.enqueue(object: retrofit2.Callback<List<DataVehicleResponse>>{
            override fun onResponse(
                call: Call<List<DataVehicleResponse>>,
                response: Response<List<DataVehicleResponse>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _listVehicle.value = response.body()
                    Log.d(TAG, "Response success Earned")
                }else{
                    _isFail.value = true
                    Log.e(TAG,"OnFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<DataVehicleResponse>>, t: Throwable) {
                _isLoading.value = false
                _isFail.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun setImageBitmap(inputBitmap: Bitmap){
        _imageBitmap.value = inputBitmap
        _isBitmapEmpty.value = false
    }

    fun postReport_e2(report: ReportRequest){
        _isLoading.value = true
        val dispatchGroup = DispatchGroup()

        dispatchGroup?.enter()
        val client1 = ApiConfig.getApiService().addReport(
            report.vehicleId,
            report.note,
            report.userId,
            report.photo
        )
        client1.enqueue(object: retrofit2.Callback<AddReportResponse>{
            override fun onResponse(
                call: Call<AddReportResponse>,
                response: Response<AddReportResponse>
            ) {
                Handler(Looper.getMainLooper()).post {
                    _isLoading.value = false
                    when(response.isSuccessful){
                        true->{
                            _addReportResponse.value = response.body()
                            Log.d(TAG, "response: $addReportResponse")
                        }
                        false->{
                            _isFail.value = true
                            _addReportFailMessage.value = response.message()
                            Log.d(TAG, "onFailure: ${response.message()}")
                        }
                    }
                    dispatchGroup.leave()
                }
            }

            override fun onFailure(call: Call<AddReportResponse>, t: Throwable) {
                _isLoading.value = false
                _isFail.value = true
                _addReportFailMessage.value = t.message.toString()
                Log.d(TAG, "onFailure: ${t.message.toString()}")
                dispatchGroup.leave()
            }

        })

    }

    fun postReport(report: ReportRequest){
        _isLoading.value = true
        val client1 = ApiConfig.getApiService().addReport(
            vehicleId = report.vehicleId,
            note = report.note,
            userId = report.userId,
            photo = report.photo
        )
        client1.enqueue(object: retrofit2.Callback<AddReportResponse>{
            override fun onResponse(
                call: Call<AddReportResponse>,
                response: Response<AddReportResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _addReportResponse.value = response.body()
                    Log.d(TAG, "Response earned")
                }else{
                    _isFail.value = true
                    _addReportFailMessage.value = response.message()
                    Log.d(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<AddReportResponse>, t: Throwable) {
                _isLoading.value = false
                _isFail.value = true
                _addReportFailMessage.value = t.message.toString()
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}