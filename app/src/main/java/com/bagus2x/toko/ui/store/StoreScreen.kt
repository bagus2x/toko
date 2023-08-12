package com.bagus2x.toko.ui.store

import androidx.camera.core.CameraSelector
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.LocationSearching
import androidx.compose.material.icons.rounded.Navigation
import androidx.compose.material.icons.rounded.RotateLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bagus2x.toko.R
import com.bagus2x.toko.model.Location
import com.bagus2x.toko.ui.main.LocalSnackbar
import com.bagus2x.toko.ui.store.components.CameraView
import com.bagus2x.toko.ui.store.components.CoolIconButton
import com.bagus2x.toko.ui.store.components.RequestCameraPermission
import com.bagus2x.toko.ui.store.components.StoreDetail
import com.bagus2x.toko.ui.store.components.rememberCameraState
import kotlinx.coroutines.flow.collectLatest
import java.io.File

@Composable
fun StoreScreen(
    viewModel: StoreViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    RequestCameraPermission {
        StoreScreen(
            stateProvider = { state },
            setPhoto = viewModel::setPhoto,
            setMyLocation = viewModel::setMyLocation,
            visit = viewModel::visit,
            resetLocation = viewModel::resetLocation,
            navigateToDashboardScreen = { storeId ->
                navController.navigate("dashboard/$storeId") {
                    popUpTo("store_list") {
                        inclusive = true
                    }
                }
            },
            navigateUp = navController::navigateUp,
            consumeMessage = viewModel::consumeMessage
        )
    }
}

@Composable
fun StoreScreen(
    stateProvider: () -> StoreState,
    setPhoto: (File?) -> Unit,
    setMyLocation: (Location?) -> Unit,
    resetLocation: () -> Unit,
    visit: () -> Unit,
    navigateToDashboardScreen: (String) -> Unit,
    navigateUp: () -> Unit,
    consumeMessage: () -> Unit
) {
    val state = stateProvider()
    val density = LocalDensity.current
    val statusBarHeight = with(density) { WindowInsets.statusBars.getTop(this).toDp() }
    val snackbar = LocalSnackbar.current
    LaunchedEffect(Unit) {
        snapshotFlow { stateProvider() }.collectLatest { state ->
            if (state.visitSuccess && state.store?.storeId != null) {
                navigateToDashboardScreen(state.store.storeId)
            }
            if (state.message.isNotBlank()) {
                snackbar.show(state.message)
                consumeMessage()
            }
        }
    }
    Box {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            val (topBarRef, photoRef, buttonsRef, detailRef, visitsRef) = createRefs()
            val cameraState = rememberCameraState()
            AnimatedContent(
                targetState = state.photo != null,
                label = "camera",
                modifier = Modifier
                    .constrainAs(photoRef) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        height = Dimension.value(statusBarHeight + 300.dp)
                    }
            ) { captured ->
                if (captured) {
                    AsyncImage(
                        model = state.photo,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray)
                    )
                } else {
                    CameraView(
                        cameraState = cameraState,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray)
                    )
                }
            }
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = stringResource(R.string.text_verify_store),
                            style = MaterialTheme.typography.body1
                        )
                        Text(
                            text = "User A",
                            style = MaterialTheme.typography.caption
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
                backgroundColor = Color.Transparent,
                contentColor = Color.White,
                modifier = Modifier.constrainAs(topBarRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top, statusBarHeight)
                    end.linkTo(parent.end)
                },
                elevation = 0.dp
            )
            if (state.store != null) {
                StoreDetail(
                    store = state.store,
                    myLocation = state.myLocation,
                    resetLocation = resetLocation,
                    modifier = Modifier.constrainAs(detailRef) {
                        start.linkTo(parent.start, 4.dp)
                        top.linkTo(photoRef.bottom, (-24).dp)
                        end.linkTo(parent.end, 4.dp)
                        width = Dimension.fillToConstraints
                    },
                )
            }
            Row(
                modifier = Modifier
                    .constrainAs(buttonsRef) {
                        top.linkTo(detailRef.top, (-24).dp)
                        end.linkTo(parent.end, 16.dp)
                        height = Dimension.preferredWrapContent
                    },
                horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.End)
            ) {
                CoolIconButton(
                    onClick = navigateUp
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Navigation,
                        contentDescription = null
                    )
                }
                CoolIconButton(
                    onClick = {
                        if (state.photo == null) {
                            cameraState.takePhoto(
                                onError = { e ->
                                    val message = e.message ?: return@takePhoto
                                    snackbar.show(message)
                                },
                                onImageCaptured = setPhoto
                            )
                        } else {
                            setPhoto(null)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CameraAlt,
                        contentDescription = null
                    )
                }
                if (state.photo == null) {
                    CoolIconButton(
                        onClick = {
                            cameraState.lensFacing =
                                if (cameraState.lensFacing == CameraSelector.LENS_FACING_BACK) {
                                    CameraSelector.LENS_FACING_FRONT
                                } else {
                                    CameraSelector.LENS_FACING_BACK
                                }
                        }
                    ) {
                        val rotation by animateFloatAsState(
                            targetValue = if (cameraState.lensFacing == CameraSelector.LENS_FACING_BACK) 0f else 180f,
                            label = "camera_rotation"
                        )
                        Icon(
                            imageVector = Icons.Rounded.RotateLeft,
                            contentDescription = null,
                            modifier = Modifier.graphicsLayer {
                                rotationZ = rotation
                            }
                        )
                    }
                }
                CoolIconButton(onClick = { setMyLocation(null) }) {
                    Icon(
                        imageVector = Icons.Rounded.LocationSearching,
                        contentDescription = null
                    )
                }
            }
            Row(
                modifier = Modifier
                    .constrainAs(visitsRef) {
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(detailRef.bottom, 16.dp)
                        end.linkTo(parent.end, 16.dp)
                        width = Dimension.fillToConstraints
                    }
                    .navigationBarsPadding(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = navigateUp,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.error
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(R.string.text_button_no_visit))
                }
                Button(
                    onClick = visit,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(R.string.text_button_visit))
                }
            }
        }
        if (state.loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
