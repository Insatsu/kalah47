package com.example.kalah47.feature.root_page.widgets

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kalah47.core.SettingsRoute

@Composable
internal fun SettingsButton(koinNav: NavController, modifier: Modifier){
    IconButton(modifier = modifier
        .padding(20.dp)
        .size(35.dp),
        onClick = {
            koinNav.navigate(SettingsRoute)
        }) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            imageVector = Icons.Rounded.Settings,
            contentDescription = "settings"
        )
    }
}