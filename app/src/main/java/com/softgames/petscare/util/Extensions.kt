package com.softgames.petscare.util

import android.util.Log
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import java.text.Normalizer

fun logMessage(message: Any, tag: String = "ALDAIR") {
    Log.d(tag, message.toString())
}

fun String.unacent(): String {
    val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    return REGEX_UNACCENT.replace(temp, "")
}

fun NavOptionsBuilder.clearBackStack(navController: NavHostController) {
    popUpTo(navController.graph.findStartDestination().id) {
        inclusive = true
    }
}