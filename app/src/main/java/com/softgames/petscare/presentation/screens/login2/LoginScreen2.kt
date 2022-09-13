@file:OptIn(ExperimentalComposeUiApi::class)

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.softgames.petscare.domain.model.LoginState
import com.softgames.petscare.domain.model.LoginState.*
import com.softgames.petscare.presentation.components.others.MyIcon
import com.softgames.petscare.presentation.components.textfields.MyTextField
import com.softgames.petscare.presentation.screens.login2.ImageSlider
import com.softgames.petscare.presentation.theme.PetscareTheme

@Composable()
fun LoginScreen2() {

    var loginState: LoginState by remember { mutableStateOf(NOT_LOGGED) }

    when (loginState) {
        LOGGED -> {}
        NOT_LOGGED, LOADING -> {

            if (loginState == LOADING) LinearProgressIndicator(Modifier.fillMaxWidth())

            ConstraintLayout(
                constraintSet = constraints, modifier = Modifier.fillMaxSize()
            ) {
                ImageSlider(Modifier.layoutId("imageSlider"))
                LoginCard(Modifier.layoutId("loginCard"))
            }
        }

    }
}

private val constraints = ConstraintSet {

    val imageSlider = createRefFor("imageSlider")
    val loginCard = createRefFor("loginCard")

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

@Composable()
private fun LoginCard(modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TitleText()
            PhoneTextField(text = "", error = null)
        }
    }
}

@Composable()
private fun TitleText() {
    Text(
        text = "Inicia sesión o registrate en Petscare",
        style = MaterialTheme.typography.headlineMedium
    )
}

@Composable()
private fun PhoneTextField(text: String, error: String?) {
    MyTextField(
        bodyText = text,
        onValueChange = {},
        labelText = "Telefóno",
        leadingIcon = { MyIcon(Icons.Outlined.Phone) },
        maxChar = 10,
        isCounterMaxCharEnabled = true
    )
}

// *************************************************************************************************
//                                               PREVIEWS
// *************************************************************************************************

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    PetscareTheme {
        LoginScreen2()
    }
}