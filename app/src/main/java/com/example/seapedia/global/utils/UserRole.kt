package com.example.seapedia.global.utils

import com.example.seapedia.data.remote.responses.order.DeliveryMethod
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class UserRole {
    @SerialName("Buyer")
    Buyer,
    @SerialName("Seller")
    Seller,
    @SerialName("Driver")
    Driver,
    @SerialName("Admin")
    Admin,
    Guest
}
val ALL_USER_ROLES = UserRole.entries.filter { it != UserRole.Guest && it != UserRole.Admin }
val ALL_LOGIN_ROLES = UserRole.entries.filter { it != UserRole.Guest }
val ALL_DELIVERY_METHOD = DeliveryMethod.entries