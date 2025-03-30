package com.example.kalah47.feature.settings.widgets

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.kalah47.R
import com.example.kalah47.core.MY_TAG
import com.example.kalah47.feature.common_components.getSettingsData
import com.example.kalah47.repository.CommonActions
import com.example.kalah47.repository.DataStoreManager
import com.example.kalah47.repository.SettingsData
import kotlinx.coroutines.launch
import org.koin.compose.koinInject


@Composable
fun TableThemes(
    dataStore: DataStoreManager = koinInject()
) {
    val bgsId = (1..3).toList()

    val coroutine = rememberCoroutineScope()

//  * Table color from dataStore
    val q = getSettingsData()
    val settings = remember {
        q
    }

    SettingsComponent(
        settingsName = "${stringResource(id = R.string.settting_theme)}: ",
        clipShape = CircleShape,
        settingsElement =
        { modifier, content ->
            Box(
                modifier = modifier
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.onBackground,
                        CircleShape
                    )
            ) {
                Image(
                    painter = painterResource(
                        id = CommonActions.getSmallTableThemeById(settings.value.tableTheme)
                    ),
                    contentDescription = "current",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                content()
            }
        },
        settingsElementCliclable = {},
        values = bgsId,
        valueComponent = { modifier, settingsValue ->
            Image(
                painter = painterResource(
                    id = CommonActions.getSmallTableThemeById(settingsValue as Int)
                ),
                contentDescription = "bg",
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .clip(CircleShape)
                    .border(1.dp, MaterialTheme.colorScheme.onBackground, CircleShape)
            )
        },
        valueComponentClickable = { settingsValue ->
            Log.d(MY_TAG, "tableTheme: $settingsValue")
            coroutine.launch {
                dataStore.saveSettingsTableTheme(SettingsData(tableTheme = settingsValue as Int))
            }
        }
    )
}