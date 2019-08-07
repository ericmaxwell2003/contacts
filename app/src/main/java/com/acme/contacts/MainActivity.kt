package com.acme.contacts

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host)
        bottomNavigationView = findViewById(R.id.bottom_nav_view)
        bottomNavigationView.setupWithNavController(navController)

        // Adds the Up Button to the Toolbar
        appBarConfiguration =
            AppBarConfiguration(setOf(R.id.contacts_list, R.id.settings))
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener {
                navController, newDestination, arguments ->
            when (newDestination.id) {
                R.id.contact_detail -> bottomNavigationView.visibility = View.GONE
                else -> bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }

    // Provides up navigation when the up button is clicked
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}
