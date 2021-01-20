package com.ashish.restaurantapp.data.repository

import com.ashish.restaurantapp.data.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    suspend fun signIn(email: String, pass: String) = coroutineScope {
        try {
            auth.signInWithEmailAndPassword(email, pass).await()
            return@coroutineScope "logged in!"
        } catch (e: Exception) {
            return@coroutineScope e.message.toString()
        }
    }

    suspend fun signUp(email: String, pass: String) = coroutineScope {
        try {
            auth.createUserWithEmailAndPassword(email, pass).await()
            addUserDetails()
            return@coroutineScope "logged in!"
        } catch (e: Exception) {
            return@coroutineScope e.message.toString()
        }
    }

    private suspend fun addUserDetails() {
        val index = auth.currentUser?.email!!.indexOf("@")
        val name = auth.currentUser?.email!!.substring(0, index)
        val userModel = UserModel(name, auth.currentUser?.email.toString(), "", "")

        db.collection("Users").document(auth.currentUser!!.uid).collection("User Details")
            .add(userModel).await()
    }


}