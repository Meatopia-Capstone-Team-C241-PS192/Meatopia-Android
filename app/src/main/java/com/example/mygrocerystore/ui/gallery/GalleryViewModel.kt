package com.example.mygrocerystore.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GalleryViewModel : ViewModel() {
    private val mText = MutableLiveData<String>()

    init {
        mText.value = "This is gallery fragment"
    }

    val text: LiveData<String>
        get() = mText
}