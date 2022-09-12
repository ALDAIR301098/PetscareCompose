package com.softgames.petscare.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.softgames.petscare.data.services.authentication.AuthService
import com.softgames.petscare.domain.model.Country
import com.softgames.petscare.domain.model.LoginState
import com.softgames.petscare.domain.model.LoginState.*
import com.softgames.petscare.util.logMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService,
) : ViewModel() {

    // STATES **************************************************************************************

    private var user: MutableStateFlow<FirebaseUser?> = MutableStateFlow(null)

    private var _country: MutableStateFlow<Country?> = MutableStateFlow(null)
    val country = _country.asStateFlow()

    private var _countryError: MutableStateFlow<String?> = MutableStateFlow(null)
    val countryError = _countryError.asStateFlow()

    private var _phone = MutableStateFlow("")
    val phone = _phone.asStateFlow()

    private var _phoneError: MutableStateFlow<String?> = MutableStateFlow(null)
    val phoneError = _phoneError.asStateFlow()

    private var _isButtonLoginEnabled = MutableStateFlow(false)
    val isButtonLoginEnabled = _isButtonLoginEnabled.asStateFlow()

    private var _loginState: MutableStateFlow<LoginState> = MutableStateFlow(NOT_LOGGED)
    val loginState = _loginState.asStateFlow()

    init {
        user.value = authService.loadUser()
        user.value?.let { _loginState.value = LOGGED }
    }

    // UPDATE STATES *******************************************************************************

    fun updateCountry(country: Country) {
        _country.value = country
    }

    fun updatePhone(phone: String) {
        _phone.value = phone
        _isButtonLoginEnabled.value = (phone.length == 10)
    }

    // AUTH SERVICES *******************************************************************************

    fun signInPetscare(credential: AuthCredential) {
        viewModelScope.launch {
            try {
                _loginState.value = LOADING
                user.value = authService.signInPetscare(credential).user
                _loginState.value = LOGGED
            } catch (e: Exception) {
                if (e is FirebaseNetworkException) {
                    logMessage(authService.getError("NO_INTERNET_CONEXION"))
                } else {
                    logMessage(e.printStackTrace())
                    logMessage(e.message!!)
                    val exception = e as FirebaseAuthException
                    logMessage(exception.errorCode)
                }
                _loginState.value = NOT_LOGGED
            }
        }
    }

    suspend fun checkIfUserExist(): Boolean = authService.checkIfUserExist(user.value!!.uid)

    suspend fun checkUserType(): String = authService.checkUserType(user.value!!.uid)

}

