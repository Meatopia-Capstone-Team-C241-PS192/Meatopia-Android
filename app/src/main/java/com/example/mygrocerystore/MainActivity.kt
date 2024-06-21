package com.example.mygrocerystore

import HomeFragment
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.Fragment
import com.example.mygrocerystore.data.database.DataPreferences
import com.example.mygrocerystore.ui.login.LoginActivity
import com.example.mygrocerystore.ui.mycart.MyCartActivity
import com.example.mygrocerystore.ui.profile.DetailProfileActivity

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var preferences: DataPreferences
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)

        preferences = DataPreferences(this)

        val btnDrawer: ImageButton = findViewById(R.id.btn_drawer)
        btnDrawer.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            handleNavigationItemSelected(menuItem)
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        setNavHeaderData()
    }

    private fun setNavHeaderData() {
        val headerView = navigationView.getHeaderView(0)
        val nameTextView: TextView = headerView.findViewById(R.id.name_in_nav_header)
        val emailTextView: TextView = headerView.findViewById(R.id.email_in_nav_header)

        val user = preferences.getUser()
        user?.let {
            nameTextView.text = it.name
            emailTextView.text = it.email
        }
    }

    private fun handleNavigationItemSelected(item: MenuItem) {
        // Handle navigation view item clicks here
        when (item.itemId) {
            R.id.nav_home -> {
                replaceFragment(HomeFragment())
            }

            R.id.nav_user -> {
                startActivity(Intent(this, DetailProfileActivity::class.java))
            }

            R.id.nav_my_cart -> {
                startActivity(Intent(this, MyCartActivity::class.java))
            }

            R.id.nav_logout -> {
                preferences.clearLogin()
                startActivity(Intent(this, LoginActivity::class.java).apply {
                    finish()
                })
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_frame, fragment)
            .commit()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
