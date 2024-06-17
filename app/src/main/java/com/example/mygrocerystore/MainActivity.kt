package com.example.mygrocerystore

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.Fragment
import com.example.mygrocerystore.ui.home.HomeFragment
import com.example.mygrocerystore.ui.login.LoginActivity
import com.example.mygrocerystore.ui.mycart.MyCartActivity
import com.example.mygrocerystore.ui.ordermeat.OderHistoryActivity
import com.example.mygrocerystore.ui.profile.DetailProfileActivity

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)

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
            R.id.nav_order_history ->{
                startActivity(Intent(this, OderHistoryActivity::class.java))
            }
            R.id.nav_logout -> {
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
