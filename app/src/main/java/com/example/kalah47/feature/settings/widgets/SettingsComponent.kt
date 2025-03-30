package com.example.kalah47.feature.settings.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun SettingsComponent(
    settingsName: String,
    settingsElement: @Composable (modifier: Modifier, content: @Composable () -> Unit) -> Unit,
    settingsElementCliclable: () -> Unit,
    values: List<Any>,
    valueComponent: @Composable (modifier: Modifier, settingsValue: Any) -> Unit,
    valueComponentClickable: (settingsValue: Any) -> Unit,
    clipShape: Shape = RectangleShape
) {
//  * Dropdown enable property
    val dropdownExpanded = remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = settingsName, style = MaterialTheme.typography.titleMedium)

//      * Current value
        settingsElement(
            Modifier
                .size(40.dp)
                .clip(clipShape)
                .clickable {
                    settingsElementCliclable()
                    dropdownExpanded.value = true
                },
        ) {
//          * Dropdown that contain available values
            DropdownMenu(
                expanded = dropdownExpanded.value,
                onDismissRequest = { dropdownExpanded.value = false },
                offset = DpOffset((-5).dp, 5.dp),
                modifier = Modifier
                    .width(50.dp)
                    .heightIn(40.dp, 200.dp)
            ) {
//              * The values
                values.forEach {value ->
                    valueComponent(
                        Modifier
                            .padding(vertical = 5.dp)
                            .size(40.dp)
                            .align(Alignment.CenterHorizontally)
                            .clip(clipShape)
                            .clickable {
                                valueComponentClickable(value)
//                          * Close dropDown
                                dropdownExpanded.value = false
                            },
                        value
                    )
                }
            }
        }
    }
}