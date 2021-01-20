package com.ashish.restaurantapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ashish.restaurantapp.data.repository.MainRepository
import com.ashish.restaurantapp.ui.main.viewmodel.MainViewModel

class MainViewModelFactory(var mainRepository: MainRepository): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(mainRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}

