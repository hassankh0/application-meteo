package com.example.applicationmeteo

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.applicationmeteo.fragments.HomeFragment
import com.example.applicationmeteo.fragments.WeekFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inject the HomeFragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.page_container, HomeFragment(this))
        transaction.addToBackStack(null)
        transaction.commit()
    }



}