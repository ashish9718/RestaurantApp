package com.ashish.restaurantapp.data.models

data class EstablishmentList(
    val establishments: List<Establishment>
)

data class Establishment(
    val establishment: EstablishmentX
)

data class EstablishmentX(
    val id: Int,
    val name: String
)