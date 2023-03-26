package com.pangondionkn.vecore.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pangondionkn.vecore.R
import com.pangondionkn.vecore.databinding.LayoutBottomsheetBinding
import com.pangondionkn.vecore.model.data_class.DataVehicleResponse

class BottomSheetFragment(): BottomSheetDialogFragment() {

    private lateinit var binding: LayoutBottomsheetBinding
    private lateinit var retrievedListVehicle: ArrayList<DataVehicleResponse>
    private val TAG = BottomSheetFragment::class.java.simpleName

    companion object{
        const val DELIVERED_LIST_VEHICLE = "DELIVERED_LIST_VEHICLE"
        fun newInstance(deliveredListVehicle: List<DataVehicleResponse>): BottomSheetFragment{
            val fragment = BottomSheetFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList(DELIVERED_LIST_VEHICLE, ArrayList(deliveredListVehicle))
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutBottomsheetBinding.inflate(inflater, container, false)

        retrievedListVehicle = arguments?.getParcelableArrayList(DELIVERED_LIST_VEHICLE)!!

        setUpDropdown(retrievedListVehicle)

        return binding.root
    }

    private fun setUpDropdown(listVehicle: ArrayList<DataVehicleResponse>){
        Log.d(TAG, "listVehicle: ${listVehicle}")
    }
}