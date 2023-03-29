package com.pangondionkn.vecore.model

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.DisplayMetrics
import android.widget.CalendarView
import android.widget.ImageView
import com.pangondionkn.vecore.model.data_class.response.ReportResponse
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class Utils {
    interface REPORT_STATUS{
        companion object{
            const val SEND = "Terkirim"
            const val IN_PROCESS = "Sedang Diproses"
            const val NOT_SENT = "Tidak Terkirim"
        }
    }

    interface NETWORKING{
        companion object{
            const val BASE_URL = "https://public-api-staging.fleetify.id/api/android/"
            const val USER_ID = "gJHhrGKYP6"
        }
    }

    interface CARS{
        companion object{
            const val DAIHATSU_AVANZA = "Daihatsu Avanza"
            const val DAIHATSU_AYLA = "Daihatsu Ayla"
            const val GRAND_MAX = "Grand Max"
            const val HONDA_BRIO = "Honda Brio"
            const val HONDA_CIVIC = "Honda Civic"
            const val NISSAN_GRAND_LIVINA = "Nissan Grand Livina"
            const val NISSAN_JUKE = "Nissan Juke"
            const val TOYOTA_AGYA = "Toyota Agya"
            const val WULING = "Wuling"
            const val XENIA = "Xenia"
        }
    }

    interface REQUEST_CODE{
        companion object{
            const val CAMERA_REQUEST = 1888
        }
    }

    interface EXTENSION{
        companion object{
            fun convertDpToPx(dp: Int): Int {
                val metrics = Resources.getSystem().displayMetrics
                return (dp * (metrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
            }
        }
    }

    interface TIME_UTILS{
        companion object{
            fun getDateToday(): String{
                val formatter = SimpleDateFormat("EE, dd MM - HH:mm")
                val time = Calendar.getInstance().time
                val current = formatter.format(time)

                return current
            }
        }
    }

    interface IMAGE_UTILS{
        companion object{
            const val PREFERRED_IMAGE_SIZE = 200  //400kb
            const val ONE_MB_TO_KB = 1024

             fun imageToBitmap(image: ImageView): ByteArray {
                val bitmap = (image.drawable as BitmapDrawable).bitmap
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

                return stream.toByteArray()
            }

            fun bitmapToByteArray(bitmap: Bitmap): ByteArray{
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)

                return stream.toByteArray()
            }

            fun resizePhoto(bitmap: Bitmap): Bitmap {

                val w = bitmap.width
                val h = bitmap.height
                val aspRat = w / h
                val W = 400
                val H = W * aspRat
                val b = Bitmap.createScaledBitmap(bitmap, W, H, false)

                return b

            }

            fun reduceBitmap_e2(bitmap: Bitmap):ByteArray{
                var bitmapCont = bitmap
                var stream = ByteArrayOutputStream()

                bitmapCont.compress(Bitmap.CompressFormat.JPEG, 50, stream)

                if(stream.toByteArray().size / ONE_MB_TO_KB > PREFERRED_IMAGE_SIZE){
                    bitmapCont = resizePhoto(bitmapCont)
                    reduceBitmap_e2(bitmapCont)
                }
                return stream.toByteArray()
            }

            fun reduceBitmap(bitmap: Bitmap): Bitmap{
                val imageInBitMapAfterResize: Bitmap
                val baos = ByteArrayOutputStream()

                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)

                if (baos.toByteArray().size / ONE_MB_TO_KB > PREFERRED_IMAGE_SIZE) {
                    //resize photo & set Image in imageview In UI
                    imageInBitMapAfterResize = resizePhoto(bitmap)
                }else{
                    imageInBitMapAfterResize = bitmap
                }

                return imageInBitMapAfterResize
            }
        }
    }

    interface LIST_REPORT{
        companion object{
            fun _getListReport(): List<ReportResponse> = listOf(
                ReportResponse(
                    reportId = "LP/20230320/105220/VEHICLE-02",
                    vehicleId = "VEHICLE-02",
                    vehicleLicenseNumber = "B-8730-YZZ",
                    vehicleName = "Xenia",
                    note = "test",
                    photo = "https://storage.googleapis.com/fleetifyid_images_staging/android/gJHhrGKYP6/REPORT-52105220.png",
                    createdAt = "2023-03-20 10:52:21",
                    createdBy = "Pangondion K Naibaho",
                    reportStatus = "Terkirim"
                ),
                ReportResponse(
                    reportId = "LP/20230320/105216/VEHICLE-08",
                    vehicleId = "VEHICLE-08",
                    vehicleLicenseNumber = "AB-2023-MAR",
                    vehicleName = "Grand Max",
                    note = "test",
                    photo = "https://storage.googleapis.com/fleetifyid_images_staging/android/gJHhrGKYP6/REPORT-91105216.png",
                    createdAt = "2023-03-20 10:52:17",
                    createdBy = "Pangondion K Naibaho",
                    reportStatus = "Terkirim"
                ),
                ReportResponse(
                    reportId = "LP/20230321/174222/VEHICLE-09",
                    vehicleId = "VEHICLE-09",
                    vehicleLicenseNumber = "AB-2023-MAR",
                    vehicleName = "Daihatsu Ayla",
                    note = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa",
                    photo = "https://storage.googleapis.com/fleetifyid_images_staging/android/gJHhrGKYP6/REPORT-91105216.png",
                    createdAt = "2023-03-21 17:42:23",
                    createdBy = "Pangondion K Naibaho",
                    reportStatus = "Terkirim"
                )
            )

            fun getdummyListReport(): ArrayList<ReportResponse> = ArrayList(_getListReport())
        }
    }
}