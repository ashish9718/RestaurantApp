package com.ashish.restaurantapp.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {

        val baseurl = "https://developers.zomato.com/api/v2.1/"
        const val userkey = "1b3c8b37ea96785391fa55c288ac385c"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var apiService: ApiService = retrofit.create(ApiService::class.java)
    }
}