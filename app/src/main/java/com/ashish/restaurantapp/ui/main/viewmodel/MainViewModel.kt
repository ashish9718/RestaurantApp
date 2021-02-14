package com.ashish.restaurantapp.ui.main.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ashish.restaurantapp.data.repository.MainRepository
import com.ashish.restaurantapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class MainViewModel @ViewModelInject constructor(private var mainRepository: MainRepository) : ViewModel() {

    fun getCategories() = liveData {
            emit(Resource.loading(null))
            try {
                 emit(Resource.success(mainRepository.getCategories()))
            }catch (e: Exception) {
                emit(Resource.error(e.message.toString(),null))
            }
        }

    fun getRestaurantsCollection() = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.getRestaurantsCollection()))
        }catch (e: Exception) {
            emit(Resource.error(e.message.toString(),null))
        }
    }

    fun getCuisines()  = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.getCuisines()))
        }catch (e: Exception) {
            emit(Resource.error(e.message.toString(),null))
        }
    }

    fun getEstablishments()  = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.getEstablishments()))
        }catch (e: Exception) {
            emit(Resource.error(e.message.toString(),null))
        }
    }

}