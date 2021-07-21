package com.ashish.restaurantapp.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.ashish.restaurantapp.R
import com.ashish.restaurantapp.data.models.Establishment
import com.ashish.restaurantapp.data.models.EstablishmentList
import com.ashish.restaurantapp.ui.main.view.activities.MainActivity
import com.ashish.restaurantapp.ui.main.view.activities.ResultActivity
import com.ashish.restaurantapp.databinding.EstablishmentItemBinding
import com.ashish.restaurantapp.ui.main.view.fragments.HomeFragment

class EstablishmentAdapter(val context: Context, establishmentList: EstablishmentList) :
    RecyclerView.Adapter<EstablishmentAdapter.ViewHolder>() {

    val list: List<Establishment> = establishmentList.establishments

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val establishmentItemBinding = EstablishmentItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(establishmentItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val establishmentX = list[position].establishment

        holder.establishmentItemBinding?.establishment   = establishmentX
        holder.itemView.setOnClickListener {
            val intent = Intent (context, ResultActivity::class.java)
            intent.putExtra("est_id",establishmentX.id)
            intent.action = "establishment"
            context.startActivity(intent)
        }

        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.translateup_smooth_anim)
        holder.itemView.startAnimation(animation)


    }

    override fun getItemCount(): Int {
        return if(HomeFragment.establishmentExpanded) {
            list.size
        }else{
            6
        }
    }

    class ViewHolder( private var binding: EstablishmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var establishmentItemBinding: EstablishmentItemBinding? = binding

    }
}