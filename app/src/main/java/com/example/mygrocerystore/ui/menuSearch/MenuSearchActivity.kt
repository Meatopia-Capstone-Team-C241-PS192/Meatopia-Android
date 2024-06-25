package com.example.mygrocerystore.ui.menuSearch

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mygrocerystore.MainActivity
import com.example.mygrocerystore.R
import com.example.mygrocerystore.databinding.ActivityMenuSearchBinding

class MenuSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAction()

        supportActionBar?.hide()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setUpAction() {
        binding.buttonBackInMenuSearch.setOnClickListener {
            Intent(this, MainActivity::class.java).apply {
                val options = ActivityOptions.makeCustomAnimation(
                    this@MenuSearchActivity,
                    R.anim.anim_none,
                    R.anim.anim_none
                )
                startActivity(this, options.toBundle())
            }
        }
    }
}
