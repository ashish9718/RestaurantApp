package com.ashish.restaurantapp

data class PhotoX(
    val id: Int,
    val md5sum: String,
    val order: Int,
    val photo_id: Int,
    val thumb_url: String,
    val type: String,
    val url: String,
    val uuid: Long
)