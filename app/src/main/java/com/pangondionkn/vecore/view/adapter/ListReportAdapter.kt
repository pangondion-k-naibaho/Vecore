package com.pangondionkn.vecore.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pangondionkn.vecore.R
import com.pangondionkn.vecore.databinding.LayoutItemrvBinding
import com.pangondionkn.vecore.model.Utils.CARS.Companion.DAIHATSU_AVANZA
import com.pangondionkn.vecore.model.Utils.CARS.Companion.DAIHATSU_AYLA
import com.pangondionkn.vecore.model.Utils.CARS.Companion.GRAND_MAX
import com.pangondionkn.vecore.model.Utils.CARS.Companion.HONDA_BRIO
import com.pangondionkn.vecore.model.Utils.CARS.Companion.HONDA_CIVIC
import com.pangondionkn.vecore.model.Utils.CARS.Companion.NISSAN_GRAND_LIVINA
import com.pangondionkn.vecore.model.Utils.CARS.Companion.NISSAN_JUKE
import com.pangondionkn.vecore.model.Utils.CARS.Companion.TOYOTA_AGYA
import com.pangondionkn.vecore.model.Utils.CARS.Companion.WULING
import com.pangondionkn.vecore.model.Utils.CARS.Companion.XENIA
import com.pangondionkn.vecore.model.Utils.REPORT_STATUS.Companion.IN_PROCESS
import com.pangondionkn.vecore.model.Utils.REPORT_STATUS.Companion.NOT_SENT
import com.pangondionkn.vecore.model.data_class.response.ReportResponse

class ListReportAdapter(
    var data: List<ReportResponse>?= null,
): RecyclerView.Adapter<ListReportAdapter.ItemHolder>() {

    inner class ItemHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(item: ReportResponse) = with(itemView){
            val binding = LayoutItemrvBinding.bind(itemView)
            binding.apply {
                tvReportDate.text = item.createdAt
                tvTitleVehicleName.text = item.vehicleName
                tvContentComplaintNotes.text = item.note
                tvTitleReportBy.text = item.createdBy
                tvReportStatus.text = item.reportStatus
                tvLicenseNumber.text = item.vehicleLicenseNumber
                tvReportId.text = item.reportId
                when(item.reportStatus){
                    NOT_SENT ->{
                        tvReportStatus.background = resources.getDrawable(R.drawable.bg_status_red)
                    }
                    IN_PROCESS ->{
                        tvReportStatus.background = resources.getDrawable(R.drawable.bg_status_yellow)
                    }
                    else ->{
                        tvReportStatus.background = resources.getDrawable(R.drawable.bg_status_green)
                    }
                }

                when(item.vehicleName){
                    DAIHATSU_AVANZA -> {
                        tvLicenseNumber.background = resources.getDrawable(R.drawable.bg_vehicle_red)
                    }
                    DAIHATSU_AYLA ->{
                        tvLicenseNumber.background = resources.getDrawable(R.drawable.bg_vehicle_orange)
                    }
                    GRAND_MAX ->{
                        tvLicenseNumber.background = resources.getDrawable(R.drawable.bg_vehicle_yellow)
                    }
                    HONDA_BRIO ->{
                        tvLicenseNumber.background = resources.getDrawable(R.drawable.bg_vehicle_green)
                    }
                    HONDA_CIVIC ->{
                        tvLicenseNumber.background = resources.getDrawable(R.drawable.bg_vehicle_blue)
                    }
                    NISSAN_GRAND_LIVINA ->{
                        tvLicenseNumber.background = resources.getDrawable(R.drawable.bg_vehicle_nila)
                    }
                    NISSAN_JUKE ->{
                        tvLicenseNumber.background = resources.getDrawable(R.drawable.bg_vehicle_purple)
                    }
                    TOYOTA_AGYA->{
                        tvLicenseNumber.background = resources.getDrawable(R.drawable.bg_vehicle_donker_blue)
                    }
                    WULING->{
                        tvLicenseNumber.background = resources.getDrawable(R.drawable.bg_vehicle_pink)
                    }
                    XENIA->{
                        tvLicenseNumber.background = resources.getDrawable(R.drawable.bg_vehicle_green_highlight)
                    }
                }

                Glide.with(context).load(item.photo)
                    .into(ivContentComplaintNotes)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_itemrv, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(data?.get(position)!!)
    }

    override fun getItemCount(): Int = data?.size!!
}