package com.ashish.restaurantapp.ui.main.view.activities

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashish.restaurantapp.data.repository.UserRepository
import com.ashish.restaurantapp.ui.main.adapter.WishlistAdapter
import com.ashish.restaurantapp.databinding.ActivityWishlistBinding
import com.ashish.restaurantapp.ui.base.UserViewModelFactory
import com.ashish.restaurantapp.ui.main.viewmodel.UserViewModel
import com.ashish.restaurantapp.utils.Status
import es.dmoral.toasty.Toasty

class WishlistActivity : AppCompatActivity() {

    private val TAG = "WishlistActivity"
    private lateinit var activityWishlistBinding: ActivityWishlistBinding
    private lateinit var wishlistAdapter: WishlistAdapter

    private lateinit var userViewModel: UserViewModel

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityWishlistBinding = ActivityWishlistBinding.inflate(layoutInflater)
        setContentView(activityWishlistBinding.root)
        supportActionBar?.hide()
        window.statusBarColor = Color.TRANSPARENT

        val repository = UserRepository()
        val factory = UserViewModelFactory(repository)
        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        activityWishlistBinding.wishlistLoading.visibility = View.VISIBLE

        userViewModel.getWishlist().observe(this) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    activityWishlistBinding.wishlistLoading.visibility = View.GONE
                    resource.data?.let {
                        if (it.isNotEmpty()) {
                            activityWishlistBinding.wishlistTv.visibility = View.GONE
                            wishlistAdapter = WishlistAdapter(it, this,this)
                            activityWishlistBinding.wishlistRecyclerview.layoutManager =
                                LinearLayoutManager(applicationContext)
                            activityWishlistBinding.wishlistRecyclerview.adapter = wishlistAdapter
                            wishlistAdapter.notifyDataSetChanged()
                        } else {
                            activityWishlistBinding.wishlistTv.visibility = View.VISIBLE
                        }
                    }
                }
                Status.LOADING -> {
                    activityWishlistBinding.wishlistTv.visibility = View.GONE
                    activityWishlistBinding.wishlistLoading.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    activityWishlistBinding.wishlistTv.visibility = View.GONE
                    activityWishlistBinding.wishlistLoading.visibility = View.GONE
                    Toasty.error(this, resource.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    wishlistAdapter.delete(viewHolder.adapterPosition)
                    Log.d(TAG, "onSwiped: " + viewHolder.adapterPosition)
                    wishlistAdapter.notifyDataSetChanged()
                }

            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(activityWishlistBinding.wishlistRecyclerview)
    }

}