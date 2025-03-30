package com.example.kalah47.feature.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.kalah47.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ScrollToTopButton(
    lazyColumnState: LazyListState,
    coroutine: CoroutineScope
) {
    if (lazyColumnState.canScrollBackward)
        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.chevron_direction_top_outline_icon),
            contentDescription = "go_top",
            tint = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.tertiaryContainer)
                .clickable {
                    coroutine.launch {
                        lazyColumnState.animateScrollToItem(0)
                    }
                }
                .padding(horizontal = 8.dp)
        )
}
