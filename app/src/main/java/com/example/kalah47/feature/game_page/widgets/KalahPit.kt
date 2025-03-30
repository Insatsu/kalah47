package com.example.kalah47.feature.game_page.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.example.kalah47.feature.game_page.viewmodel.PitsVMBot
import com.example.kalah47.feature.game_page.viewmodel.PitsVMOnline
import com.example.kalah47.feature.game_page.viewmodel.PitsViewModel

@Composable
fun KalahPit(
    kalahWidth: Dp,
    kalahHeight: Dp,
    id: Int,
    vm: PitsViewModel,
    modifierWithRotate: Modifier = Modifier
) {
    val isLocal: Boolean = vm !is PitsVMOnline && vm !is PitsVMBot

    if (isLocal)
        KalahPitLocal(
            kalahWidth = kalahWidth,
            kalahHeight = kalahHeight,
            id = id,
            vm = vm,
            modifier = modifierWithRotate
        )
    else
        KalahPitWithAve(
            componentWidth = kalahWidth,
            componentHeight = kalahHeight,
            id = id,
            vm =  vm,
            modifier = modifierWithRotate
        )


}