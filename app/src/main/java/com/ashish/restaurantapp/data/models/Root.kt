package com.ashish.restaurantapp.data.models

data class Root(
    val restaurants: List<Restaurant>,
    val results_found: Int,
    val results_shown: Int,
    val results_start: Int
)