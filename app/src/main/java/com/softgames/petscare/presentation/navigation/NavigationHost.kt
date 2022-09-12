package com.softgames.petscare.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.softgames.petscare.presentation.screens.login2.LoginScreen2


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