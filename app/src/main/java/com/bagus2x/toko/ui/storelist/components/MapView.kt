package com.bagus2x.toko.ui.storelist.components

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.preference.PreferenceManager
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

@Composable
fun rememberMapViewWithLifecycle(onLoad: (MapView.() -> Unit)? = null): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = View.generateViewId()
        }
    }

    // Makes MapView follow the lifecycle of this composable
    val lifecycleObserver = rememberMapLifecycleObserver(mapView, onLoad)
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }

    return mapView
}

@Composable
fun rememberMapLifecycleObserver(
    mapView: MapView,
    onLoad: (MapView.() -> Unit)? = null,
): LifecycleEventObserver =
    remember(mapView) {
        LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    mapView.apply {
                        onLoad?.invoke(this)
                        onResume()
                    }
                }
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                else -> {}
            }
        }
    }

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    onLoad: (MapView.() -> Unit)? = null,
    onUpdate: (MapView.() -> Unit)? = null,
    enableMyLocation: Boolean = false
) {
    val mapViewState = rememberMapViewWithLifecycle {
        if (enableMyLocation) {
            overlays.add(MyLocationNewOverlay(this))
        }
        onLoad?.invoke(this)
    }
    val context = LocalContext.current
    LaunchedEffect(context) {
        Configuration
            .getInstance()
            .load(context, PreferenceManager.getDefaultSharedPreferences(context))
    }
    AndroidView(
        factory = { mapViewState },
        modifier = modifier,
        update = { mapView -> onUpdate?.invoke(mapView) }
    )
}
