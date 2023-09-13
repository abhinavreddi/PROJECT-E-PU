package com.example.e_pu


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val supportFragment = SupportFragment()
    private val profileFragment = ProfileFragment()

    private lateinit var activeFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, profileFragment, "profile").hide(profileFragment)// immediately hiding the profileFragment by calling the hide method on it. This means that when the fragments are initially added, the profileFragment will be hidden from view.
            add(R.id.fragment_container, supportFragment, "dashboard").hide(supportFragment)
            add(R.id.fragment_container, homeFragment, "home")
        }.commit()

        activeFragment = homeFragment

        navView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> showFragment(homeFragment)
                R.id.navigation_dashboard -> showFragment(supportFragment)
                R.id.navigation_profile -> showFragment(profileFragment)
            }
            true// you're returning true to indicate that the event has been successfully handled and no further action is needed.
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().hide(activeFragment).show(fragment)
            .commit()  // responsible for managinging fragment visibility
        activeFragment = fragment // make the activefragment as current fragment
    }

}