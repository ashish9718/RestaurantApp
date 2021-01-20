package com.ashish.restaurantapp.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getCategories() = apiService.getCategories(RetrofitInstance.userkey)

    suspend fun getRestaurantsCollection() =
        apiService.getRestaurantsCollection(RetrofitInstance.userkey)

    suspend fun getCuisines() = apiService.getCuisines(RetrofitInstance.userkey)

    suspend fun getEstablishments() = apiService.getEstablishments(RetrofitInstance.userkey)

    ////

    suspend fun getRestaurantsUsingSearch(querry: String) =
        apiService.getRestaurantsUsingSearch(RetrofitInstance.userkey, querry)

    suspend fun getRestaurantsByCategory(id: Int) =
        apiService.getRestaurantsByCategory(RetrofitInstance.userkey, id)

    suspend fun getRestaurantsByCuisine(id: Int) =
        apiService.getRestaurantsByCuisine(RetrofitInstance.userkey, id)

    suspend fun getRestaurantsByEstablishment(id: Int) =
        apiService.getRestaurantsByEstablishment(RetrofitInstance.userkey, id)

    suspend fun getRestaurantsByCollection(id: Int) =
        apiService.getRestaurantsByCollection(RetrofitInstance.userkey, id)

    ////
    suspend fun getRestaurantDetail(id: Int) =
        apiService.getRestaurantDetail(RetrofitInstance.userkey, id)

    /// filter

    suspend fun getRestaurantByCuisineFilter(i: Int, str: String) =
        apiService.getRestaurantByCuisineFilter(RetrofitInstance.userkey, i, str)

    suspend fun getRestaurantByCategoryFilter(i: Int, str: String) =
        apiService.getRestaurantByCategoryFilter(RetrofitInstance.userkey, i, str)

    suspend fun getRestaurantByLocationFilter(str: String, str2: String) =
        apiService.getRestaurantByLocationFilter(RetrofitInstance.userkey, str, str2)

    suspend fun getRestaurantByEstablishmentFilter(i: Int, str: String) =
        apiService.getRestaurantByEstablishmentFilter(RetrofitInstance.userkey, i, str)

    suspend fun getRestaurantByCollectionFilter(i: Int, str: String) =
        apiService.getRestaurantByCollectionFilter(RetrofitInstance.userkey, i, str)
}
