package com.example.kalah47.feature.game_page.widgets

import androidx.compose.animation.core.EaseInOutQuint
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.kalah47.core.JEWEL_SIZE

@Composable
fun Jewel(modifier: Modifier) {
    Box(
        modifier = modifier
            .size(JEWEL_SIZE.dp)
            .clip(CircleShape)
            .border(1.dp, MaterialTheme.colorScheme.onPrimaryContainer, CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer.copy(0.5f))
    )
}


@Composable
fun AnimatedJewel(
    offset: IntOffset,
    id: Int
) {
    val animatedOffset = animateIntOffsetAsState(
        targetValue = offset,
        label = "Jewel$id",
//        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow)
        animationSpec = tween(250, easing = EaseInOutQuint)
    )

    Jewel(modifier = Modifier
        .absoluteOffset {
            animatedOffset.value
        }
    )
}
