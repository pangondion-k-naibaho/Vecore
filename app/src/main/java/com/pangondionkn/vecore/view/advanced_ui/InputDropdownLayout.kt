package com.pangondionkn.vecore.view.advanced_ui

import android.content.Context
import android.hardware.display.DisplayManager
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.*
import android.widget.PopupWindow
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.pangondionkn.vecore.R
import com.pangondionkn.vecore.databinding.LayoutPopupDropdownBinding
import com.pangondionkn.vecore.databinding.LayoutSpinnerBinding
import com.pangondionkn.vecore.model.Utils.EXTENSION.Companion.convertDpToPx
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
                if(!popUpWindow.isShowing){
                    showPopUp()
                }else{
                    dismissPopUp()
                }
            }
            isDropdownShown = !isDropdownShown
        }
        binding.tvSelectedItem.setOnClickListener{
            dropdownListener?.onClickInput()
            if(listVehicle.size > 0){
                binding.spReportForm.performClick()
                if(!popUpWindow.isShowing){
                    showPopUp()
                }else{
                    dismissPopUp()
                }
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

    fun Spinner.setScrollable(listSize:Int){
        try {
            val popup = Spinner::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true

            // Get private mPopup member variable and try cast to ListPopupWindow
            val popupWindow = popup.get(this) as android.widget.ListPopupWindow

            // Set popupWindow height to 500px
            if (listSize >= 5) popupWindow.height = convertDpToPx(250)
        } catch (e: NoClassDefFoundError) {
            // silently fail...
        } catch (e: ClassCastException) {
        } catch (e: NoSuchFieldException) {
        } catch (e: IllegalAccessException) {
        }
    }

    fun selectedType(item: DataVehicleResponse){
        setText(item.type)
    }

    fun setData(list: ArrayList<DataVehicleResponse>){
        this.listVehicle = list
        itemDropdownAdapter.updateChecked(list[0])
        if(list.size>0) setText(list[0].type)
        itemDropdownAdapter = ItemDropdownAdapter(list, object: ItemDropdownAdapter.CustomListener{
            override fun onClick(customItem: DataVehicleResponse, position: Int) {
                itemDropdownAdapter.updateChecked(customItem)
                selectedType(customItem)
                dismissPopUp()
                dropdownListener?.onItemSelected(position, customItem.type)
            }


        })
        popupBinding.rvItem.apply {
            layoutManager = LinearLayoutManager(context.applicationContext)
            adapter = itemDropdownAdapter
            layoutParams.height = when(itemDropdownAdapter.data.size < 5){
                true ->{
                    ViewGroup.LayoutParams.WRAP_CONTENT
                }
                false ->{
                    400
                }
            }
        }
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

    fun setHint(hint:String){
        binding.tvSelectedItem.text = hint
        binding.tvSelectedItem.setTextColor(ContextCompat.getColor(context, R.color.colorGreyHard))
    }

    fun setListener(listener: DropdownListener){
        dropdownListener = listener
    }

    fun setAdapter(adapter: ItemDropdownAdapter){
        itemDropdownAdapter = adapter
    }

    interface DropdownListener{
        fun onClickInput(){}
        fun onItemSelected(position: Int, item: String){}
        fun onDismissPopUp(){}
    }
}