package com.ashish.restaurantapp.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.ashish.restaurantapp.R
import com.ashish.restaurantapp.data.models.Collection
import com.ashish.restaurantapp.data.models.CollectionList
import com.ashish.restaurantapp.databinding.ItemCollectionRestaurantBinding
import com.ashish.restaurantapp.ui.main.view.activities.ResultActivity
import com.squareup.picasso.Picasso

class CollectionsAdapter(var context: Context, collectionList: CollectionList) :
    RecyclerView.Adapter<CollectionsAdapter.ViewHolder>() {

    val list: List<Collection> = collectionList.collections

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemCollectionRestaurantBinding: ItemCollectionRestaurantBinding =
            ItemCollectionRestaurantBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemCollectionRestaurantBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val collectionX = list[position].collection

        holder.itemCollectionRestaurantBinding?.collectionx = collectionX

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ResultActivity::class.java)
            intent.putExtra("collection_id", collectionX.collection_id)
            intent.action = "collection"
            context.startActivity(intent)
        }
        holder.setData(collectionX.image_url, collectionX.res_count)

        val animation: Animation =
            AnimationUtils.loadAnimation(context, R.anim.translateup_smooth_anim)
        holder.itemView.startAnimation(animation)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(private var binding: ItemCollectionRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var itemCollectionRestaurantBinding: ItemCollectionRestaurantBinding? = binding
        fun setData(imageUrl: String, resCount: Int) {
            Picasso.get().load(imageUrl).fit().into(itemCollectionRestaurantBinding!!.imageview)
            itemCollectionRestaurantBinding!!.visitorCount.text = "$resCount places"
        }

    }
}