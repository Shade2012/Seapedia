package com.example.seapedia.global.navigation.add_role


sealed class AddRoleRoutes(val route: String,val name: String){
    object AddRole : AddRoleRoutes("add_role","add_role_screen")
}