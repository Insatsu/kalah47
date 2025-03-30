package com.example.kalah47.feature.common_components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.kalah47.repository.DataStoreManager
import com.example.kalah47.repository.PlayersInfo
import org.koin.compose.koinInject

val PlayersInfoSaver = listSaver<MutableState<PlayersInfo>, Any>(save = {
    listOf(it.value.iconsId, it.value.nickName)
}, restore = { data ->
    mutableStateOf(PlayersInfo(data[0] as Int, data[1] as String))
})

@Composable
fun getPlayersInfo(
): MutableState<PlayersInfo> {
    val dataStore: DataStoreManager = koinInject()

    val info: MutableState<PlayersInfo> =
        rememberSaveable(saver = PlayersInfoSaver) { mutableStateOf(PlayersInfo(0, "")) }

    LaunchedEffect(key1 = Unit) {
        dataStore.getPlayersInfo().collect { playerInfo ->
            info.value = playerInfo
        }
    }

    return info
}
