package com.bagus2x.toko.ui.store.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bagus2x.toko.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestCameraPermission(
    required: Boolean = true,
    content: @Composable () -> Unit
) {
    val locationPermissionsState = rememberPermissionState(android.Manifest.permission.CAMERA)

    if (locationPermissionsState.status.isGranted && required) {
        content()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val textToShow = if (locationPermissionsState.status.shouldShowRationale) {
                stringResource(R.string.text_camera_permission_retionale)
            } else {
                stringResource(R.string.text_camera_permission_description)
            }
            Text(
                text = textToShow,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { locationPermissionsState.launchPermissionRequest() }) {
                Text(stringResource(R.string.text_request_permission))
            }
        }
    }
}
