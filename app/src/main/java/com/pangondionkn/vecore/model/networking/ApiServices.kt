package com.pangondionkn.vecore.model.networking

import com.pangondionkn.vecore.model.Utils.NETWORKING.Companion.USER_ID
import com.pangondionkn.vecore.model.data_class.ReportResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    //Getting Report

    @GET("read_all_laporan")
    fun getListReport(
        @Query("userId") userId: String = USER_ID,
        @Query("vehicleLicenseNumber") vehicleLicenseNumber: String ?= null
    ): Call<List<ReportResponse>>
}