package com.example.kalah47.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.kalah47.R
import com.example.kalah47.feature.settings.widgets.BotSettings
import com.example.kalah47.feature.settings.widgets.JewelsSettings
import com.example.kalah47.feature.settings.widgets.PitsSettings
import com.example.kalah47.feature.settings.widgets.TableThemes

@Composable
fun SettingsPage() {

    Scaffold{
        val padding = it
        Column(
            Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(stringResource(id = R.string.settings_title), style = MaterialTheme.typography.headlineLarge)
            TableThemes()
            PitsSettings()
            JewelsSettings()

            Spacer(modifier = Modifier.height(50.dp))
            BotSettings()
        }
    }
}