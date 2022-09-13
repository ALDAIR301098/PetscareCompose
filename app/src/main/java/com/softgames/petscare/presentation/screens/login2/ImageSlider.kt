@file:OptIn(ExperimentalPagerApi::class)

package com.softgames.petscare.presentation.screens.login2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.softgames.petscare.data.source.welcomeImageList
import com.softgames.petscare.presentation.components.others.MyImage
import com.softgames.petscare.presentation.theme.PetscareTheme
import com.softgames.petscare.util.logMessage

@Composable()
fun ImageSlider(modifier: Modifier) {

    val pagerState = rememberPagerState()
    var currentPage by remember { mutableStateOf(0) }

    logMessage(currentPage)

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { index ->
            currentPage = index
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        HorizontalPager(count = 5, state = pagerState) { index ->
            MyImage(
                imageRes = welcomeImageList[index], modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentScale = ContentScale.Crop
            )
        }
        Surface(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.BottomEnd)
                .padding(horizontal = 16.dp, vertical = 32.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "${currentPage.plus(1)}/5",
                modifier = Modifier.padding(6.dp)
            )
        }
    }
}

// *************************************************************************************************
//                                               PREVIEWS
// *************************************************************************************************

@Preview(showBackground = true)
@Composable
private fun ImageSliderPreview() {
    PetscareTheme {
        ImageSlider(Modifier)
    }
}