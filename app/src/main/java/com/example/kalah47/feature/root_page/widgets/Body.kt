package com.example.kalah47.feature.root_page.widgets

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kalah47.R
import com.example.kalah47.core.BotGameRoute
import com.example.kalah47.core.GameRuleRoute
import com.example.kalah47.core.LocalGameRoute
import com.example.kalah47.core.OnlineRoute


@Composable
internal fun Body(
    scrollState: ScrollState, koinNav: NavController, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Text(
            modifier = Modifier.weight(0.12f),
            text = stringResource(id = R.string.root_title),
            style = MaterialTheme.typography.headlineLarge
        )

        RootButton(
            modifier = Modifier
                .weight(0.1f, fill = false), onClick = {
                koinNav.navigate(LocalGameRoute)
            },
            text = stringResource(id = R.string.start_local_game)
        )

        RootButton(
            modifier = Modifier
                .weight(0.1f, fill = false),

            onClick = {
                koinNav.navigate(
                    BotGameRoute
                )
            },
            text = stringResource(id = R.string.start_bot_game)
        )


        RootButton(
            onClick = {
                koinNav.navigate(OnlineRoute)
            },
            text = stringResource(id = R.string.online_mode),
            modifier = Modifier.weight(0.1f, false)
        )

        RootButton(
            onClick = {
                koinNav.navigate(GameRuleRoute)
            },
            text = stringResource(id = R.string.game_rules),
            modifier = Modifier.weight(0.1f, false)
        )

    }
}



@Composable
private fun RootButton(onClick: () -> Unit, text: String, modifier: Modifier) {
    OutlinedButton(
        modifier = modifier.width(250.dp),
        onClick = onClick
    ) {
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}