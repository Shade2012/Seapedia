package com.example.seapedia.global.navigation.seller

sealed class SellerRoute(val route: String,val name: String){
    object SellerOrderRoute : SellerRoute("seller_order_root","seller_order_root_screen")
    object Home : SellerRoute("home_seller","home_seller_screen")
    object Store : SellerRoute("store_seller","home_seller_screen")
    object Profile : SellerRoute("profile_seller", name = "profile_seller_screen")
    object ProductList : SellerRoute("seller_products", name = "seller_products_screen")
    object ProductDetail : SellerRoute("seller_product/{productId}", "seller_product_detail_screen") {
        const val PRODUCT_ID = "productId"
        fun createRoute(productId: Int) : String {
            return "seller_product/$productId"
        }
    }
    object ProductCreate : SellerRoute(
        "seller_product_create",
        "seller_product_create_screen"
    )
    object ProductUpdate : SellerRoute(
        "seller_product_update/{productId}",
        "seller_product_update_screen"
    ) {
        const val PRODUCT_ID = "productId"

        fun createRoute(productId: Int) : String {
            return "seller_product_update/$productId"
        }
    }

    object ProductImageUpdate : SellerRoute(
        "seller_product_image_update/{productId}",
        "seller_product_image_update_screen"
    ) {
        const val PRODUCT_ID = "productId"

        fun createRoute(productId: Int) : String {
            return "seller_product_image_update/$productId"
        }
    }

//    Store
    object StoreCreate : SellerRoute(
        "seller_store_create",
        "seller_store_create_screen"
    )
    object StoreUpdate : SellerRoute(
        "seller_store_update",
        "seller_store_update_screen"
    )
    object Order : SellerRoute("order_seller", name = "order_seller_screen")

    object OrderDetail : SellerRoute(
        "seller_order_detail/{orderId}",
        "seller_order_detail_screen"
    ) {
        const val ORDER_ID = "orderId"

        fun createRoute(orderId: Int) : String {
            return "seller_order_detail/$orderId"
        }
    }
    object MainNavigation : SellerRoute("main_seller", name = "main_seller_screen")
}