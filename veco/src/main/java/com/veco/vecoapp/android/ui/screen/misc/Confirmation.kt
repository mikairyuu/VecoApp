package com.veco.vecoapp.android.ui.screen.misc

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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.R
import com.veco.vecoapp.android.ui.component.MainScaffold
import com.veco.vecoapp.android.ui.component.misc.VecoButton
import com.veco.vecoapp.android.ui.enums.ResultState
import com.veco.vecoapp.android.ui.enums.ToolbarState
import com.veco.vecoapp.android.ui.navigation.Screen

@Composable
fun ConfirmationRoute(navController: NavHostController) {
    MainScaffold("", false, ToolbarState.Collapsed, navController = navController) {
        Column(modifier = Modifier.padding(16.dp)) {
            Headline(stringResource(MR.strings.task_confirm.resourceId))

            Instruction(
                stringResource(MR.strings.task_info_1.resourceId),
                stringResource(MR.strings.task_info_2.resourceId)
            )

            ImagePicker()

            Box(Modifier.fillMaxSize()) {
                VecoButton(
                    text = stringResource(MR.strings.button_send.resourceId),
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) { navController.navigate(Screen.Review.route) }
            }
        }
    }
}

@Composable
fun Headline(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.h1
    )
}

@Composable
fun Instruction(instruction: String, warning: String) {
    val text = buildAnnotatedString {
        append(instruction)
        append(" ")
        pushStyle(SpanStyle(fontWeight = Bold))
        append(warning)
        toString()
    }
    Text(
        text = text,
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.body1,
        modifier = Modifier.padding(top = 12.dp)
    )
}

@Composable
fun ImagePicker() {
    var result = remember {
        mutableStateListOf<ResultState>()
    }
    var imageUri = remember {
        mutableStateListOf<Uri?>()
    }
    repeat(5) {
        imageUri.add(null)
        result.add(ResultState.NONE)
    }

    var imageCount = remember {
        mutableStateOf(0)
    }
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                if (imageUri.count() < 5) {
                    imageUri.add(null)
                }
                imageUri[imageCount.value] = uri
                result[imageCount.value] = ResultState.LOADING
                imageCount.value++
            }
        }
    )

    PhotoGrid(
        photos = imageUri,
        imagePicker = imagePicker,
        imageCount = imageCount,
        result = result
    )
}

@Composable
fun PhotoGrid(
    photos: SnapshotStateList<Uri?>,
    imagePicker: ManagedActivityResultLauncher<String, Uri?>,
    imageCount: MutableState<Int>,
    result: SnapshotStateList<ResultState>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(top = 24.dp),
        modifier = Modifier.fillMaxWidth()

    ) {
        item() {
            Box(
                modifier = Modifier
                    .size(104.dp, 104.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                AddImg(imagePicker = imagePicker, imageCount = imageCount)
            }
        }
        itemsIndexed(photos) { i, photo ->
            if (photo != null) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(104.dp, 104.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color.Gray)
                ) {
                    AsyncImage(
                        modifier = Modifier.alpha(if (result[i] == ResultState.LOADING) 0.5f else 1f),
                        contentScale = ContentScale.Inside,
                        model = photo,
                        contentDescription = "Selected image"
                    )
                    when (result[i]) {
                        ResultState.LOADING -> {
                            Box(modifier = Modifier.padding(32.dp)) {
                                CircularProgressBar(
                                    percentage = 1f,
                                    imageCount = imageCount,
                                    result = result
                                )
                            }
                        }
                        ResultState.SUCCESS -> {
                            Box(modifier = Modifier.align(Alignment.TopEnd)) {
                                CornerImage(id = R.drawable.ic_delete_button) {
                                    photos.removeAt(photos.indexOf(photo))
                                    imageCount.value--
                                }
                            }
                        }
                        ResultState.ERROR -> {
                            ImageButton(R.drawable.ic_resend) {
                                result[i] = ResultState.LOADING
                            }
                            Box(modifier = Modifier.align(Alignment.TopEnd)) {
                                CornerImage(id = R.drawable.ic_error_button) {}
                            }
                        }
                        ResultState.CANCELED -> {
                            photos.removeAt(i)
                            imageCount.value--
                            result[i] = ResultState.NONE
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}

@Composable
fun CornerImage(id: Int, func: () -> Unit) {
    Box(modifier = Modifier.clickable { func() })
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
                "content description"
            )
            Text(text = "Загрузить")
        }
    }
}

@Composable
fun CircularProgressBar(
    percentage: Float,
    /*number: Int,
    frontSize: TextUnit = 28.sp,*/
    radius: Dp = 40.dp,
    color: Color = Color.White,
    strokeWidth: Dp = 4.dp,
    animDuration: Int = 1000,
    animDelay: Int = 500,
    result: SnapshotStateList<ResultState>,
    imageCount: MutableState<Int>

) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    var curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        )
    )
    if (curPercentage.value == percentage) {
        result[imageCount.value - 1] = ResultState.SUCCESS
    }

    LaunchedEffect(key1 = true) {
        if (!animationPlayed) {
            animationPlayed = true
        }
    }
    var func = {
        result[imageCount.value - 1] = ResultState.CANCELED
    }

    Box(
        contentAlignment = Alignment.Center,

        modifier = Modifier
            .size(radius)
            .clip(CircleShape)
    ) {
        ImageButton(R.drawable.ic_cross, func)
        Canvas(
            modifier = Modifier
                .size(radius)
                .align(Alignment.Center)
                .padding(4.dp)
        ) {
            drawArc(
                color = color,
                -90f,
                360f * curPercentage.value,
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
            .clickable {
                func()
            }
    )
    Icon(
        painter = painterResource(id = id),
        contentDescription = "cross",
        tint = Color.White
    )
}
