package com.ashish.restaurantapp.ui.main.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.HorizontalScrollView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashish.restaurantapp.R
import com.ashish.restaurantapp.data.api.ApiHelper
import com.ashish.restaurantapp.data.api.RetrofitInstance
import com.ashish.restaurantapp.databinding.ActivityMainBinding
import com.ashish.restaurantapp.data.models.*
import com.ashish.restaurantapp.data.repository.MainRepository
import com.ashish.restaurantapp.ui.base.MainViewModelFactory
import com.ashish.restaurantapp.ui.main.adapter.*
import com.ashish.restaurantapp.ui.main.viewmodel.MainViewModel
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.*

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




