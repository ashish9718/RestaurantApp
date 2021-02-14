package com.ashish.restaurantapp.data.api

import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: ApiService) {

    suspend fun getCategories() = apiService.getCategories(Keys.userkey)

    suspend fun getRestaurantsCollection() =
        apiService.getRestaurantsCollection(Keys.userkey)

    suspend fun getCuisines() = apiService.getCuisines(Keys.userkey)

    suspend fun getEstablishments() = apiService.getEstablishments(Keys.userkey)

    ////

    suspend fun getRestaurantsUsingSearch(querry: String) =
        apiService.getRestaurantsUsingSearch(Keys.userkey, querry)

    suspend fun getRestaurantsByCategory(id: Int) =
        apiService.getRestaurantsByCategory(Keys.userkey, id)

    suspend fun getRestaurantsByCuisine(id: Int) =
        apiService.getRestaurantsByCuisine(Keys.userkey, id)

    suspend fun getRestaurantsByEstablishment(id: Int) =
        apiService.getRestaurantsByEstablishment(Keys.userkey, id)

    suspend fun getRestaurantsByCollection(id: Int) =
        apiService.getRestaurantsByCollection(Keys.userkey, id)

    ////
    suspend fun getRestaurantDetail(id: Int) =
        apiService.getRestaurantDetail(Keys.userkey, id)

    /// filter

    suspend fun getRestaurantByCuisineFilter(i: Int, str: String) =
        apiService.getRestaurantByCuisineFilter(Keys.userkey, i, str)

    suspend fun getRestaurantByCategoryFilter(i: Int, str: String) =
        apiService.getRestaurantByCategoryFilter(Keys.userkey, i, str)

    suspend fun getRestaurantByLocationFilter(str: String, str2: String) =
        apiService.getRestaurantByLocationFilter(Keys.userkey, str, str2)

    suspend fun getRestaurantByEstablishmentFilter(i: Int, str: String) =
        apiService.getRestaurantByEstablishmentFilter(Keys.userkey, i, str)

    suspend fun getRestaurantByCollectionFilter(i: Int, str: String) =
        apiService.getRestaurantByCollectionFilter(Keys.userkey, i, str)
}
