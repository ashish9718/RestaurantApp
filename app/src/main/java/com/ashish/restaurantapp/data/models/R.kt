package com.ashish.restaurantapp.data.models

import com.ashish.restaurantapp.HasMenuStatus

data class R(
    val has_menu_status: HasMenuStatus,
    val is_grocery_store: Boolean,
    val res_id: Int
)