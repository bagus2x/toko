package com.bagus2x.toko.ui.store.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.LocalContentColor
import androidx.compose.material.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bagus2x.toko.ui.theme.Blue500
import com.bagus2x.toko.ui.theme.Blue700

@Composable
fun CoolIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .shadow(
                elevation = 4.dp,
                shape = CircleShape,
                clip = false
            )
            .clip(CircleShape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Blue700,
                        Blue500
                    )
                ),
                shape = CircleShape
            )
            .clickable(onClick = onClick)
            .border(
                width = 2.dp,
                color = Color.White,
                shape = CircleShape
            )
    ) {
        CompositionLocalProvider(LocalContentColor provides Color.White) {
            Box(modifier = Modifier.padding(12.dp)) {
                content()
            }
        }
    }
}
