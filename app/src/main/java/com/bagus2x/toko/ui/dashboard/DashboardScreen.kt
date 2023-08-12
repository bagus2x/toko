package com.bagus2x.toko.ui.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bagus2x.toko.R
import com.bagus2x.toko.model.Store
import com.bagus2x.toko.ui.dashboard.components.Menu
import com.bagus2x.toko.ui.dashboard.components.Statistic
import com.bagus2x.toko.ui.dashboard.components.Store
import java.time.Month

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    navController: NavController
) {
    val state by viewModel.store.collectAsState()
    DashboardScreen(
        stateProvider = { state },
        finish = navController::navigateUp
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DashboardScreen(
    stateProvider: () -> Store?,
    finish: () -> Unit
) {
    val store = stateProvider()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = stringResource(R.string.text_main_menu),
                            style = MaterialTheme.typography.body1
                        )
                        Text(
                            text = "User A",
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.disabled)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.ic_transmission_history),
                            contentDescription = null,
                            tint = Color(0xFF00BCD4)
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.onBackground,
            )
        },
        modifier = Modifier.systemBarsPadding(),
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .padding(vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.text_dashboard_marque),
                modifier = Modifier
                    .basicMarquee()
                    .padding(horizontal = 16.dp),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary
            )
            if (store != null) {
                Store(
                    store = store,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Menu(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Statistic(
                        name = "OSA",
                        month = Month.SEPTEMBER,
                        year = 2020,
                        target = 40,
                        achievement = 20,
                        color = Color(0xFFFF9800),
                        modifier = Modifier.width(180.dp)
                    )
                }
                item {
                    Statistic(
                        name = "NDP",
                        month = Month.SEPTEMBER,
                        year = 2020,
                        target = 100,
                        achievement = 80,
                        color = Color(0xFF00BCD4),
                        modifier = Modifier.width(180.dp)
                    )
                }
                item {
                    Statistic(
                        name = "PPP",
                        month = Month.SEPTEMBER,
                        year = 2020,
                        target = 100,
                        achievement = 60,
                        color = Color(0xFF3F51B5),
                        modifier = Modifier.width(180.dp)
                    )
                }
            }
            Button(
                onClick = finish,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.text_finish))
            }
        }
    }
}
