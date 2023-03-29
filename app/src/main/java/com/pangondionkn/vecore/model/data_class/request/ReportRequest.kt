package com.pangondionkn.vecore.model.data_class.request

import android.graphics.Bitmap
import com.pangondionkn.vecore.model.Utils.NETWORKING.Companion.USER_ID

data class ReportRequest(
    var vehicleId: String = "",
    var note: String = "",
    var userId: String = USER_ID,
    var photo: ByteArray?= null
)