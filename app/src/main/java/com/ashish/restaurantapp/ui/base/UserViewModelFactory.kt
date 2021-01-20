package com.ashish.restaurantapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ashish.restaurantapp.data.repository.UserRepository
import com.ashish.restaurantapp.ui.main.viewmodel.UserViewModel

class UserViewModelFactory(var userRepository: UserRepository): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserViewModel::class.java)){
            return UserViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}
