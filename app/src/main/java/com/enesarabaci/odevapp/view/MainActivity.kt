package com.enesarabaci.odevapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import com.enesarabaci.odevapp.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nav_view.setCheckedItem(R.id.odevler)

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.odevler) {
            val navController = findNavController(R.id.nav_host_fragment)
            navController.navigate(R.id.odevFragment)
        }

        if (item.itemId == R.id.dersler) {
            val navController = findNavController(R.id.nav_host_fragment)
            navController.navigate(R.id.derslerFragment)
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }else {
            super.onBackPressed()
        }
    }




}