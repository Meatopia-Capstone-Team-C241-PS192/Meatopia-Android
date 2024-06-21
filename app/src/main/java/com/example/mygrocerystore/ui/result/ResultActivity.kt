package com.example.mygrocerystore.ui.result

import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mygrocerystore.MainActivity
import com.example.mygrocerystore.R
import com.example.mygrocerystore.data.response.PredictResponse
import com.example.mygrocerystore.databinding.ActivityResultBinding
import com.example.mygrocerystore.ui.camera.CameraActivity
import com.google.gson.Gson

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAction()

        val supportAction = supportActionBar
        supportAction?.hide()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setUpAction() {
        val imageUri = intent.getStringExtra("IMAGE_URI")?.let { Uri.parse(it) }
        imageUri?.let {
            binding.imageViewResult.setImageURI(it)
        }

        val predictResponseJson = intent.getStringExtra("PREDICT_RESPONSE")
        val predictResponse = Gson().fromJson(predictResponseJson, PredictResponse::class.java)
        predictResponse?.data?.let {
            binding.resultPredectionText.text = it.prediction
        }

        binding.buttonBackInResult.setOnClickListener {
            Intent(this, CameraActivity::class.java).apply {
                val options = ActivityOptions.makeCustomAnimation(
                    this@ResultActivity,
                    R.anim.anim_none,
                    R.anim.anim_none
                )
                startActivity(this, options.toBundle())
                finish()
            }
        }
    }
}
