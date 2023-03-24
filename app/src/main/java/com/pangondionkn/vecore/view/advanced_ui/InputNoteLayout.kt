package com.pangondionkn.vecore.view.advanced_ui

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.pangondionkn.vecore.R
import com.pangondionkn.vecore.databinding.LayoutInputnoteBinding

class InputNoteLayout: ConstraintLayout {
    private lateinit var mContext: Context
    private lateinit var binding: LayoutInputnoteBinding

    constructor(context: Context):super(context){
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet):super(context, attrs){
        init(context, attrs)
    }

    private fun init(context: Context, attributeSet:AttributeSet?){
        mContext = context

        binding = LayoutInputnoteBinding.bind(
            LayoutInflater.from(context)
                .inflate(R.layout.layout_inputnote, this, true)
        )

        binding.etReportNotes.addTextChangedListener(textWatcher)
    }

    private val textWatcher = object: TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {}

    }

    fun setTextHelper(text:String){
        binding.etReportNotes.hint = text
    }

    fun getText():String{
        return binding.etReportNotes.text.toString()
    }
}