package com.ashish.restaurantapp.ui.main.view.activities

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.ashish.restaurantapp.R
import com.ashish.restaurantapp.ui.main.viewmodel.UserViewModel
import com.ashish.restaurantapp.utils.Status
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_image_preview.*

@AndroidEntryPoint
class ImagePreviewActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_preview)
        supportActionBar?.hide()
        window.statusBarColor = Color.TRANSPARENT

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        loading_anim.visibility = View.VISIBLE

        val url = intent.getStringExtra("url")!!
        Picasso.get().load(url).fit().into(image_preview)

        loading_anim.visibility = View.GONE

        profile_pic.setOnClickListener {
            userViewModel.updateProfilePic(url).observe(this) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        loading_anim.visibility = View.GONE
                        resource.data?.let {
                            Toasty.success(this, it, Toast.LENGTH_SHORT).show()
                        }
                    }
                    Status.LOADING -> {
                        loading_anim.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        loading_anim.visibility = View.GONE
                        Toasty.error(this, resource.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        profile_bg_pic.setOnClickListener {
            userViewModel.updateProfileBgPic(url).observe(this) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        loading_anim.visibility = View.GONE
                        resource.data?.let {
                            Toasty.success(this, it, Toast.LENGTH_SHORT).show()
                        }
                    }
                    Status.LOADING -> {
                        loading_anim.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        loading_anim.visibility = View.GONE
                        Toasty.error(this, resource.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }


    }
}