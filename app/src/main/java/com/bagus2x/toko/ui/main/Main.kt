package com.bagus2x.toko.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@Composable
fun Main() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val snackbar = remember {
        object : Snackbar {
            override fun show(message: String, actionLabel: String?, duration: SnackbarDuration) {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(message, actionLabel, duration)
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = it,
                modifier = Modifier.imePadding()
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            WindowInsets.statusBars
            CompositionLocalProvider(
                LocalSnackbar provides snackbar
            ) {
                NavGraph()
            }
        }
    }
}

interface Snackbar {

    fun show(
        message: String,
        actionLabel: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Short
    )
}

val LocalSnackbar = compositionLocalOf<Snackbar> {
    object : Snackbar {
        override fun show(message: String, actionLabel: String?, duration: SnackbarDuration) {}
    }
}

