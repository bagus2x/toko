package com.bagus2x.toko.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bagus2x.toko.R
import com.bagus2x.toko.ui.theme.Orange500

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Menu(
    onVisitClicked: () -> Unit,
    onSignOutClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.text_menu),
            style = MaterialTheme.typography.body1,
            color = Orange500
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterHorizontally)
        ) {
            MenuItem(
                icon = R.drawable.ic_visit,
                label = stringResource(R.string.text_visit),
                onClick = onVisitClicked
            )
            MenuItem(
                icon = R.drawable.ic_taget,
                label = stringResource(R.string.text_target_install),
            )
            MenuItem(
                icon = R.drawable.ic_dashboard,
                label = stringResource(R.string.text_dashboard)
            )
            MenuItem(
                icon = R.drawable.ic_transmission_history,
                label = stringResource(R.string.text_transmission_history)
            )
            MenuItem(
                icon = R.drawable.ic_logout,
                label = stringResource(R.string.text_logout),
                onClick = onSignOutClicked
            )
        }
    }
}

@Composable
fun MenuItem(
    icon: Int,
    label: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colors.primary.copy(alpha = 0.1f))
                .clickable { onClick?.invoke() }
        ) {
            AsyncImage(
                model = icon,
                contentDescription = label,
                modifier = Modifier
                    .padding(16.dp)
                    .size(32.dp),
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.disabled),
            textAlign = TextAlign.Center
        )
    }
}
