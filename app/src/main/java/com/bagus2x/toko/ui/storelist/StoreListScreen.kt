package com.bagus2x.toko.ui.storelist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bagus2x.toko.R
import com.bagus2x.toko.model.Store
import com.bagus2x.toko.ui.storelist.components.MapView
import com.bagus2x.toko.ui.storelist.components.RequestLocationPermission
import com.bagus2x.toko.ui.storelist.components.Store
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

@Composable
fun StoreListScreen(
    viewModel: StoreListViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    RequestLocationPermission {
        LaunchedEffect(Unit) {
            viewModel.setGpsEnabled()
        }
        StoreListScreen(
            stateProvider = { state },
            setQuery = viewModel::setQuery,
            navigateToStoreScreen = { storeId ->
                navController.navigate("store/${storeId}")
            },
            navigateUp = navController::navigateUp
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StoreListScreen(
    stateProvider: () -> StoreListState,
    setQuery: (String) -> Unit,
    navigateToStoreScreen: (String) -> Unit,
    navigateUp: () -> Unit,
) {
    val state = stateProvider()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = stringResource(R.string.text_list_store),
                            style = MaterialTheme.typography.body1
                        )
                        Text(
                            text = "User A",
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.disabled)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null
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
                contentColor = MaterialTheme.colors.onBackground
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier.padding(contentPadding)
        ) {
            Box {
                MapView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RectangleShape)
                        .height(360.dp),
                    onUpdate = {
                        state.stores.forEach { store ->
                            val lat = store.latitude?.toDoubleOrNull() ?: return@forEach
                            val lng = store.longitude?.toDoubleOrNull() ?: return@forEach
                            val marker = Marker(this).apply {
                                title = store.storeName
                                position = GeoPoint(lat, lng)
                            }
                            overlays.add(marker)
                        }
                    },
                    onLoad = {
                        setMultiTouchControls(true)
                        isTilesScaledToDpi = true
                        minZoomLevel = 5.0
                        maxZoomLevel = 18.0
                        controller.animateTo(GeoPoint(-6.3121698, 106.9544447), 15.0, 1)
                    },
                    enableMyLocation = true
                )
                OutlinedTextField(
                    value = state.query,
                    onValueChange = setQuery,
                    placeholder = {
                        Text(text = stringResource(R.string.text_search_distributor))
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .align(Alignment.TopStart),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = MaterialTheme.colors.background.copy(alpha = ContentAlpha.medium)
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = null
                        )
                    },
                    singleLine = true
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(
                    items = state.stores,
                    key = Store::storeId
                ) { store ->
                    Store(
                        store = store,
                        onClick = {
                            navigateToStoreScreen(store.storeId)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItemPlacement()
                    )
                }
            }
        }
    }
}
