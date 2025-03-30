package com.example.kalah47.feature.online_page.widgets

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kalah47.R
import com.example.kalah47.core.MY_TAG
import com.example.kalah47.core.OnlineGameRoute
import kotlin.random.Random
import kotlin.random.nextInt


@Composable
fun CreateLobby(koinNav: NavController, width: Float = 0.7f) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth(width)
            .height(70.dp)
            .padding(vertical = 10.dp),
        onClick = {
            val gameId = Random.nextInt(10000..99999)
            Log.d(MY_TAG, "Create lobby with id: $gameId")
            koinNav.navigate(
                OnlineGameRoute(
                    gameId = gameId.toString(),
                    startPlayerId = 0,
                    isCreateLobby = true
                )
            )
        }) {
        Text(
            stringResource(id = R.string.online_create_lobby_button),
            style = MaterialTheme.typography.titleLarge
        )
    }
}