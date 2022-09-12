package com.softgames.petscare.presentation.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.softgames.petscare.presentation.components.others.MyIcon
import com.softgames.petscare.presentation.theme.PetscareTheme

@Composable
fun MyOutlinedButton(
    text: String? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    enabled: Boolean = true,
    rounded: Boolean = false,
    shape: Shape = MaterialTheme.shapes.medium,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = ButtonDefaults.outlinedButtonBorder,
    content: @Composable (RowScope.() -> Unit)? = null,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(ButtonDefaults.largeHeightSize),
        enabled = enabled,
        shape = if (rounded) MaterialTheme.shapes.extraLarge else shape,
        colors = colors,
        elevation = elevation,
        border = border
    ) {
        if (content != null) content()
        else {
            leadingIcon?.let {
                MyIcon(it)
                Spacer(Modifier.width(ButtonDefaults.IconSpacing))
            }

            text?.let {
                Text(it)
            }

            trailingIcon?.let {
                Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                MyIcon(it)
            }
        }

    }
}

@Preview
@Composable
private fun MyOutlinedButtonPreview() {
    PetscareTheme {
        MyOutlinedButton(text = "Continuar", onClick = { /*TODO*/ })
    }
}