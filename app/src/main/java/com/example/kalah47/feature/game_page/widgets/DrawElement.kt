package com.example.kalah47.feature.game_page.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.CacheDrawScope
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.star
import androidx.graphics.shapes.toPath

const val ARROW_LENGTH = 0.5f

//* Arrow path for draw for need orientation
internal fun CacheDrawScope.arrowPathPortrait(isRotated: Boolean): Path {
    val matrix = Matrix()
    val scaller = 3f
    matrix.scale(size.width / scaller, size.height / scaller)
    matrix.translate(scaller / 2)
    if (isRotated) {
        matrix.rotateZ(180f)
        matrix.translate(y = -scaller)
    }

    val path = Path()

    path.moveTo(0f, 0f)
    path.addRect(Rect(topLeft = Offset(-0.4f, 0f), bottomRight = Offset(0.4f, 3f)))
    path.moveTo(-1f, 3f)
    path.lineTo(1f, 3f)
    path.lineTo(0f, 3.5f)
    path.lineTo(-1f, 3f)
    path.close()

    path.transform(matrix)
    return path
}

internal fun CacheDrawScope.arrowPathLandScape(isRotated: Boolean): Path {
    val matrix = Matrix()
    val scaller = 3f
    matrix.scale(size.width / scaller, size.height / scaller)
    matrix.translate(y=scaller / 2)
    if (isRotated) {
        matrix.rotateZ(180f)
        matrix.translate(x = -scaller)
    }

    val path = Path()

    path.moveTo(0f, 0f)
    path.addRect(Rect(topLeft = Offset(y = -0.4f, x = 0f), bottomRight = Offset(y = 0.4f, x = 3f)))
    path.moveTo(y = -1f, x = 3f)
    path.lineTo(y = 1f, x = 3f)
    path.lineTo(y = 0f, x = 3.5f)
    path.lineTo(y = -1f, x = 3f)
    path.close()

    path.transform(matrix)
    return path
}

// ===============================================================================
//* Component with arrow for game table need orientation

@Composable
internal fun ArrowsBySidePortrait() {
    BoxWithConstraints(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 5.dp)
    ) {
//        val arrowColor = MaterialTheme.colorScheme.onBackground
        val arrowColor = Color.Black
        for(i in 0..1){
            Box(
                Modifier
                    .fillMaxHeight(ARROW_LENGTH)
                    .width(10.dp)
                    .align(if(i==1) Alignment.CenterEnd else Alignment.CenterStart)
                    .drawWithCache {
                        val path = arrowPathPortrait(i==1)
                        onDrawBehind {
                            drawPath(
                                path, arrowColor,
                            )
                        }
                    }
            )
        }
    }
}


@Composable
internal fun ArrowsBySideLandScape() {
    BoxWithConstraints(
        Modifier
            .fillMaxSize()
            .padding(vertical = 5.dp)
    ) {
//        val arrowColor = MaterialTheme.colorScheme.onBackground
        val arrowColor = Color.Black
        for(i: Int in 0..1){
            Box(
                Modifier
                    .fillMaxWidth(ARROW_LENGTH)
                    .height(10.dp)
                    .align(if( i == 0) Alignment.TopCenter else Alignment.BottomCenter)
                    .drawWithCache {
                        val path = arrowPathLandScape(i==0)
                        onDrawBehind {
                            drawPath(
                                path, arrowColor,
                            )
                        }
                    }
            )
        }
    }
}

// ============================================================================
// * Current player indicator in star form
internal class CurrentPlayerIndicator : Shape {
    private val matrix = Matrix()
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

        matrix.scale(size.width / 2f, size.height / 2f)
        matrix.translate(1f, 1f)
        matrix.rotateZ(45f)

        val path = RoundedPolygon.star(4, rounding = CornerRounding(0.2f)).toPath().asComposePath()
        path.transform(matrix)
        return Outline.Generic(
            path
        )
    }
}

