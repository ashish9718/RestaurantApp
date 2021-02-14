package com.ashish.restaurantapp.data.repository

import android.net.Uri
import com.ashish.restaurantapp.data.api.ApiService
import com.ashish.restaurantapp.data.api.Keys
import com.ashish.restaurantapp.data.models.Restaurant
import com.ashish.restaurantapp.data.models.RestaurantX
import com.ashish.restaurantapp.data.models.UserModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class UserRepository @Inject constructor(private var apiService : ApiService) {

    private val auth = Firebase.auth
    private val user: FirebaseUser? = auth.currentUser
    private val db = Firebase.firestore
    private var storageReference = Firebase.storage.reference

    suspend fun getUserDetails() = coroutineScope {
        val res =
            db.collection("Users").document(user!!.uid).collection("User Details")
                .get().await()
        return@coroutineScope res.documents[0].toObject(UserModel::class.java)
    }

    suspend fun getImages() = coroutineScope {
        val list = ArrayList<String>()
        val images = storageReference.child("uploads/${user?.email}/").listAll().await()
        images.items.forEach {
            list.add(it.downloadUrl.await().toString())
        }
        return@coroutineScope list
    }

    suspend fun updateUserName(username: String?): String = coroutineScope {
        val userdata = HashMap<String, Any>()
        if (!username.isNullOrEmpty()) {
            userdata["name"] = username.toString()
        }
        try {
            val docs =
                db.collection("Users").document(user!!.uid).collection("User Details")
                    .get().await().documents
            docs[0].reference.update(userdata).await()
            return@coroutineScope "Successfully Updated!"
        } catch (e: Exception) {
            return@coroutineScope e.message.toString()
        }
    }

    suspend fun uploadImage(filePath: Uri, s: String) = coroutineScope {
        try {
            val ref =
                storageReference.child("uploads/${user?.email}/" + UUID.randomUUID().toString())
            ref.putFile(filePath).await()
            return@coroutineScope setImage(s, ref.downloadUrl.await().toString())
        } catch (e: Exception) {
            return@coroutineScope e.message.toString()
        }
    }

    suspend fun setImage(s: String, pic_url: String) = coroutineScope {
        val userdata = HashMap<String, Any>()
        when (s) {
            "profile_pic" -> {
                userdata["pic"] = pic_url
            }
            "profile_bg" -> {
                userdata["pic_bg"] = pic_url
            }
        }
        try {
            val detail = db.collection("Users").document(user!!.uid).collection("User Details")
                .get().await()
            detail.documents[0].reference.update(userdata).await()
            return@coroutineScope "Pic Set !"
        } catch (e: Exception) {
            return@coroutineScope e.message.toString()
        }
    }

    suspend fun getWishlist() = coroutineScope {
        val list = ArrayList<Restaurant>()
        db.collection("Users").document(user!!.uid).collection("Wishlist").get()
            .await().documents.forEach {
                val restaurantX: RestaurantX =
                    apiService.getRestaurantDetail(
                        Keys.userkey,
                        (it.get("res_id").toString()).toInt()
                    )
                val restaurant = Restaurant(restaurantX)
                list.add(restaurant)
            }
        return@coroutineScope list
    }


    suspend fun addRestaurantToWishList(resid: String) = coroutineScope {
        val map = HashMap<String, String>()
        map["res_id"] = resid
        try {
            db.collection("Users").document(user!!.uid).collection("Wishlist").add(map).await()
            return@coroutineScope "Added to Wishlist!"
        } catch (e: Exception) {
            return@coroutineScope e.message.toString()
        }
    }

    suspend fun removeRestaurantFromWishList(resid: String) = coroutineScope {
        try {
            val collection = db.collection("Users").document(user!!.uid).collection("Wishlist")
                .get().await()
            collection.documents.forEach {
                if (it.get("res_id").toString() == resid) {
                    it.reference.delete().await()
                    return@coroutineScope "Removed from Wishlist!"
                }
            }
        } catch (e: Exception) {
            return@coroutineScope e.message.toString()
        }
    }


    fun LogOut() {
        auth.signOut()
    }

    suspend fun deleteImage(str: String) = coroutineScope {
        try {
            storageReference.storage.getReferenceFromUrl(str).delete().await()
            return@coroutineScope "Deleted!"
        } catch (e: Exception) {
            return@coroutineScope e.message.toString()
        }
    }

    suspend fun updateProfilePic(str: String) = coroutineScope {
        val hashMap = HashMap<String, Any>()
        hashMap["pic"] = str
        try {
            val collection = db.collection("Users").document(user!!.uid).collection("User Details")
                .get().await()
            collection.documents[0].reference.update(hashMap).await()
            return@coroutineScope "Updated!"
        } catch (e: Exception) {
            return@coroutineScope e.message.toString()
        }
    }

    suspend fun updateProfileBgPic(str: String) = coroutineScope {
        val hashMap = HashMap<String, Any>()
        hashMap["pic_bg"] = str
        try {
            val collection = db.collection("Users").document(user!!.uid).collection("User Details")
                .get().await()
            collection.documents[0].reference.update(hashMap).await()
            return@coroutineScope "Updated!"
        } catch (e: Exception) {
            return@coroutineScope e.message.toString()
        }
    }


}