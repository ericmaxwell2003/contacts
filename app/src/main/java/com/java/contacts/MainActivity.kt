package com.java.contacts

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

        // Setup navigation via NavigationUI with the BottomNavigationView
        navController = findNavController(R.id.nav_host)
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavigationView.setupWithNavController(navController)

        // Allows to treat settings as a top level destination and hides the up button.
        appBarConfiguration = AppBarConfiguration(setOf(R.id.contacts_list, R.id.settings))
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Hide the BottomNavigationView on the contact details destination.
        navController.addOnDestinationChangedListener { navController, newDestination, arguments ->
            when (newDestination.id) {
                R.id.contact_detail -> bottomNavigationView.visibility = View.GONE
                else -> bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }

    // Add navigate up support by overriding this method.
    // Letting the navController have first dibs at handling it, in the implementation.
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
