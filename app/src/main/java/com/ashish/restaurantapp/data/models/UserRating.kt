package com.ashish.restaurantapp.data.models

import com.ashish.restaurantapp.RatingObj

data class UserRating(
    val aggregate_rating: String,
    val custom_rating_text: String,
    val custom_rating_text_background: String,
    val rating_color: String,
    val rating_obj: RatingObj,
    val rating_text: String,
    val rating_tool_tip: String,
    val votes: Int
)
