package com.pangondionkn.vecore.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pangondionkn.vecore.R
import com.pangondionkn.vecore.databinding.LayoutItemDropdownBinding
import com.pangondionkn.vecore.model.data_class.DataVehicleResponse

class ItemDropdownAdapter(
    var data: ArrayList<DataVehicleResponse>,
    private val listener: CustomListener?,
    private var context: Context?= null,
): RecyclerView.Adapter<ItemDropdownAdapter.ViewHolder>() {
    interface CustomListener{
        fun onClick(customItem: DataVehicleResponse)
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(item: DataVehicleResponse, listener: CustomListener?){
            val binding = LayoutItemDropdownBinding.bind(itemView)
            binding.tvItemDropdown.text = item.type
            when(item.isSelected){
                true ->{
                    binding.tvItemDropdown.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                }
                false ->{
                    binding.tvItemDropdown.setTextColor(ContextCompat.getColor(context!!, R.color.black))
                }
            }
            binding.root.setOnClickListener { listener?.onClick(item)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_item_dropdown, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = data.size

    fun updateChecked(item: DataVehicleResponse){
        data.mapIndexed { _, data -> data.isSelected = data.vehicleId == data.type}
        item.isSelected = true
        notifyDataSetChanged()
    }
}