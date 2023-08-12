package com.bagus2x.toko.ui.dashboard.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bagus2x.toko.R
import com.bagus2x.toko.model.Store

@Composable
fun Store(
    store: Store,
    modifier: Modifier = Modifier
) {
    val visit = store.visitHistory.lastOrNull() ?: return
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Surface(
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colors.primary,
            contentColor = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            shape = CircleShape,
                            color = Color.White
                        )
                ) {
                    AsyncImage(
                        model = visit.photo,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Column {
                    Text(
                        text = "ST00001",
                        style = MaterialTheme.typography.body2,
                        color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                    )
                    Text(
                        text = store.storeName,
                        style = MaterialTheme.typography.body1,
                    )
                    Text(
                        text = "Cluster: small",
                        style = MaterialTheme.typography.body1,
                    )
                    Text(
                        text = "TT Regular - ERT Big - Pareto",
                        style = MaterialTheme.typography.body1,
                    )
                }
            }
        }
    }
}
