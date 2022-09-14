package com.softgames.petscare.presentation.screens.login

import androidx.lifecycle.ViewModel
import com.softgames.petscare.domain.model.Country
import com.softgames.petscare.domain.model.LoginState
import com.softgames.petscare.domain.model.LoginState.NOT_LOGGED
import com.softgames.petscare.util.logMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

) : ViewModel() {

    private var _loginState: MutableStateFlow<LoginState> = MutableStateFlow(NOT_LOGGED)
    val loginState = _loginState.asStateFlow()

    private var _isCountySelectorVisible = MutableStateFlow(false)
    val isCountySelectorVisible = _isCountySelectorVisible.asStateFlow()

    private var _phone = MutableStateFlow("")
    val phone = _phone.asStateFlow()

    private var _phoneError: MutableStateFlow<String?> = MutableStateFlow(null)
    val phoneError = _phoneError.asStateFlow()

    private var _country: MutableStateFlow<Country?> = MutableStateFlow(null)
    val country = _country.asStateFlow()

    private var _countryError: MutableStateFlow<String?> = MutableStateFlow(null)
    val countryError = _countryError.asStateFlow()

    // FUNCTIONS ***********************************************************************************

    fun updatePhone(phone: String) {
        if (phone.length == 10) {
            if (phoneError.value != null) _phoneError.value = null
        }
        _phone.value = phone
    }

    fun updateCountry(country: Country) {
        _country.value = country
        _countryError.value = null
    }

    fun showCountrySelector(isVisible: Boolean) {
        _isCountySelectorVisible.value = isVisible
    }

    suspend fun signInPetscare() {
        if (validateData()) {
            logMessage("Iniciando sesión en Petscare")
            _loginState.value = LoginState.LOADING
            delay(7500L)
            logMessage("Sesión iniciada exitosamente")
            _loginState.value = LoginState.LOGGED
        }
    }

    private fun validateData(): Boolean {
        if (country.value == null) {
            _countryError.value = "Seleccione el país."; return false
        } else _countryError.value = null
        if (phone.value.isEmpty()) {
            _phoneError.value = "Ingresa el teléfono."; return false
        } else _phoneError.value = null
        if (phone.value.length != 10) {
            _phoneError.value = "Ingresa 10 dígitos."; return false
        } else _phoneError.value = null; return true
    }

}