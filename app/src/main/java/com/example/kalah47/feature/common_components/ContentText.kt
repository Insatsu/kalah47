package com.example.kalah47.feature.common_components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.example.kalah47.feature.game_rule_page.TOP_PADDING


@Composable
fun ContentText(title: String, content: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(top = TOP_PADDING),
        lineHeight = TextUnit(25f, TextUnitType.Sp)
    )
    Text(
        text = content,
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(top = TOP_PADDING),
        lineHeight = TextUnit(25f, TextUnitType.Sp)
    )
}
