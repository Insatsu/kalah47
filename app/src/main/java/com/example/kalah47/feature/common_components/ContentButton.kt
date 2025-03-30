package com.example.kalah47.feature.common_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun ContentButton(
    title: String, index: Int, lazyListState: LazyListState, coroutineScope: CoroutineScope
) {
    Box(
        Modifier
            .fillMaxWidth()
            .clickable {
                coroutineScope.launch {
                    lazyListState.animateScrollToItem(index)
                }
            }

    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 5.dp)
                .fillMaxWidth(0.8f),
            lineHeight = TextUnit(25f, TextUnitType.Sp)
        )

        Divider(
            Modifier.align(Alignment.BottomCenter),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.tertiaryContainer
        )

    }

}