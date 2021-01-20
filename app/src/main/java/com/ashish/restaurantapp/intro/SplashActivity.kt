package com.ashish.restaurantapp.intro

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.ashish.restaurantapp.R
import com.ashish.restaurantapp.data.repository.UserRepository
import com.ashish.restaurantapp.ui.main.view.activities.MainActivity
import com.ashish.restaurantapp.ui.main.viewmodel.UserViewModel
import com.ashish.restaurantapp.utils.Status
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_image_preview.*
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private var user: FirebaseUser? = null
    lateinit var userViewModel: UserViewModel

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        window.statusBarColor = Color.TRANSPARENT
        userViewModel = UserViewModel(UserRepository())

        try_again.setOnClickListener{
            checkConnection()
        }

    }

    override fun onStart() {
        super.onStart()
        user = Firebase.auth.currentUser
        Handler().postDelayed({
            checkConnection()
        }, 3000)
    }

    private fun checkConnection(){
        val manager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo

        if (networkInfo != null){
            loading.visibility = View.GONE
            try_again.visibility = View.GONE
            app_img.visibility = View.VISIBLE
            Splashview.setBackgroundColor(Color.BLACK)
            checkUser()
        }else{
            loading.visibility = View.VISIBLE
            try_again.visibility = View.VISIBLE
            Splashview.setBackgroundColor(Color.WHITE)
            app_img.visibility = View.GONE
        }
    }

    private fun checkUser() {
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
            userViewModel.getUserDetails().observe(this){ resource->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let {
                            Toasty.success(this, "Welcome ${it.name} !", Toast.LENGTH_SHORT).show()
                        }
                    }
                    Status.LOADING -> {
                    }
                    Status.ERROR -> {
                        Toasty.error(this, resource.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
            finish()
        } else {
            startActivity(Intent(this, IntroSliderActivity::class.java))
            finish()
        }
    }
}