package com.example.seapedia.global.navigation.buyer

sealed class BuyerRoute(val route: String,val name: String){
    object Home : BuyerRoute("home_buyer","home_buyer_screen")
    object Profile : BuyerRoute("profile_buyer", name = "profile_buyer_screen")
    object ProductDetail : BuyerRoute("buyer_product_detail/{productId}", name = "buyer_product_detail"){
        const val PRODUCT_ID = "productId"
        fun createRoute(productId: Int): String{
            return "buyer_product_detail/$productId"
        }
    }
    object ProfileBuyerUpdatePhoneNumber : BuyerRoute("profile_buyer_update_phone_number/{phoneNumber}", name = "profile_buyer_update_phone_number-screen"){
        const val PHONE_NUMBER = "phoneNumber"
        fun createRoute(phoneNumber: String): String{
            return "profile_buyer_update_phone_number/$phoneNumber"
        }
    }

    object TopUp : BuyerRoute("top_up","top_up_screen")
    object BuyerAddress : BuyerRoute("buyer_address","buyer_address_screen")
    object BuyerCreateAddress : BuyerRoute("buyer_create_address","buyer_create_address_screen")
    object BuyerUpdateAddress : BuyerRoute("buyer_update_address/{addressId}", name = "buyer_update_address"){
        const val ADDRESS_ID = "addressId"
        fun createRoute(addressId: Int): String{
            return "buyer_update_address/$addressId"
        }
    }
    object Cart : BuyerRoute("cart_buyer", name = "cart_buyer_screen")
    object CartItemCreate : BuyerRoute("cart_buyer_create/{productId}","cart_buyer_create_screen"){
        const val PRODUCT_ID = "productId"
        fun createRoute(productId: Int): String{
            return "cart_buyer_create/$productId"
        }
    }
    object CartItemUpdate : BuyerRoute("cart_buyer_edit","cart_buyer_edit_screen")
    object MainNavigation : BuyerRoute("main_buyer", name = "main_buyer_screen")
}