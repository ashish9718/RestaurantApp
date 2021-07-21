package com.ashish.restaurantapp.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.ashish.restaurantapp.R
import com.ashish.restaurantapp.data.models.EatWhat
import com.ashish.restaurantapp.ui.main.view.activities.MainActivity
import com.ashish.restaurantapp.databinding.ItemEatWhatBinding
import com.ashish.restaurantapp.ui.main.view.activities.ResultActivity
import com.ashish.restaurantapp.ui.main.view.fragments.HomeFragment
import com.squareup.picasso.Picasso

class EatWhatAdapter(var context: Context, var list: ArrayList<EatWhat>) :
    RecyclerView.Adapter<EatWhatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemEatWhatBinding: ItemEatWhatBinding =
            ItemEatWhatBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemEatWhatBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = list[position]

        holder.bind(listItem)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ResultActivity::class.java)
            intent.putExtra("eat_name",listItem.name)
            intent.action = "eatWhat"
            context.startActivity(intent)
        }

        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.translateup_smooth_anim)
        holder.itemView.startAnimation(animation)


    }

    override fun getItemCount(): Int {
        return if (HomeFragment.eatWhatExpanded) {
            list.size
        } else {
            6
        }
    }

    class ViewHolder(private var binding: ItemEatWhatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var itemEatWhatBinding: ItemEatWhatBinding? = binding

        fun bind(item: EatWhat) {
            Picasso.get().load(item.imageUrl).fit()
                .into(itemEatWhatBinding!!.eatWhatItemImage)
            itemEatWhatBinding!!.eatWhatItemName.text = item.name
        }
    }
}