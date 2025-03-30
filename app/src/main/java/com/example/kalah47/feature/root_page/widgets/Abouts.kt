package com.example.kalah47.feature.root_page.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kalah47.R
import com.example.kalah47.core.AboutSystemRoute


@Composable
internal fun Abouts(koinNav: NavController, modifier: Modifier) {
    Column(
        modifier
            .padding(bottom = 25.dp)
            .height(65.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val aboutsFontSize = 16f
        Text(
            text = stringResource(id = R.string.about_system),
            fontSize = TextUnit(aboutsFontSize, TextUnitType.Sp),
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .fillMaxHeight(0.5f)
                .clip(RoundedCornerShape(8.dp))
                .wrapContentHeight(Alignment.CenterVertically)
                .clickable {
                    koinNav.navigate(AboutSystemRoute)
                }
        )

    }
}