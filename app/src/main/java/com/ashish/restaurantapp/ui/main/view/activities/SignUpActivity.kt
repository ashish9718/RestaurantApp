package com.ashish.restaurantapp.ui.main.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ashish.restaurantapp.data.repository.AuthRepository
import com.ashish.restaurantapp.databinding.ActivitySignUpBinding
import com.ashish.restaurantapp.ui.base.AuthViewModelFactory
import com.ashish.restaurantapp.ui.main.viewmodel.AuthViewModel
import com.ashish.restaurantapp.utils.Resource
import com.ashish.restaurantapp.utils.Status
import es.dmoral.toasty.Toasty

class SignUpActivity : AppCompatActivity() {
    private val TAG = "SignUpActivity"

    lateinit var binding: ActivitySignUpBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val repository = AuthRepository()
        val factory = AuthViewModelFactory(repository)
        authViewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = authViewModel
        binding.lifecycleOwner = this

        binding.signUpBtn.setOnClickListener {
            authViewModel.registerBtnClick().observe(this) { it ->
                if (it is String) {
                    Toasty.warning(this, it, Toast.LENGTH_SHORT)
                        .show()
                } else if (it is Resource<*>) {
                    when (it.status) {
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            it.data?.let {
                                if(it.toString() == "logged in!") {
                                    val intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                    Toasty.success(this, it.toString(), Toast.LENGTH_SHORT).show()
                                }else{
                                    Toasty.error(this, it.toString(), Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }
                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        Status.ERROR -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            Toasty.error(this, it.message.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }

        }

        binding.signUpSignin.setOnClickListener {
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}