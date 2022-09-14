@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterial3Api::class)

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.softgames.petscare.R
import com.softgames.petscare.domain.model.Country
import com.softgames.petscare.domain.model.LoginState.*
import com.softgames.petscare.presentation.components.buttons.MyButton
import com.softgames.petscare.presentation.components.buttons.MyOutlinedButton
import com.softgames.petscare.presentation.components.others.MyIcon
import com.softgames.petscare.presentation.components.textfields.MyTextField
import com.softgames.petscare.presentation.screens.login.ImageSlider
import com.softgames.petscare.presentation.screens.login.LoginViewModel
import com.softgames.petscare.presentation.screens.login.components.CountrySelector
import com.softgames.petscare.presentation.theme.PetscareTheme
import com.softgames.petscare.util.logMessage
import kotlinx.coroutines.launch

@Composable()
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
) {

    val loginState by viewModel.loginState.collectAsState()

    when (loginState) {
        LOGGED -> {
            logMessage("Se inicio sesión en Petscare")
        }
        NOT_LOGGED, LOADING -> {

            ConstraintLayout(
                constraintSet = constraints, modifier = Modifier.fillMaxSize()
            ) {
                ImageSlider(Modifier.layoutId("imageSlider"))
                LoginCard(Modifier.layoutId("loginCard"), viewModel)

                if (loginState == LOADING) LinearProgressIndicator(
                    Modifier.layoutId("progressIndicator")
                )

            }
        }

    }
}

private val constraints = ConstraintSet {

    val progressIndicator = createRefFor("progressIndicator")
    val imageSlider = createRefFor("imageSlider")
    val loginCard = createRefFor("loginCard")

    constrain(progressIndicator) {
        top.linkTo(parent.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        width = Dimension.fillToConstraints
    }

    constrain(imageSlider) {
        top.linkTo(parent.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }

    constrain(loginCard) {
        top.linkTo(imageSlider.bottom, (-16).dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(parent.bottom)
        height = Dimension.fillToConstraints
    }

}


// *************************************************************************************************
//                                          COMPONENTS
// *************************************************************************************************

@Composable
private fun LoginCard(
    modifier: Modifier,
    viewModel: LoginViewModel,
) {

    val isCountrySelectorVisible by viewModel.isCountySelectorVisible.collectAsState()
    val scope = rememberCoroutineScope()

    val country by viewModel.country.collectAsState()
    val countryError by viewModel.countryError.collectAsState()
    val phone by viewModel.phone.collectAsState()
    val phoneError by viewModel.phoneError.collectAsState()

    Card(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            TitleText()
            CountryDropDownMenu(
                country = country,
                countryError = countryError,
                isExpanded = isCountrySelectorVisible,
                onExpandedChange = { isExpanded ->
                    if (isExpanded) viewModel.showCountrySelector(true)
                }
            )
            Column {
                PhoneTextField(
                    phone = phone,
                    error = phoneError,
                    onTextChange = { viewModel.updatePhone(it) }
                )
                PhoneHelperText()
            }
            LoginButton {
                scope.launch {
                    logMessage("SE PRESIONO EL LOGIN BUTTON")
                    viewModel.signInPetscare()
                }
            }
            LoginDivider()
            MailButton { }
            Row {
                GoogleButton(Modifier.weight(1f)) { }
                Spacer(Modifier.width(16.dp))
                FacebookButton(Modifier.weight(1f)) { }
            }

            if (isCountrySelectorVisible) {
                CountrySelector() { country ->
                    viewModel.showCountrySelector(false)
                    country?.let { viewModel.updateCountry(it) }
                }
            }

        }
    }
}

@Composable
private fun TitleText() {
    Text(
        text = "Iniciar sesión en Petscare",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun CountryDropDownMenu(
    country: Country?,
    countryError: String?,
    isExpanded: Boolean,
    onExpandedChange: (isExpanded: Boolean) -> Unit,
) {
    ExposedDropdownMenuBox(
        expanded = isExpanded,
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
    onTextChange: (String) -> Unit,
) {

    MyTextField(
        bodyText = phone,
        onValueChange = { onTextChange(it) },
        labelText = "Telefóno",
        errorText = error,
        leadingIcon = { MyIcon(Icons.Outlined.Phone) },
        maxChar = 10,
        isCounterMaxCharEnabled = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Phone
        )
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
private fun LoginButton(onClick: () -> Unit) {
    MyButton(onClick = onClick, text = "Iniciar sesión")
}

@Composable
private fun LoginDivider() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Divider(Modifier.weight(1f))
        Text(text = "o continúa mediante", modifier = Modifier.padding(horizontal = 16.dp))
        Divider(Modifier.weight(1f))
    }
}

@Composable
private fun MailButton(onClick: () -> Unit) {
    MyOutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        MyIcon(Icons.Outlined.Mail)
        Text(
            text = "Continuar mediante correo",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun GoogleButton(modifier: Modifier, onClick: () -> Unit) {
    MyOutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        modifier = modifier
    ) {
        MyIcon(R.drawable.ic_google)
        Text(
            text = "Google",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun FacebookButton(modifier: Modifier, onClick: () -> Unit) {
    MyOutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground
        ), modifier = modifier
    ) {
        MyIcon(
            drawable = R.drawable.ic_facebook,
            modifier = Modifier.size(25.dp)
        )
        Text(
            text = "Facebook",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
    }
}

// *************************************************************************************************
//                                               PREVIEWS
// *************************************************************************************************

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    PetscareTheme {
        LoginScreen()
    }
}