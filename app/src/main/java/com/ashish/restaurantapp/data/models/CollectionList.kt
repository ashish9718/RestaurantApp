package com.ashish.restaurantapp.data.models

data class CollectionList(
    val collections: List<Collection>,
    val display_text: String,
    val has_more: Int,
    val has_total: Int,
    val share_url: String,
    val user_has_addresses: Boolean
)

data class Collection(
    val collection: CollectionX
)

data class CollectionX(
    val collection_id: Int,
    val description: String,
    val image_url: String,
    val res_count: Int,
    val share_url: String,
    val title: String,
    val url: String
)