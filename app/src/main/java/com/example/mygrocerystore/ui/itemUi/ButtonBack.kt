package com.example.mygrocerystore.ui.itemUi

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import com.example.mygrocerystore.R

class ButtonBack (context: Context, attrs: AttributeSet?
) : AppCompatButton(context, attrs) {

    private var paddingLeft = 0
    private var paddingRight = 0
    private val drawable =
        AppCompatResources.getDrawable(context, R.drawable.backarrow)

    init {
        paddingLeft = compoundPaddingLeft
        paddingRight = compoundPaddingRight
        setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawable?.let {
            val drawableWidth = it.intrinsicWidth
            val drawableHeight = it.intrinsicHeight

            val left = width - paddingRight - drawableWidth
            val top = (height - drawableHeight) / 2

            it.setBounds(left, top, left + drawableWidth, top + drawableHeight)
            it.draw(canvas)
        }
    }
}
