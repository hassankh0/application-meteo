package com.example.applicationmeteo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.applicationmeteo.fragments.HomeFragment
import com.example.applicationmeteo.fragments.SearchFragment
import com.example.applicationmeteo.fragments.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    private var degreeTemp = "celsius"
    private var degreeVent = "kmh"

    fun getDegreeTemp(): String {
        return this.degreeTemp
    }
    fun getDegreeVent(): String {
        return this.degreeVent
    }
    fun setDegreeTemp(variable: String) {
        this.degreeTemp = variable
    }
    fun setDegreeVent(variable: String){
        this.degreeVent = variable
    }


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
                R.id.home_setting -> {
                    loadFragment(SettingFragment(this))
                }
            }
        }

        loadFragment(HomeFragment(this))

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    private fun loadFragment(fragment: Fragment) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(R.id.page_container, fragment)
    transaction.addToBackStack(null)
    transaction.commit()

    }

}