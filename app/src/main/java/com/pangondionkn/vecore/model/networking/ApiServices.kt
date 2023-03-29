package com.pangondionkn.vecore.model.networking

import android.graphics.Bitmap
import com.pangondionkn.vecore.model.Utils.NETWORKING.Companion.USER_ID
import com.pangondionkn.vecore.model.data_class.response.AddReportResponse
import com.pangondionkn.vecore.model.data_class.response.DataVehicleResponse
import com.pangondionkn.vecore.model.data_class.response.ReportResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {
    //Getting Report

    @GET("read_all_laporan")
    fun getListReport(
        @Query("userId") userId: String = USER_ID,
        @Query("vehicleLicenseNumber") vehicleLicenseNumber: String ?= null
    ): Call<List<ReportResponse>>

    @GET("list_vehicle")
    fun getListVehicle(): Call<List<DataVehicleResponse>>

    @Headers(
        "Accept-Encoding: gzip, deflate",
        "Content-Type: application/json;charset=utf-8",
        "Accept: application/json"
    )
    @POST("add_laporan")
    fun addReport(
        @Query("vehicleId") vehicleId: String,
        @Query("note") note: String,
        @Query("userId") userId: String,
        @Query("photo") photo: ByteArray?
    ): Call<AddReportResponse>

    @Multipart
    @POST("add_laporan")
    fun addReport_e2(
        @Query("vehicleId") vehicleId: String,
        @Query("note") note: String,
        @Query("userId") userId: String,
        @Part photo: MultipartBody.Part
    ):Call<AddReportResponse>
}