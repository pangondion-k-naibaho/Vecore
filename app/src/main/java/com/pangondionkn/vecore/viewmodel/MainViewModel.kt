package com.pangondionkn.vecore.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pangondionkn.vecore.model.data_class.DataVehicleResponse
import com.pangondionkn.vecore.model.data_class.ReportResponse
import com.pangondionkn.vecore.model.networking.ApiConfig
import retrofit2.Call
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val TAG = MainViewModel::class.java.simpleName

    private var _listReport = MutableLiveData<List<ReportResponse>>()
    val listReport: LiveData<List<ReportResponse>> = _listReport

    private var _listVehicle = MutableLiveData<List<DataVehicleResponse>>()
    val listVehicle: LiveData<List<DataVehicleResponse>> = _listVehicle

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isFail = MutableLiveData<Boolean>()
    val isFail: LiveData<Boolean> = _isFail

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
}