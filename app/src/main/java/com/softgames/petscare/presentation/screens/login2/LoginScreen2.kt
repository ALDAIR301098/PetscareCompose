@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.softgames.petscare.presentation.screens.login2

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.softgames.petscare.R
import com.softgames.petscare.data.source.countries
import com.softgames.petscare.domain.model.Country
import com.softgames.petscare.presentation.components.buttons.MyButton
import com.softgames.petscare.presentation.components.buttons.MyOutlinedButton
import com.softgames.petscare.presentation.components.others.MyIcon
import com.softgames.petscare.presentation.components.textfields.MyTextField
import com.softgames.petscare.presentation.theme.PetscareTheme

@Composable
fun LoginScreen2() {

    var isLoading by remember { mutableStateOf(true) }
    var authMode by remember { mutableStateOf(1) }

    Column(Modifier.fillMaxSize()) {

        if (isLoading) LinearProgressIndicator(Modifier.fillMaxWidth())

        Scaffold(
            topBar = { TopBar() }
        ) { scaffoldPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                AuthModeSelector() { authMode = it }
                when (authMode) {
                    0 -> PhoneAuthForm()
                    1 -> MailAuthForm()
                }
                LoginButton {}
                LoginDivider()

                Row(Modifier.fillMaxWidth()) {
                    GoogleButton(Modifier.weight(1f)) {}
                    Spacer(Modifier.width(16.dp))
                    FacebookButton(Modifier.weight(1f)) {}
                }

                AnimatedVisibility(
                    visible = (authMode == 1),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        RegisterText(Modifier.align(Alignment.CenterHorizontally))
                        RegisterButton()
                    }
                }

            }

        }

    }
}

// COMPONENTS **************************************************************************************

@Composable
private fun TopBar() {
    LargeTopAppBar(
        title = {
            Text(
                text = "Iniciar sesión o registrate en Petscare",
                modifier = Modifier.padding(end = 16.dp),
                maxLines = 2
            )
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
private fun AuthModeSelector(optionSelected: (Int) -> Unit) {

    var optionSelected by remember { mutableStateOf(0) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(12.dp)),
        tonalElevation = 1.dp,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PhoneButton(
                modifier = Modifier.weight(1f),
                isSelected = (optionSelected == 0),
                onClick = { optionSelected = 0; optionSelected(0) }
            )
            MailButton(
                modifier = Modifier.weight(1f),
                isSelected = (optionSelected == 1),
                onClick = { optionSelected = 1; optionSelected(1) }
            )
        }
    }
}

@Composable
private fun PhoneButton(
    modifier: Modifier,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        color = if (isSelected) MaterialTheme.colorScheme.background
        else Color.Transparent
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            MyIcon(Icons.Outlined.Phone)
            Spacer(Modifier.width(12.dp))
            Text("Teléfono")
        }
    }
}

@Composable
private fun MailButton(
    modifier: Modifier,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        color = if (isSelected) MaterialTheme.colorScheme.background
        else Color.Transparent
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            MyIcon(Icons.Outlined.Mail)
            Spacer(Modifier.width(12.dp))
            Text("Correo")
        }
    }
}

@Composable
private fun PhoneAuthForm() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CountryDropDownMenu(
            country = countries[0],
            countryError = null,
            expanded = false,
            onClick = {}
        )
        Column(Modifier.wrapContentHeight()) {
            PhoneTextField(text = "", error = null)
            PhoneAuthMessageText()
        }
    }
}


@Composable
private fun MailAuthForm() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MailTextField(text = "", error = null)
        Column(Modifier.wrapContentHeight()) {
            PasswordTextField(text = "", error = null, false)
            Spacer(Modifier.height(8.dp))
            MailAuthMessageText(Modifier.align(Alignment.End))
        }
    }
}

@Composable
private fun MailTextField(text: String, error: String?) {
    MyTextField(
        bodyText = text,
        onValueChange = {},
        labelText = "Correo",
        errorText = error,
        leadingIcon = { MyIcon(Icons.Outlined.Mail) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email
        )
    )
}

@Composable
private fun MailAuthMessageText(modifier: Modifier) {
    Text(
        text = "¿Olvidaste tu contraseña?",
        modifier = modifier,
        color = MaterialTheme.colorScheme.tertiary
    )
}

@Composable
private fun PasswordTextField(text: String, error: String?, isPasswordVisible: Boolean) {
    MyTextField(
        bodyText = text,
        onValueChange = {},
        labelText = "Contraseña",
        errorText = error,
        leadingIcon = { MyIcon(Icons.Outlined.Lock) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password
        ),
        trailingIcon = {
            if (isPasswordVisible) MyIcon(Icons.Default.Visibility)
            else MyIcon(Icons.Default.VisibilityOff)
        }
    )
}

@Composable
private fun CountryDropDownMenu(
    country: Country?,
    countryError: String?,
    expanded: Boolean,
    onClick: (showSelector: Boolean) -> Unit,
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onClick)
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
    text: String,
    error: String?,
) {
    MyTextField(
        bodyText = text,
        onValueChange = {},
        leadingIcon = { MyIcon(Icons.Outlined.Phone) },
        labelText = "Teléfono",
        maxChar = 10,
        charCounterEnabled = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Phone
        )
    )
}

@Composable
private fun PhoneAuthMessageText() {
    Text(
        text = "Te enviaremos un mensaje con un código de verificación, para comprobar que el número de teléfono te pertenece.",
        style = MaterialTheme.typography.bodySmall,
        textAlign = TextAlign.Justify
    )
}

@Composable
private fun LoginButton(onClick: () -> Unit) {
    MyButton(
        onClick = onClick,
        text = "Iniciar sesión",
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun LoginDivider() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Divider(Modifier.weight(1f))
        Text(
            text = "o continúa mediante",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Divider(Modifier.weight(1f))
    }
}

@Composable
private fun GoogleButton(modifier: Modifier, onClick: () -> Unit) {
    MyOutlinedButton(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground
        )
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
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        MyIcon(R.drawable.ic_facebook)
        Text(
            text = "Facebook",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun RegisterText(modifier: Modifier) {
    Text(
        text = "¿Aun no tienes cuenta?",
        modifier = modifier
    )
}

@Composable
private fun RegisterButton() {
    MyOutlinedButton(
        onClick = { /*TODO*/ },
        text = "Crear cuenta ahora",
    )
}


// PREVIEWS ****************************************************************************************

@Preview(showBackground = true)
@Composable
private fun LoginPreview() {
    PetscareTheme {
        LoginScreen2()
    }
}


@Preview(showBackground = true)
@Composable
private fun PhoneAuthFormPreview() {
    PetscareTheme {
        PhoneAuthForm()
    }
}