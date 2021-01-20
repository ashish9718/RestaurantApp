package com.ashish.restaurantapp.utils

import android.content.Context
import android.widget.Toast

fun Context.toastt(msg : String){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}