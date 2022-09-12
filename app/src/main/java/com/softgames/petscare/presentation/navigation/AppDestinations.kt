package com.softgames.petscare.presentation.navigation

sealed class AppDestinations(val route: String) {
    object LoginScreen : AppDestinations("loginScreen")
    object LoginScreen2 : AppDestinations("loginScreen2")
    object RegisterScreen : AppDestinations("registerScreen")
    object PhoneAuthScreen : AppDestinations("phoneAuthScreen")
    object MailAuthScreen : AppDestinations("mailAuthScreen")
}
