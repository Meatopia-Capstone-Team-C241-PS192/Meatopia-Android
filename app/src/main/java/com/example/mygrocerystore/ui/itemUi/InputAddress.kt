package com.example.mygrocerystore.ui.itemUi

import android.content.Context
<<<<<<< HEAD
import android.text.Editable
=======
import android.graphics.Canvas
import android.text.Editable
import android.text.InputType
>>>>>>> origin/master
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.example.mygrocerystore.R
<<<<<<< HEAD

class InputAddress : AppCompatEditText {

    private var isAddressValid = true

    private val addressRegex = Regex("[a-zA-Z0-9 ,.-]+")

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = s.toString()
                isAddressValid = input.matches(addressRegex)
                if (!isAddressValid) {
                    error = context.getString(R.string.addrs_example)
                } else {
                    error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
=======
import com.example.mygrocerystore.utils.Validation

class InputAddress : AppCompatEditText {
    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty() && Validation.isInvalidInput(s)) error = context.getString(R.string.addrs_example)
            }
>>>>>>> origin/master
        })
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }
<<<<<<< HEAD
}
=======

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        inputType = InputType.TYPE_CLASS_PHONE or InputType.TYPE_CLASS_TEXT
    }
}
>>>>>>> origin/master
