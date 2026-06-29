package com.example.seapedia.global.navigation.driver
import com.example.seapedia.global.navigation.buyer.BuyerRoute


sealed class DriverRoute(val route: String,val name: String){
    object Home : DriverRoute("home_driver","home_driver_screen")
    object Profile : DriverRoute("profile_driver", name = "profile_driver_screen")
    object JobHistory : DriverRoute("job_history", name = "job_history_screen")
    object JobDetail : DriverRoute("job_detail/{jobId}", name = "job_detail_screen"){
        const val JOB_ID = "jobId"
        fun createRoute(jobId: Int): String{
            return "job_detail/$jobId"
        }
    }
    object MainNavigation : DriverRoute("main_driver", name = "main_driver_screen")
}