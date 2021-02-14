package com.ashish.restaurantapp.ui.main.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ashish.restaurantapp.data.repository.RestaurantRepository
import com.ashish.restaurantapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class RestaurantViewModel @ViewModelInject constructor(private var restaurantRepository: RestaurantRepository) : ViewModel() {

    fun getRestaurantsUsingSearch(querry: String) = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(restaurantRepository.getRestaurantsUsingSearch(querry)))
        } catch (e: Exception) {
            emit(Resource.error(e.message.toString(), null))
        }
    }

    fun getRestaurantsByCategory(id: Int) = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(restaurantRepository.getRestaurantsByCategory(id)))
        } catch (e: Exception) {
            emit(Resource.error(e.message.toString(), null))
        }
    }

    fun getRestaurantsByCuisine(id: Int) = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(restaurantRepository.getRestaurantsByCuisine(id)))
        } catch (e: Exception) {
            emit(Resource.error(e.message.toString(), null))
        }
    }

    fun getRestaurantsByEstablishment(id: Int) = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(restaurantRepository.getRestaurantsByEstablishment(id)))
        } catch (e: Exception) {
            emit(Resource.error(e.message.toString(), null))
        }
    }

    fun getRestaurantsByCollection(id: Int) = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(restaurantRepository.getRestaurantsByCollection(id)))
        } catch (e: Exception) {
            emit(Resource.error(e.message.toString(), null))
        }
    }

    fun getRestaurantDetail(id: Int) = liveData {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(restaurantRepository.getRestaurantDetail(id)))
        } catch (e: Exception) {
            emit(Resource.error(e.message.toString(), null))
        }
    }

    fun getRestaurantByCollectionFilter(collectionId: Int, filtervalue: String) = liveData {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(
                    restaurantRepository.getRestaurantByCollectionFilter(
                        collectionId,
                        filtervalue
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(e.message.toString(), null))
        }
    }

    fun getRestaurantByEstablishmentFilter(establishmentId: Int, filtervalue: String) = liveData {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(
                    restaurantRepository.getRestaurantByEstablishmentFilter(
                        establishmentId,
                        filtervalue
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(e.message.toString(), null))
        }
    }

    fun getRestaurantByLocationFilter(q: String, filtervalue: String) = liveData {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(
                    restaurantRepository.getRestaurantByLocationFilter(
                        q,
                        filtervalue
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(e.message.toString(), null))
        }
    }

    fun getRestaurantByCategoryFilter(categoryId: Int, filtervalue: String) = liveData {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(
                    restaurantRepository.getRestaurantByCategoryFilter(
                        categoryId,
                        filtervalue
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(e.message.toString(), null))
        }
    }

    fun getRestaurantByCuisineFilter(cuisineId: Int, filtervalue: String) = liveData {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(
                    restaurantRepository.getRestaurantByCuisineFilter(
                        cuisineId,
                        filtervalue
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(e.message.toString(), null))
        }
    }


}