package com.ashish.restaurantapp

data class Event(
    val book_link: String,
    val date_added: String,
    val description: String,
    val disclaimer: String,
    val display_date: String,
    val display_time: String,
    val end_date: String,
    val end_time: String,
    val event_category: Int,
    val event_category_name: String,
    val event_id: Int,
    val friendly_end_date: String,
    val friendly_start_date: String,
    val friendly_timing_str: String,
    val is_active: Int,
    val is_end_time_set: Int,
    val is_valid: Int,
    val photos: List<Photo>,
    val restaurants: List<Any>,
    val share_data: ShareData,
    val share_url: String,
    val show_share_url: Int,
    val start_date: String,
    val start_time: String,
    val title: String,
    val types: List<Any>
)