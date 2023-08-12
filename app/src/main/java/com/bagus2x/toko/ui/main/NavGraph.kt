package com.bagus2x.toko.ui.main

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bagus2x.toko.ui.dashboard.DashboardScreen
import com.bagus2x.toko.ui.home.HomeScreen
import com.bagus2x.toko.ui.signin.SignInScreen
import com.bagus2x.toko.ui.store.StoreScreen
import com.bagus2x.toko.ui.storelist.StoreListScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "signin"
    ) {
        composable("signin") {
            LightIconsStatusBar()
            SignInScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
        composable("home") {
            LightIconsStatusBar()
            HomeScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
        composable("store_list") {
            DarkIconsStatusBar(lightIconsInDarkMode = true)
            StoreListScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
        composable("store/{storeId}") {
            LightIconsStatusBar()
            StoreScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
        composable("dashboard/{storeId}") {
            DarkIconsStatusBar()
            DashboardScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
    }
}


@Composable
fun LightIconsStatusBar(darkIconsInDarkMode: Boolean = false) {
    val systemUiController = rememberSystemUiController()
    val isLightMode = MaterialTheme.colors.isLight
    LaunchedEffect(Unit) {
        if (isLightMode) {
            systemUiController.setStatusBarColor(
                color = Color.Transparent,
                darkIcons = false
            )
        }
        if (darkIconsInDarkMode && !isLightMode) {
            systemUiController.setStatusBarColor(
                color = Color.Transparent,
                darkIcons = true
            )
        }
    }
}

@Composable
fun DarkIconsStatusBar(lightIconsInDarkMode: Boolean = false) {
    val systemUiController = rememberSystemUiController()
    val isLightMode = MaterialTheme.colors.isLight
    LaunchedEffect(Unit) {
        if (isLightMode) {
            systemUiController.setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = true,
                isNavigationBarContrastEnforced = false
            )
        }
        if (lightIconsInDarkMode && !isLightMode) {
            systemUiController.setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = false,
                isNavigationBarContrastEnforced = false
            )
        }
    }
}
