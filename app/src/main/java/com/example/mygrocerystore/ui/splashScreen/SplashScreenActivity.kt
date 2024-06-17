package com.example.mygrocerystore.ui.splashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mygrocerystore.MainActivity
import com.example.mygrocerystore.R
import com.example.mygrocerystore.data.database.DataPreferences
import com.example.mygrocerystore.data.response.LoginResult
import com.example.mygrocerystore.databinding.ActivitySplashScreenBinding
import com.example.mygrocerystore.ui.login.LoginActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var dataLoginPreferences: DataPreferences
    private lateinit var loginModel: LoginResult
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSkip()
        setUpAction()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setSkip() {
        dataLoginPreferences = DataPreferences(this)
        loginModel = dataLoginPreferences.getLogin()
        Handler(Looper.getMainLooper()).postDelayed({
            if (loginModel.name != null && loginModel.role != null && loginModel.id != null) {
                Intent(this, MainActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            } else {

                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
            }
        }, _DURATION)
    }

    private fun setUpAction() {
        binding.buttonGetStarted.setOnClickListener {
            Intent(this, LoginActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }
    }

    companion object {
        private const val _DURATION = 3000L
    }

}