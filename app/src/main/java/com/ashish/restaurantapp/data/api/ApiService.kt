package com.ashish.restaurantapp.data.api

import com.ashish.restaurantapp.data.models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    /// get lists

    @GET("collections?city_id=1")
    suspend fun getRestaurantsCollection(@Header("user-key") userkey: String): CollectionList

    @GET("categories")
    suspend fun getCategories(@Header("user-key") userkey: String): CategoryList

    @GET("cuisines?city_id=1")
    suspend fun getCuisines(@Header("user-key") userkey: String): CuisineList

    @GET("establishments?city_id=1")
    suspend fun getEstablishments(@Header("user-key") userkey: String): EstablishmentList

    /// get Restaurants list

    @GET("search")
    suspend fun getRestaurantsUsingSearch(
        @Header("user-key") userkey: String,
        @Query("q") location: String?
    ): Root

    @GET("search")
    suspend fun getRestaurantsByCategory(
        @Header("user-key") userkey: String,
        @Query("category") category_id: Int?
    ): Root

    @GET("search")
    suspend fun getRestaurantsByCuisine(
        @Header("user-key") userkey: String,
        @Query("cuisines") cuisine_id: Int?
    ): Root

    @GET("search")
    suspend fun getRestaurantsByEstablishment(
        @Header("user-key") userkey: String,
        @Query("establishment_type") establishment_id: Int?
    ): Root

    @GET("search")
    suspend fun getRestaurantsByCollection(
        @Header("user-key") userkey: String,
        @Query("collection_id") collection_id: Int?
    ): Root

    //// get Restaurant detail

    @GET("restaurant")
    suspend fun getRestaurantDetail(
        @Header("user-key") userkey: String,
        @Query("res_id") res_id: Int
    ): RestaurantX


    /// filter

    @GET("search")
    suspend fun getRestaurantByCategoryFilter(
        @Header("user-key") str: String,
        @Query("category") num: Int,
        @Query("sort") str2: String
    ): Root

    @GET("search")
    suspend fun getRestaurantByCollectionFilter(
        @Header("user-key") str: String,
        @Query("collection_id") num: Int,
        @Query("sort") str2: String,
    ): Root

    @GET("search")
    suspend fun getRestaurantByCuisineFilter(
        @Header("user-key") str: String,
        @Query("cuisines") num: Int,
        @Query("sort") str2: String,
    ): Root

    @GET("search")
    suspend fun getRestaurantByEstablishmentFilter(
        @Header("user-key") str: String,
        @Query("establishment_type") num: Int,
        @Query("sort") str2: String
    ): Root

    @GET("search")
    suspend fun getRestaurantByLocationFilter(
        @Header("user-key") str: String,
        @Query("q") str2: String,
        @Query("sort") str3: String
    ): Root


}