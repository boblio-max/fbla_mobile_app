package com.fbla.togethertrip.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fbla.togethertrip.ui.trips.TripsScreen
import com.fbla.togethertrip.ui.detail.TripDetailScreen

object Routes {
    const val TRIPS = "trips"
    const val DETAIL = "detail/{tripId}"
    fun detail(id: String) = "detail/$id"
}

@Composable
fun AppNav(nav: NavHostController) {
    NavHost(navController = nav, startDestination = Routes.TRIPS) {
        composable(Routes.TRIPS) { TripsScreen(nav) }
        composable(
            Routes.DETAIL,
            arguments = listOf(navArgument("tripId") { type = NavType.StringType })
        ) { backStack ->
            val id = backStack.arguments?.getString("tripId")!!
            TripDetailScreen(nav, id)
        }
    }
}