package com.ashish.restaurantapp.data.repository

import com.ashish.restaurantapp.data.api.ApiHelper

class RestaurantRepository(private var apiHelper: ApiHelper) {

    suspend fun getRestaurantsUsingSearch(querry: String) =
        apiHelper.getRestaurantsUsingSearch(querry)

    suspend fun getRestaurantsByCategory(id: Int) = apiHelper.getRestaurantsByCategory(id)

    suspend fun getRestaurantsByCuisine(id: Int) = apiHelper.getRestaurantsByCuisine(id)

    suspend fun getRestaurantsByEstablishment(id: Int) = apiHelper.getRestaurantsByEstablishment(id)

    suspend fun getRestaurantsByCollection(id: Int) = apiHelper.getRestaurantsByCollection(id)

    suspend fun getRestaurantDetail(id: Int)=apiHelper.getRestaurantDetail(id)



    suspend fun getRestaurantByCuisineFilter(i: Int, str: String) =
        apiHelper.getRestaurantByCuisineFilter(i, str)

    suspend fun getRestaurantByCategoryFilter(i: Int, str: String) =
        apiHelper.getRestaurantByCategoryFilter(i, str)

    suspend fun getRestaurantByLocationFilter(str: String, str2: String) =
        apiHelper.getRestaurantByLocationFilter(str, str2)

    suspend fun getRestaurantByEstablishmentFilter(i: Int, str: String) =
        apiHelper.getRestaurantByEstablishmentFilter(i, str)

    suspend fun getRestaurantByCollectionFilter(i: Int, str: String) =
        apiHelper.getRestaurantByCollectionFilter(i, str)
}