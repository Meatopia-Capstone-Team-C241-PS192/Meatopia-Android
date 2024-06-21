package com.example.mygrocerystore.ui.login

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mygrocerystore.MainActivity
import com.example.mygrocerystore.R
import com.example.mygrocerystore.data.database.DataPreferences
import com.example.mygrocerystore.data.database.ThisResult
import com.example.mygrocerystore.data.modelfactory.ModelFactory
import com.example.mygrocerystore.data.response.LoginResponse
import com.example.mygrocerystore.data.response.LoginResult
import com.example.mygrocerystore.databinding.ActivityLoginBinding
import com.example.mygrocerystore.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var modelLogin: ViewModelLogin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        modelLogin = accommodating(this as AppCompatActivity)

        val actionBar = supportActionBar
        actionBar?.hide()

        setUpAction()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun accommodating(activity: AppCompatActivity): ViewModelLogin {
        val loginPreferences = DataPreferences(activity.application)
        val factory = ModelFactory.getInstance(activity.application, loginPreferences)
        return ViewModelProvider(activity, factory)[ViewModelLogin::class.java]
    }

    private fun setUpAction() {
        binding.buttonSignInInLogin.setOnClickListener {
            Toast.makeText(this@LoginActivity, "Wait", Toast.LENGTH_SHORT).show()
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                dataLoginVerification(email, password)
            }
        }

        binding.buttonSignUpInLogin.setOnClickListener {
            Intent(this, RegisterActivity::class.java).apply {
                val options = ActivityOptions.makeCustomAnimation(
                    this@LoginActivity,
                    R.anim.anim_none,
                    R.anim.anim_none
                )
                startActivity(this, options.toBundle())
            }
        }
    }

    private fun dataLoginVerification(email: String, password: String) {
        modelLogin.loginUser(email, password)
            .observe(this@LoginActivity) { result ->
                when (result) {
                    is ThisResult.SuccessData -> {
                        Toast.makeText(
                            this@LoginActivity,
                            getString(R.string.welcome),
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("TAG", "dataLoginVerification: ${result.data}")
                        navigateToHome(result.data)
                    }
                    is ThisResult.ErrorData -> {
                        Toast.makeText(
                            this@LoginActivity,
                            getString(R.string.Something_wrong),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {}
                }
            }
    }

    private fun navigateToHome(data: LoginResponse) {
        saveDataLogin(data)
        Intent(this, MainActivity::class.java).apply {
            val options = ActivityOptions.makeCustomAnimation(
                this@LoginActivity,
                R.anim.slide_out_right,
                R.anim.anim_none
            )
            startActivity(this, options.toBundle())
            finish()
        }
    }

    private fun saveDataLogin(data: LoginResponse) {
        val dataPreference = DataPreferences(this)
        val loginModel = LoginResult(
            name = data.name,
            id = data.id,
            token = data.token,
            role = data.role,
            email = data.email
        )
        Log.d("TAG", "saveDataLogin: $loginModel")
        dataPreference.setLogin(loginModel)
    }


}
