package com.example.applicationmeteo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.applicationmeteo.fragments.HomeFragment
import com.example.applicationmeteo.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnNavigationItemReselectedListener {
            when(it.itemId) {
                R.id.home_page -> {
                    loadFragment(HomeFragment(this))
                }
                R.id.home_location -> {
                    loadFragment(SearchFragment(this))
                }
            }
        }

        // Inject the HomeFragment
        loadFragment(HomeFragment(this))
    }

    private fun loadFragment(fragment: Fragment) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(R.id.page_container, fragment)
    transaction.addToBackStack(null)
    transaction.commit()

    }

}