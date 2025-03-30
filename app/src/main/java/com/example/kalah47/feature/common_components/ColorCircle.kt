package com.example.kalah47.feature.common_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

@Composable
fun ColorCircle(
    modifier: Modifier,
    color: ULong = MaterialTheme.colorScheme.background.value,
//    clickAction: () -> Unit,
    content: @Composable (BoxScope.() -> Unit) = {}
) {
//  Just a little decomposition
    Box(
        modifier = modifier
            .clip(CircleShape)
//            .background(Color(color))
//            .clickable {
//                clickAction()
//            }
    )
    {
        content()
    }
}