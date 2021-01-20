package com.ashish.restaurantapp.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ashish.restaurantapp.data.models.CuisineList
import com.ashish.restaurantapp.ui.main.view.activities.MainActivity
import com.ashish.restaurantapp.ui.main.view.activities.ResultActivity
import com.ashish.restaurantapp.databinding.CuisineItemBinding
import com.ashish.restaurantapp.ui.main.view.fragments.HomeFragment

class CuisineAdapter(context: Context, body: CuisineList): RecyclerView.Adapter<CuisineAdapter.ViewHolder>(){

    var context = context
    private var cuisineList = body.cuisines

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cuisineItemBinding: CuisineItemBinding =
            CuisineItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(cuisineItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cuisineX = cuisineList[position].cuisine

        holder.cuisineItemBinding?.cuisine   = cuisineX
        holder.itemView.setOnClickListener {
            val intent = Intent (context, ResultActivity::class.java)
            intent.putExtra("cui_id",cuisineX.cuisine_id)
            intent.action = "cuisine"
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        if(HomeFragment.cuisinesExpanded) {
            return cuisineList.size
        }else{
            return 6
        }
    }

    class ViewHolder(binding: CuisineItemBinding):RecyclerView.ViewHolder(binding.root) {

        var cuisineItemBinding: CuisineItemBinding? = binding
    }
}