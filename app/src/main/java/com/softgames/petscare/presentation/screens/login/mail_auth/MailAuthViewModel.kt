package com.softgames.petscare.presentation.screens.login.mail_auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MailAuthViewModel @Inject constructor(
) : ViewModel() {

    private val _data = MutableStateFlow(MailAuthData())
    val data = _data.asStateFlow()

    fun updateMail(mail: String) {
        _data.value.mail = mail
    }

    fun updatePassword(password: String) {
        _data.value.password = password
    }

}

data class MailAuthData(
    var mail: String = "",
    var mailError: String? = null,
    var password: String = "",
    var passwordError: String? = null,
)