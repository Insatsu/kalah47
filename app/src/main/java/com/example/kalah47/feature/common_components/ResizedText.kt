package com.example.kalah47.feature.common_components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit

@Composable
fun ResizedText(
    text: String,
    modifier: Modifier = Modifier,
    defaultTextStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign = TextAlign.Unspecified,
    isSoftWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE
) {
    var resizedTextStyle by remember { mutableStateOf(defaultTextStyle) }
    Text(
        text,
        modifier = modifier,
        softWrap = isSoftWrap,
        maxLines = maxLines,
        color = color,
        style = resizedTextStyle,
        fontSize = fontSize,
        textAlign = textAlign,
        onTextLayout = { result ->
            if (result.didOverflowWidth) {
                resizedTextStyle =
                    resizedTextStyle.copy(fontSize = resizedTextStyle.fontSize * 0.85)
            } else if (result.didOverflowHeight) {
                resizedTextStyle =
                    resizedTextStyle.copy(fontSize = resizedTextStyle.fontSize * 0.95)
            }
        })
}