package com.ashish.restaurantapp.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ashish.restaurantapp.R
import com.ashish.restaurantapp.databinding.AddsItemBinding
import com.squareup.picasso.Picasso

class AddAdapter ( var list : List<Int>) : RecyclerView.Adapter<AddAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val addsItemBinding: AddsItemBinding =AddsItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(addsItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = list[position]

        holder.bind(listItem)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(private var addsItemBinding: AddsItemBinding) : RecyclerView.ViewHolder(addsItemBinding.root) {
        fun bind(item: Int) {
            Picasso.get().load(item).fit().into(addsItemBinding.addImageview)
        }

    }
}