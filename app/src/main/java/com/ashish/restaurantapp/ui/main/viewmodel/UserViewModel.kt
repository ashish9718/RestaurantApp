package com.ashish.restaurantapp.ui.main.viewmodel

import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ashish.restaurantapp.data.repository.UserRepository
import com.ashish.restaurantapp.utils.Resource
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

class UserViewModel @ViewModelInject constructor(private var userRepository: UserRepository) : ViewModel() {

    fun getWishlist() = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(userRepository.getWishlist()))
        } catch (e: Exception) {
            emit(Resource.error(e.message.toString(), null))
        }
    }

    fun getUserDetails() = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(userRepository.getUserDetails()))
        } catch (e: Exception) {
            emit(Resource.error(e.message.toString(), null))
        }
    }

    fun getImages() = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(userRepository.getImages()))
        } catch (e: Exception) {
            emit(Resource.error(e.message.toString(), null))
        }
    }

    fun uploadImage(filePath: Uri, s: String) = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(userRepository.uploadImage(filePath, s)))
        } catch (e: Exception) {
            emit(Resource.error(e.message.toString(), null))
        }
    }

    fun updateUserName(username: String?) = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(userRepository.updateUserName(username)))
        } catch (e: Exception) {
            emit(Resource.error(e.message.toString(), null))
        }
    }

    fun logout() {
        userRepository.LogOut()
    }

    fun addRestaurantToWishList(id: String) = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(userRepository.addRestaurantToWishList(id)))
        } catch (e: Exception) {
            emit(Resource.error(e.message.toString(), null))
        }
    }

    fun removeRestaurantFromWishList(id: String) = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(userRepository.removeRestaurantFromWishList(id)))
        } catch (e: Exception) {
            emit(Resource.error(e.message.toString(), null))
        }
    }

    fun deleteImage(url: String) = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(userRepository.deleteImage(url)))
        } catch (e: Exception) {
            emit(Resource.error(e.message.toString(), null))
        }
    }

    fun updateProfilePic(str: String) = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(userRepository.updateProfilePic(str)))
        } catch (e: Exception) {
            emit(Resource.error(e.message.toString(), null))
        }
    }

    fun updateProfileBgPic(str: String) = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(userRepository.updateProfileBgPic(str)))
        } catch (e: Exception) {
            emit(Resource.error(e.message.toString(), null))
        }
    }
}