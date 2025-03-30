package com.example.kalah47.feature.settings.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.kalah47.R
import com.example.kalah47.feature.common_components.getSettingsData
import com.example.kalah47.repository.DataStoreManager
import com.example.kalah47.repository.SettingsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun BotSettings(
    dataStore: DataStoreManager = koinInject(),
) {
    val coroutine = rememberCoroutineScope()

//  * From datastore
    val q = getSettingsData()
    val settings = remember {
        q
    }
//  * Title
    Text(
        stringResource(id = R.string.setting_bot_mode),
        style = MaterialTheme.typography.titleLarge
    )
//  * Main content
    Row(
        Modifier
            .height(80.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
//      * First move title
        Text(
            stringResource(id = R.string.setting_bot_first_player),
            style = MaterialTheme.typography.bodyLarge
        )

//      * First move content
        for (i in 0..1) {
            ChoiceButton(
                isEnable = settings.value.botId != i,
                coroutine = coroutine,
                dataStore = dataStore,
                settingsData =
                SettingsData(
                    botId = i,
                    botLevel = settings.value.botLevel,
                    tableTheme = 0,
                    pitsCountOneSide = 0,
                    jewelCountInOnePit = 0
                )
            ) {
                Text(stringResource(id = if (i == 1) R.string.setting_bot_firstmove_player else R.string.setting_bot_firstmove_bot))
            }
        }
    }

//  * Bot level title
    Text(
        stringResource(id = R.string.setting_bot_level),
        modifier = Modifier.height(30.dp),
        style = MaterialTheme.typography.bodyLarge
    )

//  * Bot level content
    Row(
        Modifier.height(70.dp),
    ) {
        for (i in 1..5) {
            ChoiceButton(
                isEnable = settings.value.botLevel != i,
                coroutine = coroutine,
                dataStore = dataStore,
                settingsData =
                SettingsData(
                    botId = settings.value.botId,
                    botLevel = i,
                    tableTheme = 0,
                    pitsCountOneSide = 0,
                    jewelCountInOnePit = 0
                )
            ) {
                Text("$i")
            }
        }
    }
}


@Composable
fun ChoiceButton(
    isEnable: Boolean,
    coroutine: CoroutineScope,
    dataStore: DataStoreManager,
    settingsData: SettingsData,
    content: @Composable () -> Unit
) {
    Button(
        modifier = Modifier.padding(5.dp),
        enabled = isEnable,
        onClick = {
            coroutine.launch {
                dataStore.saveSettingsBot(
                    settingsData
                )
            }
        },
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        content()
    }
}