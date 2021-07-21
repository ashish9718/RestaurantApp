package com.ashish.restaurantapp.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.ashish.restaurantapp.R
import com.ashish.restaurantapp.data.models.CategoryList
import com.ashish.restaurantapp.data.models.CategoryX
import com.ashish.restaurantapp.ui.main.view.activities.MainActivity
import com.ashish.restaurantapp.ui.main.view.activities.ResultActivity
import com.ashish.restaurantapp.databinding.CategorieItemBinding
import com.ashish.restaurantapp.ui.main.view.fragments.HomeFragment

class CategoryAdapter(var context: Context, categoryList: CategoryList) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    val list: List<CategoryX> = categoryList.categories

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val categorieItemBinding: CategorieItemBinding =
            CategorieItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(categorieItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = list[position].categories

        holder.categorieItemBinding?.category = category

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ResultActivity::class.java)
            intent.putExtra("cat_id", category.id)
            intent.action = "category"
            context.startActivity(intent)
        }

        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.translateup_smooth_anim)
        holder.itemView.startAnimation(animation)


    }

    override fun getItemCount(): Int {
        return if(HomeFragment.categoriesExpanded) {
            list.size
        }else{
            6
        }

    }

    class ViewHolder(private var binding: CategorieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var categorieItemBinding: CategorieItemBinding? = binding

    }
}

