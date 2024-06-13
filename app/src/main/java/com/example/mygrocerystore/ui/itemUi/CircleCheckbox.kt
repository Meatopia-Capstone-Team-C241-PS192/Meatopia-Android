package com.example.mygrocerystore.ui.itemUi

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckBox
import com.example.mygrocerystore.R

class CircleCheckbox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatCheckBox(context, attrs, defStyleAttr) {

    init {
        buttonDrawable = null
        setBackgroundResource(R.drawable.circle_checkbox)
        val padding = resources.getDimensionPixelSize(R.dimen.custom_checkbox_padding)
        setPadding(padding, padding, padding, padding)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}