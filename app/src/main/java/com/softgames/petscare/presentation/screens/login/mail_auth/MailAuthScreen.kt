@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.softgames.petscare.presentation.screens.login.mail_auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softgames.petscare.presentation.components.others.MyIcon
import com.softgames.petscare.presentation.components.textfields.MyTextField
import com.softgames.petscare.presentation.theme.PetscareTheme

@Composable
fun MailAuthScreen(
    modifier: Modifier = Modifier,
    viewModel: MailAuthViewModel = hiltViewModel(),
) {

    // STATES **************************************************************************************

    val isLoading by remember { mutableStateOf(false) }
    val data = viewModel.data.collectAsState()


    Column(
        modifier = modifier.fillMaxSize()
    ) {
        if (isLoading) LinearProgressIndicator(Modifier.fillMaxWidth())

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Scaffold(
                topBar = { TopBar() }
            ) { padding ->
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MailTextField(data.value.mail) { viewModel.updateMail(it) }
                    PasswordTextField(data.value.password) { viewModel.updatePassword(it) }
                }
            }
        }

    }
}

// COMPONENTS **************************************************************************************

@Composable
private fun TopBar() {
    LargeTopAppBar(
        title = { Text("Iniciar sesión mediante correo electronico") },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ), navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                MyIcon(Icons.Default.ArrowBack)
            }
        }
    )
}

@Composable
private fun MailTextField(
    mail: String,
    update: (String) -> Unit,
) {
    MyTextField(
        bodyText = mail,
        onValueChange = { },
        labelText = "Correo electronico"
    )
}

@Composable
fun PasswordTextField(
    password: String,
    update: (String) -> Unit,
) {
    MyTextField(bodyText = password, onValueChange = update)
}

@Preview(showBackground = true)
@Composable
private fun MailAuthPreview() {
    PetscareTheme {
        MailAuthScreen()
    }
}