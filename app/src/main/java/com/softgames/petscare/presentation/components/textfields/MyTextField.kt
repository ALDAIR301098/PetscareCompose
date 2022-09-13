@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)

package com.softgames.petscare.presentation.components.textfields

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.softgames.petscare.presentation.components.others.MyIcon
import com.softgames.petscare.presentation.theme.PetscareTheme

@Composable
fun MyTextField(
    bodyText: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    labelText: String? = null,
    placeholderText: String? = null,
    helperText: String? = null,
    errorText: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    maxChar: Int? = null,
    isCounterMaxCharEnabled: Boolean = false,
    enabled: Boolean = true,
    errorIconEnabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    focusManager: FocusManager = LocalFocusManager.current,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        capitalization = KeyboardCapitalization.Words,
        imeAction = ImeAction.Next
    ),
    keyboardActions: KeyboardActions = KeyboardActions(
        onNext = { focusManager.moveFocus(FocusDirection.Down) },
        onDone = { keyboardController?.hide() }
    ),
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        cursorColor = MaterialTheme.colorScheme.tertiary,
        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
        focusedLabelColor = MaterialTheme.colorScheme.tertiary
    ),
) {

    ConstraintLayout(modifier = modifier) {

        val (textField, txtHelper, txtError, txtCounter) = createRefs()

        OutlinedTextField(
            value = bodyText,
            onValueChange = {
                if (it.length <= (maxChar ?: Int.MAX_VALUE)) {
                    onValueChange(it)
                }
            },
            modifier = Modifier.constrainAs(textField) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            label = Label(labelText),
            isError = errorText != null,
            placeholder = PlaceHolder(placeholderText),
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon ?: ErrorIcon(errorText, errorIconEnabled),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            interactionSource = interactionSource,
            shape = shape,
            colors = colors
        )

        helperText?.let {
            if (errorText == null) {
                Text(
                    text = it,
                    modifier = Modifier
                        .constrainAs(txtHelper) {
                            top.linkTo(textField.bottom, 4.dp)
                            start.linkTo(textField.start, 16.dp)
                            end.linkTo(textField.end, 16.dp)
                            width = Dimension.fillToConstraints
                        },
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        errorText?.let {
            Text(
                text = errorText,
                modifier = modifier.constrainAs(txtError) {
                    top.linkTo(textField.bottom, 4.dp)
                    start.linkTo(textField.start, 16.dp)
                    if (isCounterMaxCharEnabled) end.linkTo(txtCounter.start, 16.dp)
                    else end.linkTo(textField.end, 16.dp)
                    width = Dimension.fillToConstraints
                },
                color = MaterialTheme.colorScheme.error,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall,
            )
        }

        if (isCounterMaxCharEnabled) {
            Text(
                text = "${bodyText.length}/$maxChar",
                modifier = Modifier.constrainAs(txtCounter) {
                    top.linkTo(textField.bottom, 4.dp)
                    end.linkTo(textField.end, 16.dp)
                },
                style = MaterialTheme.typography.bodySmall
            )
        }

    }
}

@Composable
private fun Label(labelText: String?): @Composable (() -> Unit)? {
    if (labelText != null) return { Text(labelText) }
    else return null
}

@Composable
private fun PlaceHolder(placeholderText: String?): @Composable (() -> Unit)? {
    if (placeholderText != null) return { Text(placeholderText) }
    else return null
}

@Composable
private fun ErrorIcon(errorText: String?, errorIconEnabled: Boolean): @Composable (() -> Unit)? {
    if (errorText != null && errorIconEnabled) {
        return {
            MyIcon(
                imageVector = Icons.Default.Error,
                tint = MaterialTheme.colorScheme.error
            )
        }
    } else return null
}

@Preview(showBackground = true)
@Composable
private fun MyTextFieldPreview() {
    PetscareTheme {
        val txtName by remember { mutableStateOf("México") }
        MyTextField(
            bodyText = txtName,
            onValueChange = {},
            labelText = "País",
            leadingIcon = { MyIcon(Icons.Default.Public) },
        )
    }
}