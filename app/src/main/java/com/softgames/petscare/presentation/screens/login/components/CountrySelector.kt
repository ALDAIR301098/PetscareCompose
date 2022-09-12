@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.softgames.petscare.presentation.screens.login.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.softgames.petscare.data.source.countries
import com.softgames.petscare.domain.model.Country
import com.softgames.petscare.presentation.components.others.MyIcon
import com.softgames.petscare.presentation.theme.PetscareTheme
import com.softgames.petscare.util.unacent

@Composable
fun CountrySelector(
    modifier: Modifier = Modifier,
    onDismiss: (Country?) -> Unit,
) {

    var txtSearch by remember { mutableStateOf("") }

    // SCREEN **************************************************************************************

    Dialog(
        onDismissRequest = { onDismiss(null) }
    ) {
        Surface(
            shape = AlertDialogDefaults.shape,
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(.85f),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TitleText()
                Search(txtSearch) { txtSearch = it }
                CountryList(txtSearch) { onDismiss(it) }
            }
        }
    }

}

// COMPONENTS **************************************************************************************

@Composable
private fun TitleText() {
    Text(
        text = "Seleccionar país",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.fillMaxWidth()
    )
}

/* TODO: CREATE CUSTOM SEARCH COMPONENT */
@Composable
private fun Search(
    txtSearch: String,
    onValueChange: (String) -> Unit,
) {

    val keyboard = LocalSoftwareKeyboardController.current

    TextField(
        value = txtSearch,
        onValueChange = { onValueChange(it) },
        leadingIcon = { MyIcon(Icons.Default.Search) },
        placeholder = { Text("Buscar") },
        shape = MaterialTheme.shapes.extraLarge,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            containerColor = Color.White
        ),
        keyboardOptions = KeyboardOptions().copy(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { keyboard?.hide() }
        ),
        singleLine = true
    )
}

@Composable
private fun CountryList(
    txtSearch: String,
    onItemClick: (Country) -> Unit,
) {
    LazyColumn() {
        val countries = countries
            .filter { it.name.unacent().contains(txtSearch, true) }
            .sortedBy { it.name }

        items(countries) { country ->
            CountryItem(country = country, onClick = { onItemClick(it) })
        }

    }
}

@Composable
private fun CountryItem(
    country: Country,
    onClick: (Country) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { onClick(country) }
            .padding(vertical = 16.dp)
    ) {
        MyIcon(drawable = country.flag)
        Text(
            text = country.name,
            modifier = modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        )
        Text("( ${country.code} )")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CountrySelectorPreview() {
    PetscareTheme {
        CountrySelector() {}
    }
}