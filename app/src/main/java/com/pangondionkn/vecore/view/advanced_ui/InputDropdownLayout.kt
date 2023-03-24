package com.pangondionkn.vecore.view.advanced_ui

import android.content.Context
import android.hardware.display.DisplayManager
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.pangondionkn.vecore.R
import com.pangondionkn.vecore.databinding.LayoutPopupDropdownBinding
import com.pangondionkn.vecore.databinding.LayoutSpinnerBinding
import com.pangondionkn.vecore.model.data_class.DataVehicleResponse
import com.pangondionkn.vecore.view.adapter.ItemDropdownAdapter

class InputDropdownLayout: ConstraintLayout {
    private lateinit var mContext: Context
    private lateinit var binding: LayoutSpinnerBinding

    private var dropdownListener: DropdownListener?= null
    private var listVehicle: ArrayList<DataVehicleResponse> = ArrayList()
    private lateinit var popupBinding: LayoutPopupDropdownBinding
    private lateinit var popUpWindow: PopupWindow
    private var isDropdownShown: Boolean = false
    private lateinit var itemDropdownAdapter: ItemDropdownAdapter
    private var customListener: ItemDropdownAdapter.CustomListener?= null

    constructor(context: Context):super(context){
        init(context, null)
    }

    constructor(context: Context,attributeSet: AttributeSet): super(context, attributeSet){
        init(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int):super(context, attributeSet, defStyleAttr){
        init(context, attributeSet)
    }

    private fun init(context: Context, attributeSet: AttributeSet?){
        mContext = context

        binding = LayoutSpinnerBinding.bind(
            LayoutInflater.from(mContext)
                .inflate(R.layout.layout_spinner, this, true)
        )

        binding.clSpinner.setOnClickListener {
            dropdownListener?.onClickInput()
            if(listVehicle.size > 0){
                binding.spReportForm.performClick()
            }
            isDropdownShown = !isDropdownShown
        }
        val popUpView = LayoutInflater.from(mContext).inflate(R.layout.layout_popup_dropdown, null)
        popupBinding = LayoutPopupDropdownBinding.bind(popUpView)
        val displayMetrics = DisplayMetrics()
        val displayService = mContext.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager

        displayService.getDisplay(Display.DEFAULT_DISPLAY).getRealMetrics(displayMetrics)

        popUpWindow = PopupWindow(
            popUpView,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            true
        )

        popUpWindow.setOnDismissListener {
            dropdownListener?.onDismissPopUp()
        }
        itemDropdownAdapter = ItemDropdownAdapter(listVehicle, customListener)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        popUpWindow.width = w
        super.onSizeChanged(w, h, oldw, oldh)
    }

    private fun showPopUp(){
        popUpWindow.showAsDropDown(binding.root,0,10)
    }

    private fun dismissPopUp(){
        popUpWindow.dismiss()
    }

    fun setText(field: String){
        binding.tvSelectedItem.text = field
    }

    fun setVisibilityProgressBar(visible: Boolean){
        when(visible){
            true ->{
                binding.pbReportForm.visibility = View.VISIBLE
            }
            false ->{
                binding.pbReportForm.visibility = View.GONE
            }
        }
    }

    fun setHint(hint:String){
        binding.tvSelectedItem.text = hint
        binding.tvSelectedItem.setTextColor(ContextCompat.getColor(context, R.color.colorGreyHard))
    }

    private fun setListener(listener: DropdownListener){
        dropdownListener = listener
    }

    interface DropdownListener{
        fun onClickInput(){}
        fun onItemSelected(position: Int, item: String){}
        fun onDismissPopUp(){}
    }
}