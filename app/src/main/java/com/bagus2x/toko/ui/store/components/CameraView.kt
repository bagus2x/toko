package com.bagus2x.toko.ui.store.components

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.bagus2x.toko.ui.utils.UriUtils
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun CameraView(
    cameraState: CameraState,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val previewView = remember { PreviewView(context) }
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner, cameraState.cameraSelector) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraState.cameraSelector,
            cameraState.preview,
            cameraState.imageCapture
        )

        cameraState.preview.setSurfaceProvider(previewView.surfaceProvider)
    }
    Box(
        modifier = modifier
            .clip(RectangleShape)
            .background(Color.Black)
    ) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize(),
        )
    }
}

class CameraState(
    private val context: Context
) {
    var lensFacing by mutableIntStateOf(CameraSelector.LENS_FACING_BACK)
    val preview = Preview.Builder().build()
    val imageCapture: ImageCapture = ImageCapture.Builder().build()
    val cameraSelector by derivedStateOf {
        CameraSelector.Builder()
            .requireLensFacing(lensFacing)
            .build()
    }
    private val executor = Executors.newSingleThreadExecutor()

    fun takePhoto(
        onImageCaptured: (File) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val name = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            context.contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ).build()

        imageCapture.takePicture(
            outputOptions,
            executor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exception: ImageCaptureException) {
                    Timber.e("Take photo error: ", exception)
                    onError(exception)
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val uri = outputFileResults.savedUri
                    Timber.i("onImageSaved uri $uri")
                    if (uri != null) {
                        val file = UriUtils.convertUriToFile(context, uri)
                        Timber.i("onImageSaved file $file ${file.exists()}")
                        onImageCaptured(file)
                    } else {
                        onError(RuntimeException("Uri is null"))
                    }
                }
            })
    }
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }

@Composable
fun rememberCameraState(): CameraState {
    val context = LocalContext.current
    return remember(context) { CameraState(context) }
}
