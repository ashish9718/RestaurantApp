package com.ashish.restaurantapp.ui.main.view.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.ashish.restaurantapp.databinding.ActivitySettingsBinding
import com.ashish.restaurantapp.ui.main.adapter.PicsAdapter
import com.ashish.restaurantapp.ui.main.viewmodel.UserViewModel
import com.ashish.restaurantapp.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_settings.*
@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    private val PICK_PROFILE_PIC = 1

    private var filePath: Uri? = null
    private lateinit var activitySettingsBinding: ActivitySettingsBinding
    lateinit var pic_type: String
    private lateinit var picsAdapter: PicsAdapter

    private val userViewModel: UserViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySettingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(activitySettingsBinding.root)
        supportActionBar?.hide()
        window.statusBarColor = Color.TRANSPARENT

        activitySettingsBinding.chooseProfilePic.setOnClickListener {
            pic_type = "profile_pic"
            launchGallery()
        }
        activitySettingsBinding.chooseProfileBg.setOnClickListener {
            pic_type = "profile_bg"
            launchGallery()
        }

        activitySettingsBinding.updateBtn.setOnClickListener {
            val userName = activitySettingsBinding.updatedName.text.toString()
            userViewModel.updateUserName(userName).observe(this) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        activitySettingsBinding.loadingMain.visibility = View.GONE
                        resource.data?.let {
                            Toasty.success(this, it, Toast.LENGTH_SHORT).show()
                        }
                    }
                    Status.LOADING -> {
                        activitySettingsBinding.loadingMain.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        activitySettingsBinding.loadingMain.visibility = View.GONE
                        Toasty.error(this, resource.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
        imageListObserver()

        swiperefreshlayout.setOnRefreshListener {
            imageListObserver()
            swiperefreshlayout.isRefreshing = false
        }

    }

    private fun imageListObserver() {
        userViewModel.getImages().observe(this) { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    activitySettingsBinding.loadingMain.visibility = View.GONE
                    it.data?.let {
                        picsAdapter = PicsAdapter(this, this, it,userViewModel)
                        activitySettingsBinding.imagesRecyclerview.adapter = picsAdapter
                        activitySettingsBinding.imagesRecyclerview.layoutManager =
                            GridLayoutManager(this, 3)
                        picsAdapter.notifyDataSetChanged()
                    }
                }
                Status.LOADING -> {
                    activitySettingsBinding.loadingMain.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    activitySettingsBinding.loadingMain.visibility = View.GONE
                    Toasty.error(this, it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }


    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PROFILE_PIC)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PROFILE_PIC && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                filePath = data.data
            }
            userViewModel.uploadImage(filePath!!, pic_type).observe(this) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        activitySettingsBinding.loadingMain.visibility = View.GONE
                        resource.data?.let {
                            Toasty.success(this, it, Toast.LENGTH_SHORT)
                                .show()
                            imageListObserver()
                        }
                    }
                    Status.LOADING -> {
                        activitySettingsBinding.loadingMain.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        activitySettingsBinding.loadingMain.visibility = View.GONE
                        Toasty.error(this, resource.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}