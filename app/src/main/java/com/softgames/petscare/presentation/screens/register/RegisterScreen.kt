package com.softgames.petscare.presentation.screens.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.softgames.petscare.presentation.theme.PetscareTheme

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Register Screen", style = MaterialTheme.typography.titleLarge)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegisterPreview() {
    PetscareTheme {
        RegisterScreen()
    }
}