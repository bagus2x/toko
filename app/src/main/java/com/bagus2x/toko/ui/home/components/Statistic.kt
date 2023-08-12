package com.bagus2x.toko.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bagus2x.toko.R
import com.bagus2x.toko.ui.theme.Green500
import com.bagus2x.toko.ui.theme.Red500
import com.bagus2x.toko.ui.theme.Orange500
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun Statistic(
    totalStore: Int,
    actualStore: Int,
    percentage: String,
    modifier: Modifier = Modifier,
    date: LocalDate = LocalDate.now(),
) {
    Card(
        modifier = modifier,
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(
                    R.string.text_visit_in,
                    date.month.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                    date.year
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_total_store),
                            contentDescription = null,
                            tint = Red500
                        )
                        Text(
                            text = "$totalStore",
                            style = MaterialTheme.typography.h6,
                            color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium),
                        )
                    }
                    Text(
                        text = stringResource(R.string.text_total_store),
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.disabled),
                        textAlign = TextAlign.Center
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_actual_store),
                            contentDescription = null,
                            tint = Green500
                        )
                        Text(
                            text = "$actualStore",
                            style = MaterialTheme.typography.h6,
                            color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium)
                        )
                    }
                    Text(
                        text = stringResource(R.string.text_actual_store),
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.disabled),
                        textAlign = TextAlign.Center
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_percentage),
                            contentDescription = null,
                            tint = Orange500
                        )
                        Text(
                            text = percentage,
                            style = MaterialTheme.typography.h6,
                            color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium)
                        )
                    }
                    Text(
                        text = stringResource(R.string.text_percentage),
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.disabled),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

