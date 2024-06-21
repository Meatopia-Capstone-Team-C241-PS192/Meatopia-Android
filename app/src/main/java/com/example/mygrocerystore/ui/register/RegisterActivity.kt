package com.example.mygrocerystore.ui.register

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
    private lateinit var viewModelRegister: ViewModelRegister

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataPreferences = DataPreferences(application)
        val factory = ModelFactory.getInstance(application, dataPreferences)
        viewModelRegister = ViewModelProvider(this, factory).get(ViewModelRegister::class.java)

        setupAction()
    }

    private fun setupAction() {
        binding.buttonBackInSignUp.setOnClickListener {
            Intent(this, LoginActivity::class.java).apply {
                val options = ActivityOptions.makeCustomAnimation(
                    this@RegisterActivity,
                    R.anim.anim_none,
                    R.anim.anim_none
                )
                startActivity(this, options.toBundle())
                finish()
            }
        }

        binding.buttonSignUpInLogin.setOnClickListener {
            Intent(this, LoginActivity::class.java).apply {
                val options = ActivityOptions.makeCustomAnimation(
                    this@RegisterActivity,
                    R.anim.anim_none,
                    R.anim.anim_none
                )
                startActivity(this, options.toBundle())
            }
        }

        binding.buttonSignUpInRegister.setOnClickListener {
            val name = binding.edLoginName.text.toString()
            val email = binding.edLoginEmail.text.toString()
            val phoneNumber = binding.edLoginPhoneNumber.text.toString()
            val address = binding.edLoginAddress.text.toString()
            val password = binding.edLoginPassword.text.toString()
            val confPassword = binding.edLoginConfirmPassword.text.toString()
            val role = "user"
            if (name.isNotEmpty() && email.isNotEmpty() && phoneNumber.isNotEmpty() && address.isNotEmpty() && password.isNotEmpty() && confPassword.isNotEmpty()) {
                registerDataSend(name, email, phoneNumber, address, password, confPassword, role)
            }
        }
    }

    private fun registerDataSend(
        name: String,
        email: String,
        phoneNumber: String,
        address: String,
        password: String,
        confPassword: String,
        role: String
    ) {
        Log.d(
            "RegisterActivity",
            "onCreate: $name $email $phoneNumber $address $password $confPassword $role"
        )
        viewModelRegister.registerUser(
            name,
            email,
            phoneNumber,
            address,
            password,
            confPassword,
            role
        )
            .observe(this) { result ->
                when (result) {
                    is ThisResult.SuccessData -> {
                        Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show()
                        navigateToLogin()
                    }

                    is ThisResult.ErrorData -> {
                        Toast.makeText(
                            this,
                            getString(R.string.Something_wrong),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {}
                }
            }
    }

    private fun navigateToLogin() {
        Intent(this, LoginActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }
}
