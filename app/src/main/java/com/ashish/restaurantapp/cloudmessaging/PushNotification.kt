package com.ashish.restaurantapp.cloudmessaging

data class PushNotification(
    val data: NotificationData,
    val to: String
)