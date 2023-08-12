package com.bagus2x.toko.ui.dashboard.components

import androidx.annotation.DrawableRes
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
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bagus2x.toko.R
import com.bagus2x.toko.ui.theme.Orange500
import com.bagus2x.toko.ui.theme.TokoTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Menu(
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
                icon = R.drawable.ic_information,
                label = stringResource(R.string.text_information),
                color = Color(0xFF673AB7)
            )
            MenuItem(
                icon = R.drawable.ic_product_check,
                label = stringResource(R.string.text_product_check),
                color = Color(0xFFFF9800)
            )
            MenuItem(
                icon = R.drawable.ic_chart,
                label = stringResource(R.string.text_sku_promo),
                color = Color(0xFF2196F3)
            )
            MenuItem(
                icon = R.drawable.ic_product_check,
                label = stringResource(R.string.text_oos),
                color = Color(0xFFE91E63)
            )
            MenuItem(
                icon = R.drawable.ic_investment,
                label = stringResource(R.string.text_store_investment),
                color = Color(0xFF3F51B5)
            )
        }
    }
}

@Composable
fun MenuItem(
    @DrawableRes
    icon: Int,
    label: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(color)
                .clickable { onClick?.invoke() }
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = label,
                modifier = Modifier
                    .padding(16.dp)
                    .size(32.dp),
                tint = Color.White
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

@Preview
@Composable
fun PreviewMenu() {
    TokoTheme {
        Menu(
            modifier = Modifier.fillMaxWidth()
        )
    }
}
