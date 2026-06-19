package com.example.seapedia.global.utils

enum class UserRole {
    Buyer,
    Seller,
    Driver,
    Admin,
    Guest
}
val ALL_USER_ROLES = UserRole.entries.filter { it != UserRole.Guest && it != UserRole.Admin }