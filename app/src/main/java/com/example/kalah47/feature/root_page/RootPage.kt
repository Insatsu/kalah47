package com.example.kalah47.feature.root_page

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.kalah47.feature.root_page.widgets.Abouts
import com.example.kalah47.feature.root_page.widgets.Body
import com.example.kalah47.feature.root_page.widgets.SettingsButton
import org.koin.compose.koinInject


@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun RootPage() {
    val koinNav = koinInject<NavController>()
    val scrollState = rememberScrollState()

    Scaffold {
        BoxWithConstraints(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val c = constraints
//          * Main body
            Body(scrollState, koinNav, Modifier.align(Alignment.Center))

//          * Settings button
            SettingsButton(
                koinNav = koinNav, modifier = Modifier.align(Alignment.TopEnd)
            )
//          * Abouts
            Abouts(koinNav, Modifier.align(Alignment.BottomCenter))

        }
    }


}
