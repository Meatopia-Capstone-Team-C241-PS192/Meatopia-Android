package com.example.mygrocerystore.ui.itemUi

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatEditText
import com.example.mygrocerystore.R

class InputSearch : AppCompatEditText {


    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        background = AppCompatResources.getDrawable(context, R.drawable.searchbar)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        gravity = Gravity.CENTER
    }
}