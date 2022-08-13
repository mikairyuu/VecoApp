package com.veco.vecoapp.android.ui.screen.confimation

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.R
import com.veco.vecoapp.android.ui.component.misc.VecoButton

@Composable
fun ConfirmationRoute() {
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
            ) {}
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
    var imageUri = remember {
        mutableStateListOf<Uri?>(null)
    }
    repeat(5) {
        imageUri.add(null)
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
                imageCount.value++
            }
        }
    )
    PhotoGrid(photos = imageUri, imagePicker = imagePicker, imageCount = imageCount)
}

@Composable
fun PhotoGrid(
    photos: SnapshotStateList<Uri?>,
    imagePicker: ManagedActivityResultLauncher<String, Uri?>,
    imageCount: MutableState<Int>
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
                AddImg(imagePicker = imagePicker, imageCount = imageCount.value)
            }
        }
        items(photos) { photo ->
            if (photo != null) {
                Box(
                    modifier = Modifier
                        .size(104.dp, 104.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color.Gray)
                ) {
                    AsyncImage(
                        contentScale = ContentScale.Inside,
                        model = photo,

                        contentDescription = "Selected image"
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_delete_button),
                        contentDescription = "Delete image",
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                            .clickable(
                                enabled = true,
                                onClickLabel = "Clickable image",
                                onClick = {
                                    photos.removeAt(photos.indexOf(photo))
                                    imageCount.value--
                                }
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun AddImg(imagePicker: ManagedActivityResultLauncher<String, Uri?>, imageCount: Int) {
    Button(
        onClick = {
            if (imageCount < 5) {
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

@Preview
@Composable
fun ConfirmationPreview() {
    ConfirmationRoute()
}
