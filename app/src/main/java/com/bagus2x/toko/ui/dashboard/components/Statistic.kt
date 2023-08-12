package com.bagus2x.toko.ui.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocalDrink
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bagus2x.toko.R
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun Statistic(
    name: String,
    month: Month,
    year: Int,
    target: Int,
    achievement: Int,
    color: Color,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(LocalContentColor provides Color.White) {
        Box(modifier = modifier) {
            Column(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .background(color)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${month.getDisplayName(TextStyle.FULL, Locale.getDefault())} $year",
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = "${
                        String.format(
                            "%.0f",
                            (achievement.toFloat() / target.toFloat()) * 100
                        )
                    }%",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.text_target),
                        style = MaterialTheme.typography.body2
                    )
                    Text(
                        text = "$target",
                        style = MaterialTheme.typography.body2
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.text_achievement),
                        style = MaterialTheme.typography.body2
                    )
                    Text(
                        text = "$target",
                        style = MaterialTheme.typography.body2
                    )
                }
            }
            Icon(
                imageVector = Icons.Rounded.LocalDrink,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            )
        }
    }
}
