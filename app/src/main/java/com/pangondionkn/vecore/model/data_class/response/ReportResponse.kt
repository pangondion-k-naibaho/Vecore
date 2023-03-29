package com.pangondionkn.vecore.model.data_class.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReportResponse(
    var reportId: String = "",
    var vehicleId: String = "",
    var vehicleLicenseNumber: String = "",
    var vehicleName: String = "",
    var note: String = "",
    var photo: String = "",
    var createdAt: String = "",
    var createdBy: String = "",
    var reportStatus: String = ""
):Parcelable