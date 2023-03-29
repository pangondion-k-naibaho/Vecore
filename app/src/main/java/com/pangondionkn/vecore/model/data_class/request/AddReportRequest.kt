package com.pangondionkn.vecore.model.data_class.request

import com.pangondionkn.vecore.model.Utils
import okhttp3.MultipartBody

data class AddReportRequest(
    var vehicleId: String = "",
    var note: String = "",
    var userId: String = Utils.NETWORKING.USER_ID,
    var photo: MultipartBody.Part?= null
)