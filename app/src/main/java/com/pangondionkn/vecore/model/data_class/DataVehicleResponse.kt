package com.pangondionkn.vecore.model.data_class

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataVehicleResponse(
    var vehicleId: String = "",
    var licenseNumber: String = "",
    var type: String = "",
    var isSelected: Boolean = false
):Parcelable