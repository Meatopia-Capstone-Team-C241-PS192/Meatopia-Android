package com.example.mygrocerystore.ui.register


import android.widget.Button
import android.widget.EditText


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

import com.example.mygrocerystore.R
import com.example.mygrocerystore.data.database.DataPreferences
import com.example.mygrocerystore.data.database.ThisResult
import com.example.mygrocerystore.data.modelfactory.ModelFactory
import com.example.mygrocerystore.databinding.ActivityRegisterBinding
import com.example.mygrocerystore.ui.login.LoginActivity




class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModelRegister: ModelRegister
    private lateinit var edLoginName: EditText
    private lateinit var edLoginEmail: EditText
    private lateinit var edLoginPhoneNumber: EditText
    private lateinit var edLoginAddress: EditText
    private lateinit var edLoginPassword: EditText
    private lateinit var edLoginConfirmPassword: EditText
    private lateinit var myButtonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataPreferences = DataPreferences(application)
        val factory = ModelFactory.getInstance(application, dataPreferences)
        viewModelRegister = ViewModelProvider(this, factory).get(ModelRegister::class.java)

        // Bind views
        edLoginName = binding.edLoginName
        edLoginEmail = binding.edLoginEmail
        edLoginPhoneNumber = binding.edLoginPhoneNumber
        edLoginAddress = binding.edLoginAddress
        edLoginPassword = binding.edLoginPassword
        edLoginConfirmPassword = binding.edLoginConfirmPassword
        myButtonRegister = binding.buttonSignUpInRegister

        setupAction()
        setupAnimation()
    }

    private fun setupAnimation() {
        // Implementasi animasi jika diperlukan
    }

    private fun setupAction() {
        // Setup actions for text changes, button clicks, etc.
        edLoginPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        edLoginEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.buttonBackInSignUp.setOnClickListener {
            Intent(this, LoginActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }

        myButtonRegister.setOnClickListener {
            Toast.makeText(this, getString(R.string.Loading), Toast.LENGTH_SHORT).show()
            val name = edLoginName.text.toString()
            val email = edLoginEmail.text.toString()
            val phoneNumber = edLoginPhoneNumber.text.toString()
            val address = edLoginAddress.text.toString()
            val password = edLoginPassword.text.toString()
            val confirmPassword = edLoginConfirmPassword.text.toString()

            if (validateInputs(name, email, phoneNumber, address, password, confirmPassword)) {
                registerDataSend(name, email, phoneNumber, address, password, confirmPassword)
            } else {
                Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setMyButtonEnable() {
        val name = edLoginName.text.toString()
        val email = edLoginEmail.text.toString()
        val phoneNumber = edLoginPhoneNumber.text.toString()
        val address = edLoginAddress.text.toString()
        val password = edLoginPassword.text.toString()
        val confirmPassword = edLoginConfirmPassword.text.toString()

        myButtonRegister.isEnabled = (
                name.isNotEmpty() &&
                        email.isNotEmpty() &&
                        phoneNumber.isNotEmpty() &&
                        address.isNotEmpty() &&
                        password.isNotEmpty() &&
                        confirmPassword.isNotEmpty()
                )
    }

    private fun validateInputs(name: String, email: String, phoneNumber: String, address: String, password: String, confirmPassword: String): Boolean {
        return (
                name.isNotEmpty() &&
                        email.isNotEmpty() &&
                        phoneNumber.isNotEmpty() &&
                        address.isNotEmpty() &&
                        password.isNotEmpty() &&
                        confirmPassword.isNotEmpty()
                )
    }

    private fun registerDataSend(name: String, email: String, phoneNumber: String, address: String, password: String, confirmPassword: String) {
        viewModelRegister.registerUser(name, email, phoneNumber, address, password, confirmPassword).observe(this) { result ->
            when (result) {
                is ThisResult.SuccessData -> {
                    Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show()
                    navigateToLogin()
                }
                is ThisResult.ErrorData -> {
                    Toast.makeText(this, getString(R.string.Something_wrong), Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun navigateToLogin() {
        Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show()
        Intent(this, LoginActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }
}




