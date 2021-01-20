package com.ashish.restaurantapp.data.models

data class CuisineList(
    val cuisines: List<Cuisine>
)

data class Cuisine(
    val cuisine: CuisineX
)

data class CuisineX(
    val cuisine_id: Int,
    val cuisine_name: String
)