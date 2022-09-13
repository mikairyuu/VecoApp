package com.veco.vecoapp.android.ui.screen.materials

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.component.scaffold.ConnectionCheckScaffold
import com.veco.vecoapp.android.ui.navigation.Screen
import com.veco.vecoapp.android.ui.theme.body3
import com.veco.vecoapp.android.ui.theme.spacing
import com.veco.vecoapp.android.ui.utils.defaultGradient
import com.veco.vecoapp.android.ui.utils.shimmer
import com.veco.vecoapp.domain.entity.Material
import com.veco.vecoapp.presentation.UIState
import com.veco.vecoapp.presentation.material.MaterialViewModel

@Composable
fun MaterialHome(navController: NavHostController, viewModel: MaterialViewModel = viewModel()) {
    val listState by viewModel.materialListState.collectAsState()
    ConnectionCheckScaffold(
        onConnection = { viewModel.invalidate(); viewModel.loadNextPage() },
        shouldDisplay = listState is UIState.Success && (listState as UIState.Success).data.isEmpty()
    ) {
        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            items(
                if (listState is UIState.Success) {
                    (listState as UIState.Success<MutableList<Material>>).data
                } else {
                    viewModel.placeholderMaterialList
                }
            ) { item ->
                var image by remember { mutableStateOf<ImageBitmap?>(null) }
                LaunchedEffect(listState) {
                    if (listState is UIState.Success) {
                        viewModel.downloadImage(item.imagePaths.get(0)) {
                            image = BitmapFactory.decodeByteArray(it, 0, it.size).asImageBitmap()
                        }
                    }
                }
                MaterialCard(
                    image = image,
                    title = item.title,
                    isPopular = item.category == 1,
                    onClick = {
                        navController.navigate("${Screen.MaterialDetails.route}/${item.id}")
                    },
                    isLoading = listState is UIState.Loading
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MaterialCard(
    image: ImageBitmap?,
    title: String,
    isPopular: Boolean,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        onClick = { if (!isLoading) onClick() }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .height(216.dp)
                    .fillMaxWidth()
                    .shimmer(image == null),
                painter = if (image == null) {
                    ColorPainter(Color.Unspecified)
                } else {
                    BitmapPainter(
                        image
                    )
                },
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = title,
                modifier = Modifier
                    .padding(12.dp, 8.dp, 12.dp, 16.dp)
                    .shimmer(isLoading)
            )
        }
        if (isPopular) {
            Box(modifier = Modifier.padding(12.dp, 12.dp, 0.dp, 0.dp)) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .defaultGradient()
                        .padding(MaterialTheme.spacing.small, MaterialTheme.spacing.mini)
                ) {
                    Text(
                        text = stringResource(id = MR.strings.material_popular.resourceId),
                        style = MaterialTheme.typography.body3,
                        color = Color.White
                    )
                }
            }
        }
    }
}
