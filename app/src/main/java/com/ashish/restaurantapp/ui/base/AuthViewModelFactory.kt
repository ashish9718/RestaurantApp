package com.ashish.restaurantapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ashish.restaurantapp.data.repository.AuthRepository
import com.ashish.restaurantapp.ui.main.viewmodel.AuthViewModel

class AuthViewModelFactory(var authRepository: AuthRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AuthViewModel::class.java)){
            return AuthViewModel(authRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}
