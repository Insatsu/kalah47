package com.example.kalah47.feature.game_page.widgets

import android.content.res.Configuration
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.roundToIntRect
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kalah47.core.JEWEL_SIZE
import com.example.kalah47.feature.game_page.viewmodel.PitsVMOnline
import com.example.kalah47.feature.game_page.viewmodel.PitsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.math.cos
import kotlin.math.round
import kotlin.math.sin

val pitPadding = 10.dp


@Composable
fun GameTable(
    pitVM: PitsViewModel = viewModel(),
) {
    val orientation = LocalConfiguration.current.orientation


    when (orientation) {
        Configuration.ORIENTATION_PORTRAIT -> GameTablePortrait(
            pitVM
        )

        else -> {
            LandScapeGameTable(
                pitVM
            )
        }
    }
}

internal fun getPitLength(
    tableSmallerSide: Dp
) = tableSmallerSide.div(2.9f) - 2 * pitPadding


internal fun rotateIfNeed(pitVM: PitsViewModel): Float {
    if (pitVM !is PitsVMOnline)
        return 0f

    return when (pitVM.playerId) {
        0 -> 0f
        1 -> 180f
        else -> 0f
    }
}


@Composable
fun Jewels(vm: PitsViewModel) {
    if (vm.pitsRects.isEmpty() || vm.pitsRects[0].center == Offset.Zero) return

    val coroutine = rememberCoroutineScope()


    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    val isKeyboardOpen = remember {
        mutableStateOf(isImeVisible)
    }


//  * Value for initial state. Used once
    val isInitJewel = remember {
        mutableStateOf(false)
    }

//  * Empty pits list from previous state. If for-loop will have pit that exist in this list initial animation started
    val emptyPits: SnapshotStateList<Int> = remember {
        mutableStateListOf()
    }
//  * Temporary empty pits list. Contain pits on current state
    val tempEmptyPits = mutableListOf<Int>()


    LaunchedEffect(0) {
        snapshotFlow { vm.pitsRects[0] }.onEach {
            isInitJewel.value = false
        }.launchIn(coroutine)
    }
    LaunchedEffect(0) {
        snapshotFlow { isKeyboardOpen }.onEach {
            isInitJewel.value = false
        }.launchIn(coroutine)
    }
//    !====
//    *? Fun bug. If in for-loop add
//    ? if (!vm.kalahModel.pitsJewels.isNotEmpty())
//    ?        continue
//    *? Then will be crush for some reason
//    !===

    for (pitId in vm.pitsRects.indices) {
        for ((index, jewelId) in vm.kalahModel.pitsJewels[pitId].withIndex()) {
            AnimatedJewel(
                offset = getOffset(
                    jewelId = index,
                    pitId = pitId,
                    vm.pitsRects[pitId],
                    isInit = isInitJewel.value && !emptyPits.contains(pitId),
                    vm = vm
                ), id = jewelId
            )
        }

//      * Add in temp list pit if it empty
        if (vm.kalahModel.pitsJewels[pitId].isEmpty() && isInitJewel.value)
            tempEmptyPits.add(pitId)
    }


//  * Change empty pits list if its temp version changed
    LaunchedEffect(tempEmptyPits) {
        emptyPits.apply {
            clear()
            addAll(tempEmptyPits)
        }
    }


//  * Thanks to it jewels started from the pit center and then go to theirs real positions
    if (!isInitJewel.value) {
        isInitJewel.value = true
    }
}


fun getOffset1(jewelId: Int, pitId: Int, rect: Rect, vm: PitsViewModel): IntOffset {
    val width = rect.width.dp
    val height = rect.height.dp

    val count = 2 * Math.PI / vm.kalahModel.pitsState[pitId]

    val scale = 3.0

    val dx: Int = round(cos(count * jewelId) * width.value / scale).toInt()
    val dy: Int = round(sin(count * jewelId) * height.value / scale).toInt()

    return rect.roundToIntRect().center - IntOffset(JEWEL_SIZE, JEWEL_SIZE) + IntOffset(dx, dy)
}

fun getOffset(
    jewelId: Int,
    pitId: Int,
    rect: Rect,
    isInit: Boolean = true,
    vm: PitsViewModel
): IntOffset {
//  * Return offset of center of pit
    if (!isInit)
        return vm.pitsRects[pitId].center.round() - IntOffset(
            JEWEL_SIZE, JEWEL_SIZE
        )

    return getOffset1(jewelId, pitId, rect, vm)
}