package com.ashish.restaurantapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ashish.restaurantapp.data.repository.RestaurantRepository
import com.ashish.restaurantapp.ui.main.viewmodel.RestaurantViewModel

class RestaurantViewModelFactory(var restaurantRepository: RestaurantRepository): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RestaurantViewModel::class.java)){
            return RestaurantViewModel(restaurantRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}
