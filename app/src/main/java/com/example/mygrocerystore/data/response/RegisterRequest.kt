package com.example.mygrocerystore.data.response

data class RegisterRequest(
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val password: String,
    val confPassword: String,
    val role: String
)