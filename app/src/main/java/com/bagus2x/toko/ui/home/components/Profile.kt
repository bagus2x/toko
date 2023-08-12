package com.bagus2x.toko.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Profile(
    name: String,
    role: String,
    nik: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium)
        )
        Text(
            text = role,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium)
        )
        Text(
            text = nik,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.disabled)
        )
    }
}
