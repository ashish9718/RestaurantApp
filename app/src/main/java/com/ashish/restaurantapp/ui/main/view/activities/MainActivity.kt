package com.ashish.restaurantapp.ui.main.view.activities

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.ashish.restaurantapp.R
import com.ashish.restaurantapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        window.statusBarColor = Color.TRANSPARENT

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationViewLinear.setNavigationChangeListener { view: View, i: Int ->
            when (i) {
                0 -> {
                    when (navController.currentDestination?.label) {
                        "fragment_profile" -> {
                            navController.navigate(R.id.action_profileFragment_to_homeFragment)
                        }
                        "fragment_cart" -> {
                            navController.navigate(R.id.action_cartFragment_to_homeFragment)
                        }
                    }
                }
                1 -> {
                    when (navController.currentDestination?.label) {
                        "fragment_home" -> {
                            navController.navigate(R.id.action_homeFragment_to_cartFragment)
                        }
                        "fragment_profile" -> {
                            navController.navigate(R.id.action_profileFragment_to_cartFragment)
                        }
                    }
                }
                2 -> {
                    when (navController.currentDestination?.label) {
                        "fragment_home" -> {
                            navController.navigate(R.id.action_homeFragment_to_profileFragment)
                        }
                        "fragment_cart" -> {
                            navController.navigate(R.id.action_cartFragment_to_profileFragment)
                        }
                    }
                }
            }
        }

    }


}




