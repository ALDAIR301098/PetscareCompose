@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.softgames.petscare.presentation.screens.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.softgames.petscare.R
import com.softgames.petscare.data.source.C_PET_OWNER
import com.softgames.petscare.data.source.C_PROVIDER
import com.softgames.petscare.domain.contracts.GoogleAuthContract
import com.softgames.petscare.domain.model.Country
import com.softgames.petscare.domain.model.LoginState.*
import com.softgames.petscare.presentation.components.buttons.MyButton
import com.softgames.petscare.presentation.components.buttons.MyOutlinedButton
import com.softgames.petscare.presentation.components.others.LoadingAnimation
import com.softgames.petscare.presentation.components.others.MyIcon
import com.softgames.petscare.presentation.components.textfields.MyTextField
import com.softgames.petscare.presentation.screens.login.components.CountrySelector
import com.softgames.petscare.presentation.theme.PetscareTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    showRegisterScreen: () -> Unit = {},
    showPhoneAuthScreen: () -> Unit = {},
    showMailAuthScreen: () -> Unit = {},
    showPetOwnerMenuScreen: () -> Unit = {},
    showProviderMenuScreen: () -> Unit = {},
) {

    // STATES **************************************************************************************

    val loginState by viewModel.loginState.collectAsState()
    val country by viewModel.country.collectAsState()
    val countryError by viewModel.countryError.collectAsState()
    val phone by viewModel.phone.collectAsState()
    val phoneError by viewModel.phoneError.collectAsState()
    val isLoginButtonEnabled by viewModel.isButtonLoginEnabled.collectAsState()
    var isGoogleButtonLoading by remember { mutableStateOf(false) }
    var showCountrySelector by remember { mutableStateOf(false) }

    val googleAuthRequest = SignInWithGoogle(
        onSuccess = {
            isGoogleButtonLoading = false
            viewModel.signInPetscare(it)
        },
        onFailure = { isGoogleButtonLoading = false }
    )

    // SCREEN **************************************************************************************

    when (loginState) {
        LOGGED -> LaunchedEffect(key1 = Unit) {
            if (viewModel.checkIfUserExist()) {
                when (viewModel.checkUserType()) {
                    C_PET_OWNER -> {}
                    C_PROVIDER -> {}
                }
            } else showRegisterScreen()
        }

        NOT_LOGGED, LOADING -> {

            Column(modifier = modifier.fillMaxSize()) {

                if (loginState == LOADING) LinearProgressIndicator(Modifier.fillMaxWidth())

                Scaffold(
                    topBar = { TopBar() }
                ) { padding ->
                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(padding)
                            .padding(top = 16.dp)
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CountryDropDownMenu(
                            country = country,
                            countryError = countryError,
                            expanded = showCountrySelector,
                            onExpandedChange = { isExpanded ->
                                if (isExpanded) showCountrySelector = true
                            },
                        )
                        Column {
                            PhoneTextField(phone, phoneError) { viewModel.updatePhone(it) }
                            Spacer(Modifier.height(4.dp))
                            PhoneHelperText()
                        }
                        LoginButton(isLoginButtonEnabled)
                        LoginDivider()
                        MailButton() { showMailAuthScreen() }
                        GoogleButton(isGoogleButtonLoading) {
                            googleAuthRequest.launch(0); isGoogleButtonLoading = true
                        }
                        FacebookButton()
                    }
                }

                if (showCountrySelector) CountrySelector() { country ->
                    showCountrySelector = false
                    country?.let { viewModel.updateCountry(country) }
                }
            }
        }
    }

}

// COMPONENTS **************************************************************************************

@Composable
private fun TopBar() {
    LargeTopAppBar(
        modifier = Modifier.padding(end = 16.dp),
        title = { Text("Iniciar sesión o registrate en Petscare") },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
private fun CountryDropDownMenu(
    country: Country?,
    countryError: String?,
    expanded: Boolean,
    onExpandedChange: (showSelector: Boolean) -> Unit,
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange)
    {
        MyTextField(
            bodyText = country?.name ?: "",
            onValueChange = { },
            labelText = "País o región",
            leadingIcon = {
                if (country != null) MyIcon(country.flag)
                else MyIcon(Icons.Default.Public)
            },
            trailingIcon = {
                if (country == null) MyIcon(Icons.Default.ArrowDropDown)
                else {
                    Row(modifier = Modifier.padding(start = 16.dp, end = 12.dp)) {
                        Text("( ${country.code} )")
                        Spacer(Modifier.width(8.dp))
                        MyIcon(Icons.Default.ArrowDropDown)
                    }
                }
            },
            errorText = countryError,
            readOnly = true
        )
    }

}

@Composable
private fun PhoneTextField(
    phone: String,
    error: String?,
    onValueChange: (String) -> Unit,
) {
    MyTextField(
        bodyText = phone,
        onValueChange = { onValueChange(it) },
        labelText = "Teléfono",
        leadingIcon = { MyIcon(Icons.Outlined.Phone) },
        keyboardOptions = KeyboardOptions().copy(
            keyboardType = KeyboardType.Phone
        ),
        helperText = "Ingrese 10 dígitos",
        maxChar = 10,
        charCounterEnabled = true,
        errorText = error,
    )
}

@Composable
private fun PhoneHelperText() {
    Text(
        text = "Te enviaremos un mensaje con un código de verificación, para comprobar que el número de teléfono te pertenece.",
        style = MaterialTheme.typography.bodySmall,
        textAlign = TextAlign.Justify
    )

}

@Composable
private fun LoginButton(isEnabled: Boolean) {
    MyButton(onClick = { /*TODO*/ }, text = "Continuar", enabled = isEnabled)
}

@Composable
private fun LoginDivider(modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Divider(modifier = modifier.weight(1f))
        Text(text = "o bien", modifier = modifier.padding(horizontal = 16.dp))
        Divider(modifier = modifier.weight(1f))
    }
}

@Composable
private fun MailButton(onClick: () -> Unit) {
    MyOutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
            containerColor = Color.White,
        )
    ) {
        MyIcon(Icons.Outlined.Mail)
        Spacer(Modifier.width(ButtonDefaults.IconSpacing))
        Text(
            text = "Continuar con correo electrónico",
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun GoogleButton(isLoading: Boolean, onClick: () -> Unit) {
    MyOutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
            containerColor = Color.White
        )
    ) {
        if (isLoading) {
            LoadingAnimation()
        } else {
            MyIcon(drawable = R.drawable.ic_google, modifier = Modifier.size(22.dp))
            Spacer(Modifier.width(ButtonDefaults.IconSpacing))
            Text(
                text = "Continuar con Google",
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun FacebookButton() {
    MyOutlinedButton(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
            containerColor = Color.White
        )
    ) {
        MyIcon(R.drawable.ic_facebook)
        Spacer(Modifier.width(ButtonDefaults.IconSpacing))
        Text(
            text = "Continuar con Facebook",
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SignInWithGoogle(
    onSuccess: (AuthCredential) -> Unit,
    onFailure: () -> Unit,
) = rememberLauncherForActivityResult(contract = GoogleAuthContract()
) { task ->
    if (task != null) {
        val account = task.result
        val credential = GoogleAuthProvider
            .getCredential(account?.idToken, null)
        onSuccess(credential)
    } else onFailure()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginPreview() {
    PetscareTheme {
        LoginScreen()
    }
}
