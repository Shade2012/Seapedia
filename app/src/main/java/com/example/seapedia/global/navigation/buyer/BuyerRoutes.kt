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
    object BuyerAddress : BuyerRoute("buyer_address","buyer_address_screen")
    object BuyerCreateAddress : BuyerRoute("buyer_create_address","buyer_create_address_screen")
    object BuyerUpdateAddress : BuyerRoute("buyer_update_address/{addressId}", name = "buyer_update_address"){
        const val ADDRESS_ID = "addressId"
        fun createRoute(addressId: Int): String{
            return "buyer_update_address/$addressId"
        }
    }
    object Cart : BuyerRoute("cart_buyer", name = "cart_buyer_screen")
    object MainNavigation : BuyerRoute("main_buyer", name = "main_buyer_screen")
}