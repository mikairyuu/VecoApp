package com.veco.vecoapp.android.ui.screen.misc

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.veco.vecoapp.MR
import com.veco.vecoapp.VecoAndroidApp
import com.veco.vecoapp.android.R
import com.veco.vecoapp.android.ui.component.misc.VecoButton
import com.veco.vecoapp.android.ui.component.misc.VecoHeadline
import com.veco.vecoapp.android.ui.enums.ResultState
import com.veco.vecoapp.android.ui.navigation.Screen
import com.veco.vecoapp.commonLog
import com.veco.vecoapp.presentation.misc.ConfirmViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import kotlin.random.Random

@Composable
fun ConfirmationRoute(
    navController: NavHostController,
    taskId: Int,
    viewModel: ConfirmViewModel = viewModel()
) {
    val selectedImageIds = remember {
        mutableStateListOf<Int?>()
    }
    val imageCount = remember {
        mutableStateOf(0)
    }
    Column(modifier = Modifier.padding(16.dp, 24.dp, 16.dp, 16.dp)) {
        Headline()
        ImagePicker(viewModel, selectedImageIds, imageCount)
        Box(Modifier.fillMaxSize()) {
            VecoButton(
                text = stringResource(MR.strings.button_send.resourceId),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                viewModel.proceed(
                    selectedImageIds.subList(0, imageCount.value).toList().requireNoNulls(),
                    taskId = taskId,
                    navigateHome = { navController.navigate(Screen.Home.route) }
                )
            }
        }
    }
}

@Composable
fun Headline() {
    val desc = buildAnnotatedString {
        append(stringResource(MR.strings.task_info_1.resourceId))
        append(" ")
        pushStyle(SpanStyle(fontWeight = Bold))
        append(stringResource(MR.strings.task_info_2.resourceId))
        toString()
    }
    VecoHeadline(text1 = stringResource(MR.strings.task_confirm.resourceId), text2 = desc)
}

@Composable
fun ImagePicker(
    viewModel: ConfirmViewModel,
    imageIds: SnapshotStateList<Int?>,
    imageCount: MutableState<Int>
) {
    val ctx = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val result = remember {
        mutableStateListOf<ResultState>()
    }
    val imageUri = remember {
        mutableStateListOf<Uri?>()
    }

    LaunchedEffect(Unit) {
        repeat(5) {
            imageUri.add(null)
            result.add(ResultState.NONE)
            imageIds.add(null)
        }
    }

    val uploadPhoto: (Int, Uri) -> Unit = { n: Int, uri: Uri ->
        result[n] = ResultState.LOADING(0f)
        val stream = ctx.contentResolver.openInputStream(uri)!!
        coroutineScope.launch (Dispatchers.IO) {
            val webpFilePath = encodeToWebp(stream)
            viewModel.uploadPicture(
                File(webpFilePath).readBytes(),
                webpFilePath.substringAfterLast('/'),
                onDone = {
                    if (it == null) {
                        result[n] = ResultState.ERROR
                    } else {
                        result[n] = ResultState.SUCCESS
                        imageIds[n] = it
                    }
                }
            ) {
                result[n] = ResultState.LOADING(it)
            }
        }
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                val n = imageCount.value
                imageCount.value++
                imageUri[n] = uri
                uploadPhoto(n, uri)
            }
        }
    )

    PhotoGrid(
        photos = imageUri,
        imagePicker = imagePicker,
        imageCount = imageCount,
        result = result,
        uploadPhoto = uploadPhoto
    )
}

fun encodeToWebp(inputStream: InputStream): String {
    val bitmap = BitmapFactory.decodeStream(inputStream)
    inputStream.close()
    var path: String
    do {
        path =
            VecoAndroidApp.INSTANCE.applicationContext.filesDir.absolutePath + Random.nextInt() + "_cache.webp"
        val exists = File(path).exists()
    } while (exists)
    val out = FileOutputStream(path)
    bitmap.compress(Bitmap.CompressFormat.WEBP, 70, out)
    out.close()
    return path
}

@Composable
fun PhotoGrid(
    photos: SnapshotStateList<Uri?>,
    imagePicker: ManagedActivityResultLauncher<String, Uri?>,
    imageCount: MutableState<Int>,
    result: SnapshotStateList<ResultState>,
    uploadPhoto: (Int, Uri) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(top = 24.dp),
        modifier = Modifier.fillMaxWidth()

    ) {
        item {
            Box(
                modifier = Modifier
                    .size(104.dp, 104.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                AddImg(imagePicker = imagePicker, imageCount = imageCount)
            }
        }
        itemsIndexed(photos) { i, photo ->
            val removePhoto = {
                photos[i] = null
                result[i] = ResultState.NONE
                imageCount.value--
            }

            if (photo != null) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(104.dp, 104.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color.Gray)
                ) {
                    AsyncImage(
                        modifier = Modifier.alpha(if (result[i] is ResultState.LOADING) 0.5f else 1f),
                        contentScale = ContentScale.Inside,
                        model = photo,
                        contentDescription = null
                    )
                    when (result[i]) {
                        is ResultState.LOADING -> {
                            Box(modifier = Modifier.padding(32.dp)) {
                                CircularProgressBar(
                                    percentage = (result[i] as ResultState.LOADING).progress,
                                    imageCount = imageCount,
                                    result = result
                                )
                            }
                        }
                        is ResultState.SUCCESS -> {
                            Box(modifier = Modifier.align(Alignment.TopEnd)) {
                                CornerImage(id = R.drawable.ic_delete_button) {
                                    removePhoto()
                                }
                            }
                        }
                        is ResultState.ERROR -> {
                            ImageButton(R.drawable.ic_resend) {
                                uploadPhoto(i, photo)
                            }
                            Box(modifier = Modifier.align(Alignment.TopEnd)) {
                                CornerImage(id = R.drawable.ic_error_button) {
                                    removePhoto()
                                }
                            }
                        }
                        ResultState.CANCELED -> {
                            removePhoto()
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}

@Composable
fun CornerImage(id: Int, onClick: () -> Unit) {
    Box(modifier = Modifier.clickable(onClick = onClick))
    Image(
        painter = painterResource(id = id),
        contentDescription = "Delete image",
        modifier = Modifier
            .width(20.dp)
            .height(20.dp)
            .padding(4.dp)
    )
}

@Composable
fun AddImg(
    imagePicker: ManagedActivityResultLauncher<String, Uri?>,
    imageCount: MutableState<Int>
) {
    Button(
        onClick = {
            if (imageCount.value < 5) {
                imagePicker.launch("image/*")
            }
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        modifier = Modifier.fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painterResource(R.drawable.ic_baseline_camera_alt_24),
                null
            )
            Text(text = "Загрузить")
        }
    }
}

@Composable
fun CircularProgressBar(
    percentage: Float,
    radius: Dp = 40.dp,
    color: Color = Color.White,
    strokeWidth: Dp = 4.dp,
    result: SnapshotStateList<ResultState>,
    imageCount: MutableState<Int>
) {
    val curPercentage by animateFloatAsState(
        targetValue = percentage,
        animationSpec = tween(durationMillis = 100)
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(radius)
            .clip(CircleShape)
    ) {
        ImageButton(R.drawable.ic_cross, { result[imageCount.value - 1] = ResultState.CANCELED })
        Canvas(
            modifier = Modifier
                .size(radius)
                .align(Alignment.Center)
                .padding(4.dp)
        ) {
            drawArc(
                color = color,
                -90f,
                360f * curPercentage,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}

@Composable
fun ImageButton(id: Int, func: () -> Unit) {
    Box(
        modifier = Modifier
            .alpha(0.7f)
            .fillMaxSize()
            .background(color = Color.Black)
            .clickable(onClick = func)
    )
    Icon(
        painter = painterResource(id = id),
        contentDescription = "cross",
        tint = Color.White
    )
}
