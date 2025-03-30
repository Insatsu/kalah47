package com.example.kalah47.feature.common_components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.kalah47.repository.DataStoreManager
import com.example.kalah47.repository.SettingsData
import org.koin.compose.koinInject

val SettingsSaver = listSaver<MutableState<SettingsData>, Any>(save = {
    listOf(
        it.value.tableTheme,
        it.value.pitsCountOneSide,
        it.value.jewelCountInOnePit,
        it.value.botId,
        it.value.botLevel
    )
}, restore = { data ->
    mutableStateOf(
        SettingsData(
            data[0] as Int,
            data[1] as Int,
            data[2] as Int,
            data[3] as Int,
            data[4] as Int
        )
    )
})

@Composable
fun getSettingsData(
): MutableState<SettingsData> {
//  * Just get table color from dataStore
    val dataStore: DataStoreManager = koinInject()

    val isDark = isSystemInDarkTheme()

    val settings: MutableState<SettingsData> =
        rememberSaveable(saver = SettingsSaver) { mutableStateOf(SettingsData()) }

    LaunchedEffect(key1 = Unit) {
        dataStore.getSettings(isDark).collect { sett ->

            settings.value = sett
        }
    }

    return settings
}
