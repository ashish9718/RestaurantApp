package com.ashish.restaurantapp.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.ashish.restaurantapp.R
import com.ashish.restaurantapp.data.models.Location
import com.ashish.restaurantapp.data.models.Restaurant
import com.ashish.restaurantapp.data.models.RestaurantX
import com.ashish.restaurantapp.data.models.UserRating
import com.ashish.restaurantapp.data.repository.UserRepository
import com.ashish.restaurantapp.ui.main.view.activities.RestaurantDetailsActivity
import com.ashish.restaurantapp.databinding.ItemRestaurantBinding
import com.ashish.restaurantapp.ui.main.viewmodel.UserViewModel
import com.ashish.restaurantapp.utils.Status
import com.squareup.picasso.Picasso
import es.dmoral.toasty.Toasty


class ItemRestaurantAdapter(var restaurantList: List<Restaurant>?,var context: Context, private var lifecycleOwner: LifecycleOwner) :
    RecyclerView.Adapter<ItemRestaurantAdapter.CustomViewHolder>() {

    var addedToWishlist = false
    var userViewModel = UserViewModel(UserRepository())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemRestaurantBinding: ItemRestaurantBinding =
            ItemRestaurantBinding.inflate(layoutInflater, parent, false)
        return CustomViewHolder(itemRestaurantBinding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val res: RestaurantX = restaurantList!![position].restaurant
        val location: Location = res.location
        val userRating: UserRating = res.user_rating
        val rating: String = userRating.aggregate_rating

        holder.itemRestaurantBinding?.restaurant = res
        holder.itemRestaurantBinding?.location = location
        holder.itemRestaurantBinding?.executePendingBindings()
        holder.itemRestaurantBinding?.amountPerPerson?.text =
            res.average_cost_for_two.toString() + "₹ for 2 persons"
        holder.bind(res.thumb, rating, context, res.id)

        holder.itemRestaurantBinding?.wishlist?.setOnClickListener {
            if (!addedToWishlist) {
                addedToWishlist = true
                userViewModel.addRestaurantToWishList(res.id).observe(lifecycleOwner){ resource->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let {
                                Toasty.success(context, it, Toast.LENGTH_SHORT).show()
                            }
                        }
                        Status.LOADING -> {
                        }
                        Status.ERROR -> {
                            Toasty.error(context, resource.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                holder.itemRestaurantBinding!!.wishlist.setImageResource(R.drawable.bookmarked)
            } else {
                addedToWishlist = false
                userViewModel.removeRestaurantFromWishList(res.id).observe(lifecycleOwner){ resource->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let {
                                Toasty.success(context, it.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                        Status.LOADING -> {
                        }
                        Status.ERROR -> {
                            Toasty.error(context, resource.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                holder.itemRestaurantBinding!!.wishlist.setImageResource(R.drawable.bookmark)
            }
        }

    }

    override fun getItemCount(): Int {
        return restaurantList!!.size
    }

    class CustomViewHolder(private var binding: ItemRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var itemRestaurantBinding: ItemRestaurantBinding? = binding
        fun bind(url: String, rating: String, context: Context, id: String) {
            if (url.equals("")) {
                Picasso.get()
                    .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRCfj2cq6upd3FjyNV01elip8KIH4ek6B39lg&usqp=CAU")
                    .fit().into(itemRestaurantBinding!!.imageview)
            } else {
                Picasso.get().load(url).fit()
                    .into(itemRestaurantBinding!!.imageview)
            }

            val rate = rating.toFloat().toInt()
            if (rate == 0) {
                itemRestaurantBinding!!.smileRating.setRating(2)
            } else {
                itemRestaurantBinding!!.smileRating.setRating(rate)
            }
            itemView.setOnClickListener {
                val intent = Intent(context, RestaurantDetailsActivity::class.java)
                intent.putExtra("rest_id", id)
                context.startActivity(intent)
            }
        }
    }


}