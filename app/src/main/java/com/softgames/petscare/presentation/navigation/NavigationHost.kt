package com.softgames.petscare.presentation.navigation

import LoginScreen2
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun NavigationHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppDestinations.LoginScreen2.route
    ) {

        composable(AppDestinations.LoginScreen2.route) {
            LoginScreen2()
        }

    }

}