package com.ashish.restaurantapp.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.ashish.restaurantapp.R
import com.ashish.restaurantapp.databinding.ImageItemBinding
import com.ashish.restaurantapp.ui.main.view.activities.ImagePreviewActivity
import com.ashish.restaurantapp.ui.main.viewmodel.UserViewModel
import com.ashish.restaurantapp.utils.Status
import com.squareup.picasso.Picasso
import es.dmoral.toasty.Toasty

class PicsAdapter(
    private var lifecycleOwner: LifecycleOwner,
    var context: Context,
    var list: ArrayList<String>,
    private var userViewModel: UserViewModel
) : RecyclerView.Adapter<PicsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val imageItemBinding = ImageItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(imageItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = list[position]
        holder.bind(listItem)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ImagePreviewActivity::class.java)
            intent.putExtra("url", listItem)
            context.startActivity(intent)
        }
        holder.imageItemBinding.delete.setOnClickListener {
            userViewModel.deleteImage(listItem).observe(lifecycleOwner) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let {
                            Toasty.success(context, it, Toast.LENGTH_SHORT).show()
                        }
                    }
                    Status.LOADING -> {
                    }
                    Status.ERROR -> {
                        Toasty.error(context, resource.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
            list.removeAt(position)
            notifyItemRemoved(position)
        }

        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.translateup_smooth_anim)
        holder.itemView.startAnimation(animation)


    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(var imageItemBinding: ImageItemBinding) :
        RecyclerView.ViewHolder(imageItemBinding.root) {
        fun bind(item: String) {
            println(item)
            Picasso.get().load(item).fit().into(imageItemBinding.imageItemView)
        }
    }
}