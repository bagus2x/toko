package com.bagus2x.toko.ui.store.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EventNote
import androidx.compose.material.icons.rounded.LocalConvenienceStore
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bagus2x.toko.R
import com.bagus2x.toko.model.Location
import com.bagus2x.toko.model.Store
import com.bagus2x.toko.ui.theme.Orange500
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun StoreDetail(
    store: Store,
    myLocation: Location?,
    resetLocation: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = modifier.padding(horizontal = 12.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Rounded.LocationOn,
                    contentDescription = null,
                    tint = Orange500
                )
                Text(
                    text = myLocation?.display
                        ?: stringResource(R.string.text_location_does_not_match),
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.error,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Button(onClick = resetLocation) {
                    Text(text = stringResource(R.string.text_reset))
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = Icons.Rounded.LocalConvenienceStore,
                    contentDescription = null,
                    tint = Orange500
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = store.storeName,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface
                    )
                    Text(
                        text = store.address.ifBlank { "Address not found" },
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Tipe Outlet",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = ": {data}",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                            modifier = Modifier.weight(1f),
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Tipe Display",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = ": {data}",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Tipe Outlet",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = ": {data}",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Sub Tipe Display",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = ": {data}",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "ERTM",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = ": {data}",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Pareto",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = ": {data}",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "E-Merchandising",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = ": {data}",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.EventNote,
                    contentDescription = null,
                    tint = Orange500
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val lastVisit = remember {
                        val dateFormatter =
                            DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.getDefault())
                        val lastVisit = store.visitHistory.lastOrNull() ?: return@remember ""
                        lastVisit.visitedAt.format(dateFormatter)
                    }
                    Text(
                        text = "Last Visit",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface
                    )
                    Text(
                        text = lastVisit,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
                    )
                }
            }
        }
    }
}
