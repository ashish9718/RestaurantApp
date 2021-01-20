package com.ashish.restaurantapp.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ashish.restaurantapp.data.repository.AuthRepository
import com.ashish.restaurantapp.utils.Resource

class AuthViewModel(private var authRepository: AuthRepository) : ViewModel() {

    val pass = MutableLiveData<String>()
    val email = MutableLiveData<String>()

    fun loginBtnClick() = liveData {
        if (email.value.isNullOrEmpty() || pass.value.isNullOrEmpty()) {
            emit("Please fill all fields")
        } else {
            emit(Resource.loading(null))
            try {
                emit(Resource.success(authRepository.signIn(email.value.toString(), pass.value.toString())))
            }catch(e : Exception){
                emit(Resource.error(e.message.toString(),null))
            }
        }
    }

    fun registerBtnClick() = liveData {
        if (email.value.isNullOrEmpty() || pass.value.isNullOrEmpty()) {
            emit("Please fill all fields")
        } else {
            emit(Resource.loading(null))
            try {
                emit(Resource.success(authRepository.signUp(email.value.toString(), pass.value.toString())))
            }catch(e : Exception){
                emit(Resource.error(e.message.toString(),null))
            }
        }
    }

}