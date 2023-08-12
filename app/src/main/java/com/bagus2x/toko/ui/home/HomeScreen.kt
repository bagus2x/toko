package com.bagus2x.toko.ui.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeableState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.DebugFlags
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bagus2x.toko.R
import com.bagus2x.toko.ui.home.components.Menu
import com.bagus2x.toko.ui.home.components.Profile
import com.bagus2x.toko.ui.home.components.Statistic
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController
) {
    HomeScreen(
        navigateToStoreListScreen = {
            navController.navigate("store_list")
        },
        signOut = {}
    )
}

enum class SwipingStates {
    EXPANDED,
    COLLAPSED
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navigateToStoreListScreen: () -> Unit,
    signOut: () -> Unit
) {
    BoxWithConstraints {
        val swipingState = rememberSwipeableState(initialValue = SwipingStates.EXPANDED)
        val heightInPx = with(LocalDensity.current) { maxHeight.toPx() }
        val nestedScrollConnection = rememberNestedScrollConnection(swipingState)
        val computedProgress by remember {
            derivedStateOf {
                if (swipingState.progress.to == SwipingStates.COLLAPSED)
                    swipingState.progress.fraction
                else
                    1f - swipingState.progress.fraction
            }
        }
        val motionScene = rememberMotionScene()
        MotionLayout(
            modifier = Modifier
                .fillMaxSize()
                .swipeable(
                    state = swipingState,
                    thresholds = { _, _ ->
                        FractionalThreshold(0.05f)
                    },
                    orientation = Orientation.Vertical,
                    anchors = mapOf(
                        0f to SwipingStates.COLLAPSED,
                        heightInPx to SwipingStates.EXPANDED,
                    )
                )
                .nestedScroll(nestedScrollConnection),
            motionScene = motionScene,
            progress = computedProgress,
            debugFlags = DebugFlags.None
        ) {
            Profile(
                name = "Miftha Khairulnisa",
                role = "{...}",
                nik = "MD000001",
                modifier = Modifier
                    .fillMaxWidth()
                    .layoutId("profile")
            )
            AsyncImage(
                model = R.drawable.bg_header,
                contentDescription = null,
                modifier = Modifier
                    .layoutId("header")
                    .background(MaterialTheme.colors.primary),
                contentScale = ContentScale.Crop
            )
            Crossfade(
                targetState = computedProgress < 0.9,
                label = "title",
                modifier = Modifier.layoutId("title"),
            ) { isTitle ->
                if (isTitle) {
                    Text(
                        text = stringResource(R.string.text_main_menu),
                        style = MaterialTheme.typography.h6,
                        color = Color.White
                    )
                } else {
                    Text(
                        text = "Miftha Khairulnisa",
                        style = MaterialTheme.typography.h6,
                        color = Color.White
                    )
                }
            }
            IconButton(
                onClick = {},
                modifier = Modifier.layoutId("refresh")
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = null,
                    tint = MaterialTheme.colors.background
                )
            }
            val scope = rememberCoroutineScope()
            AsyncImage(
                model = R.drawable.placeholder_woman,
                contentDescription = null,
                modifier = Modifier
                    .layoutId("photo")
                    .clip(CircleShape)
                    .background(Color.White)
                    .drawWithContent {
                        drawContent()
                        drawCircle(
                            color = Color.White,
                            style = Stroke(width = 4.dp.toPx())
                        )
                    }
                    .clickable {
                        if (computedProgress == 1f) {
                            scope.launch { swipingState.animateTo(SwipingStates.EXPANDED) }
                        }
                    },
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .layoutId("list")
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Statistic(
                    totalStore = 150,
                    actualStore = 150,
                    percentage = "50%",
                    modifier = Modifier.fillMaxWidth()
                )
                Menu(
                    onVisitClicked = navigateToStoreListScreen,
                    onSignOutClicked = signOut,
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                )
            }
        }
    }
}

@Composable
fun rememberMotionScene(): MotionScene {
    val density = LocalDensity.current
    val statusBarHeight = with(density) { WindowInsets.statusBars.getTop(density).toDp() }
    return remember(statusBarHeight, density) {
        MotionScene {
            val headerRef = createRefFor("header")
            val titleRef = createRefFor("title")
            val photoRef = createRefFor("photo")
            val refreshRef = createRefFor("refresh")
            val profileRef = createRefFor("profile")
            val list = createRefFor("list")
            defaultTransition(
                from = constraintSet {
                    val topBarGuideLineTop = createGuidelineFromTop(statusBarHeight)
                    val topBarGuideLineBottom = createGuidelineFromTop(statusBarHeight + 56.dp)
                    constrain(headerRef) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        height = Dimension.value(statusBarHeight + 160.dp)
                    }
                    constrain(titleRef) {
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(topBarGuideLineTop)
                        bottom.linkTo(topBarGuideLineBottom)
                    }
                    constrain(refreshRef) {
                        top.linkTo(topBarGuideLineTop)
                        end.linkTo(parent.end, 4.dp)
                        bottom.linkTo(topBarGuideLineBottom)
                    }
                    constrain(photoRef) {
                        start.linkTo(headerRef.start)
                        top.linkTo(headerRef.bottom)
                        end.linkTo(headerRef.end)
                        bottom.linkTo(headerRef.bottom)
                        width = Dimension.value(120.dp)
                        height = Dimension.value(120.dp)
                    }
                    constrain(profileRef) {
                        start.linkTo(parent.start)
                        top.linkTo(photoRef.bottom, 16.dp)
                        end.linkTo(parent.end)
                    }
                    constrain(list) {
                        start.linkTo(parent.start)
                        top.linkTo(profileRef.bottom)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                },
                to = constraintSet {
                    val topBarGuideLineTop = createGuidelineFromTop(statusBarHeight)
                    val topBarGuideLineBottom = createGuidelineFromTop(statusBarHeight + 56.dp)
                    constrain(headerRef) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        height = Dimension.value(statusBarHeight + 56.dp)
                    }
                    constrain(photoRef) {
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(topBarGuideLineTop)
                        bottom.linkTo(topBarGuideLineBottom)
                        width = Dimension.value(48.dp)
                        height = Dimension.value(48.dp)
                    }
                    constrain(titleRef) {
                        start.linkTo(photoRef.end, 16.dp)
                        top.linkTo(photoRef.top)
                        bottom.linkTo(photoRef.bottom)
                    }
                    constrain(refreshRef) {
                        top.linkTo(topBarGuideLineTop)
                        end.linkTo(parent.end, 4.dp)
                        bottom.linkTo(topBarGuideLineBottom)
                    }
                    constrain(profileRef) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(headerRef.bottom)
                    }
                    constrain(list) {
                        start.linkTo(parent.start)
                        top.linkTo(headerRef.bottom)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberNestedScrollConnection(swipingState: SwipeableState<SwipingStates>): NestedScrollConnection {
    return remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val delta = available.y
                return if (delta < 0) {
                    swipingState.performDrag(delta).toOffset()
                } else {
                    Offset.Zero
                }
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val delta = available.y
                return swipingState.performDrag(delta).toOffset()
            }

            override suspend fun onPostFling(
                consumed: Velocity,
                available: Velocity
            ): Velocity {
                swipingState.performFling(velocity = available.y)
                return super.onPostFling(consumed, available)
            }

            private fun Float.toOffset() = Offset(0f, this)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    MaterialTheme {
        HomeScreen(
            navigateToStoreListScreen = {},
            signOut = {}
        )
    }
}
