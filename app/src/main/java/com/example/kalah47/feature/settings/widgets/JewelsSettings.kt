package com.example.kalah47.feature.settings.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.kalah47.R
import com.example.kalah47.core.JEWELS_COUNT_IN_PIT_MAX
import com.example.kalah47.core.JEWELS_COUNT_IN_PIT_MIN
import com.example.kalah47.feature.common_components.getSettingsData
import com.example.kalah47.repository.DataStoreManager
import com.example.kalah47.repository.SettingsData
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun JewelsSettings(
    dataStore: DataStoreManager = koinInject(),
) {

    //  * Coroutine for work with dataStore
    val coroutine = rememberCoroutineScope()

    //  * List of available tables colors
    val jewelsCounts = (JEWELS_COUNT_IN_PIT_MIN..JEWELS_COUNT_IN_PIT_MAX).toList()

    //  * Table color from dataStore
    val q = getSettingsData()
    val settings = remember {
        q
    }

    SettingsComponent(
        settingsName = "${stringResource(id = R.string.setting_jewel_count)}: ",
        settingsElement =
        { modifier, content ->
            Box(
                modifier = modifier
                    .border(1.dp, MaterialTheme.colorScheme.onBackground, RectangleShape),
            ) {
                content()
                Text(
                    text = settings.value.jewelCountInOnePit.toString(), modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }
        },
        settingsElementCliclable = {},
        values = jewelsCounts,
        valueComponent = { modifier, settingsValue ->
            Box(
                modifier = modifier
            )
            {
                Text(
                    text = (settingsValue as Int).toString(), modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }
        },
        valueComponentClickable = { settingsValue ->
            coroutine.launch {
                dataStore.saveSettingsPitsAndJewels(
                    SettingsData(
                        pitsCountOneSide = settings.value.pitsCountOneSide,
                        jewelCountInOnePit = settingsValue as Int,
                        tableTheme = 0,
                        botId = 0,
                        botLevel = 0
                    )
                )

            }
        }
    )
}
