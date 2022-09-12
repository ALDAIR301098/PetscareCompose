package com.softgames.petscare.domain.model

sealed class LoginState {
    object NOT_LOGGED : LoginState()
    object LOADING : LoginState()
    object LOGGED : LoginState()
}
