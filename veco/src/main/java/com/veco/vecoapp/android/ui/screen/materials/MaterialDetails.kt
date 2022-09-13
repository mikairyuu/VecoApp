package com.veco.vecoapp.android.ui.screen.materials

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.veco.vecoapp.android.ui.component.misc.VecoProgressIndicator
import com.veco.vecoapp.android.ui.theme.regBody1
import com.veco.vecoapp.android.ui.theme.regBody2
import com.veco.vecoapp.android.ui.theme.secondaryText
import com.veco.vecoapp.android.ui.theme.spacing
import com.veco.vecoapp.domain.entity.Material
import com.veco.vecoapp.presentation.UIState
import com.veco.vecoapp.presentation.material.MaterialDetailsViewModel
import com.veco.vecoapp.utils.MaterialElemType

@Composable
fun MaterialDetails(id: Int, viewModel: MaterialDetailsViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        viewModel.getMaterial(id)
    }
    val material by viewModel.materialState.collectAsState()
    MaterialContents(material, viewModel)
}

@Composable
fun MaterialContents(materialState: UIState<Material>, viewModel: MaterialDetailsViewModel) {
    val material = if (materialState is UIState.Success) materialState.data else null
    if (material == null) {
        Box(modifier = Modifier.fillMaxSize()) {
            VecoProgressIndicator(modifier = Modifier.size(72.dp).align(Alignment.Center))
        }
    } else {
        Column {
            LazyColumn(contentPadding = PaddingValues(16.dp)) {
                item {
                    Row {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(color = Color.Black)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = material.author, style = MaterialTheme.typography.body2)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = material.stringDate,
                            style = MaterialTheme.typography.regBody2,
                            color = MaterialTheme.colors.secondaryText
                        )
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                    Text(text = material.title, style = MaterialTheme.typography.h2)
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                }
                items(viewModel.parsedText) {
                    when (it.type) {
                        MaterialElemType.IMG -> TODO()
                        MaterialElemType.SPACE -> {
                            Spacer(modifier = Modifier.height((it.data as Int).dp))
                        }
                        else -> {
                            Text(
                                text = it.data as String,
                                style = if (it.type == MaterialElemType.B1R) {
                                    MaterialTheme.typography.regBody1
                                } else {
                                    MaterialTheme.typography.body1
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
