package com.ashish.restaurantapp.ui.main.view.activities

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.ashish.restaurantapp.R
import com.ashish.restaurantapp.data.models.Location
import com.ashish.restaurantapp.data.models.RestaurantX
import com.ashish.restaurantapp.databinding.ActivityRestaurantDetailsBinding
import com.ashish.restaurantapp.ui.main.viewmodel.RestaurantViewModel
import com.ashish.restaurantapp.utils.Status
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty

@AndroidEntryPoint
class RestaurantDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantDetailsBinding
    private val TAG = "RestaurantDetailsActivity"

    private val restaurantViewModel: RestaurantViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_restaurant_details)
        supportActionBar?.hide()
        window.statusBarColor = Color.TRANSPARENT

        val id: Int = intent.getStringExtra("rest_id").toString().toInt()

        restaurantViewModel.getRestaurantDetail(id).observe(this) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    binding.loadingAnim.visibility = View.GONE
                    resource.data?.let {
                        setData(it)
                    }
                }
                Status.LOADING -> {
                    binding.loadingAnim.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.loadingAnim.visibility = View.GONE
                    Toasty.error(this, resource.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun setData(restaurant: RestaurantX?) {
        val location: Location? = restaurant?.location
        val cuisines: String? = restaurant?.cuisines
        val cuisine: List<String>? = cuisines?.split(",")?.map { it -> it.trim() }

        binding.rest = restaurant
        binding.location = location
        binding.cuisine = cuisine
        binding.executePendingBindings()

        binding.restCostForTwo.text =
            "Cost for two - ₹" + (restaurant?.average_cost_for_two) + " (approx.)"
        binding.restAvgcost.text =
            "₹" + (restaurant?.average_cost_for_two) + " for two people (approx.)"

        if (restaurant?.thumb.equals("")) {
            Picasso.get()
                .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRCfj2cq6upd3FjyNV01elip8KIH4ek6B39lg&usqp=CAU")
                .fit().into(binding.restImage)
        } else {
            Picasso.get().load(restaurant?.thumb).fit()
                .into(binding.restImage)
        }
    }
}