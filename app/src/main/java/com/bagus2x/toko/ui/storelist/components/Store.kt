package com.bagus2x.toko.ui.storelist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bagus2x.toko.model.Store
import com.bagus2x.toko.ui.theme.Green500

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Store(
    store: Store,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = store.storeName,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )
                Text(
                    text = store.regionName,
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.disabled)
                )
                Text(
                    text = store.address,
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.disabled)
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (store.visitHistory.isNotEmpty()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.CheckCircle,
                            contentDescription = null,
                            tint = Green500
                        )
                        Text(
                            text = "Perfect Store",
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.disabled)
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!store.distance.isNullOrBlank()) {
                        Icon(
                            imageVector = Icons.Rounded.LocationOn,
                            contentDescription = null,
                            tint = Green500
                        )
                        Text(
                            text = store.distance,
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.disabled)
                        )
                    }
                }
            }
        }
    }
}

