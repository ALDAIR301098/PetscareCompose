package com.softgames.petscare.presentation.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.softgames.petscare.presentation.components.others.MyIcon
import com.softgames.petscare.presentation.theme.PetscareTheme

@Composable
fun MyTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    enabled: Boolean = true,
    rounded: Boolean = false,
    shape: Shape = MaterialTheme.shapes.medium,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.height(ButtonDefaults.largeHeightSize),
        enabled = enabled,
        shape = if (rounded) MaterialTheme.shapes.extraLarge else shape,
        colors = colors,
        elevation = elevation,
        border = border
    ) {
        leadingIcon?.let {
            MyIcon(it)
            Spacer(Modifier.width(ButtonDefaults.IconSpacing))
        }

        Text(text)

        trailingIcon?.let {
            Spacer(Modifier.width(ButtonDefaults.IconSpacing))
            MyIcon(it)
        }
    }
}

@Preview
@Composable
private fun MyTextButtonPreview() {
    PetscareTheme {
        MyTextButton(text = "Continuar", onClick = { /*TODO*/ })
    }
}