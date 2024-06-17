package com.example.mygrocerystore.data.database

sealed class ThisResult <out R> private constructor() {
    object Loading : ThisResult<Nothing>()
    data class ErrorData(val data: String) : ThisResult<Nothing>()
    data class SuccessData<out T>(val data: T) : ThisResult<T>()
}