package com.example.kalah47.feature.online_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kalah47.feature.online_page.widgets.CreateLobby
import com.example.kalah47.feature.online_page.widgets.JoinInLobby
import com.example.kalah47.feature.online_page.widgets.UserSettings
import org.koin.compose.koinInject

@Composable
fun OnlinePage() {
    val koinNav: NavController = koinInject()
    val textField = remember {
        mutableStateOf("")
    }
    val isGameIdError = remember {
        mutableStateOf(false)
    }

    val scrollScale = rememberScrollState()

    val elementWidth = 0.8f

    Scaffold { paddingValue ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValue)
                .padding(top = 40.dp, start = 20.dp, end = 20.dp, bottom = 20.dp )
                .verticalScroll(scrollScale),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            UserSettings(width = elementWidth)

            Spacer(modifier = Modifier.height(20.dp))

            CreateLobby(koinNav = koinNav, width = elementWidth)
            JoinInLobby(textField, isGameIdError, koinNav)

        }
    }
}

