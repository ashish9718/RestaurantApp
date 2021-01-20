package com.ashish.restaurantapp.data.models

data class CategoryList(
    val categories: List<CategoryX>
)

data class CategoryX(
    val categories: Categorie
)

data class Categorie(
    val id: Int,
    val name: String
)


