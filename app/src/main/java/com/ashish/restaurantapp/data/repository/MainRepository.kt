package com.ashish.restaurantapp.data.repository

import com.ashish.restaurantapp.data.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private var apiHelper: ApiHelper) {

    suspend fun getCategories() = apiHelper.getCategories()

    suspend fun getRestaurantsCollection() = apiHelper.getRestaurantsCollection()

    suspend fun getCuisines() = apiHelper.getCuisines()

    suspend fun getEstablishments() = apiHelper.getEstablishments()

}