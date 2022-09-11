package com.veco.vecoapp.android.ui.screen.map

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.R
import com.veco.vecoapp.android.ui.component.input.VecoSearchTextField
import com.veco.vecoapp.android.ui.component.misc.VecoButton
import com.veco.vecoapp.android.ui.component.scaffold.ConnectionCheckScaffold
import com.veco.vecoapp.android.ui.theme.spacing
import com.veco.vecoapp.data.PersistentDataManager
import com.veco.vecoapp.presentation.map.MapHomeViewModel
import com.veco.vecoapp.presentation.misc.AlertType
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.logo.HorizontalAlignment
import com.yandex.mapkit.logo.VerticalAlignment
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.launch

@Composable
fun MapHome(
    navController: NavController,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: MapHomeViewModel = viewModel()
) {
    val mapView: MutableState<MapView?> = remember { mutableStateOf(null) }
    val userLocationLayer: MutableState<UserLocationLayer?> = remember { mutableStateOf(null) }
    val ctx = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            coroutineScope.launch {
                PersistentDataManager.makeAlert(AlertType.CommonAlert(MR.strings.map_permission_denied.desc()))
            }
        }
    }

    val zoomIntoUserLocation: (Float) -> Unit = { duration: Float ->
        userLocationLayer.value!!.cameraPosition()?.let {
            mapView.value?.map?.move(
                CameraPosition(it.target, 15.0f, 0.0f, 0.0f),
                Animation(Animation.Type.SMOOTH, duration),
                null
            )
        }
    }

    ConnectionCheckScaffold(onConnection = {}) {
        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_START) {
                    MapKitFactory.initialize(ctx)
                    MapKitFactory.getInstance().onStart()
                    mapView.value?.onStart()
                } else if (event == Lifecycle.Event.ON_STOP) {
                    MapKitFactory.getInstance().onStop()
                    mapView.value?.onStop()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }

        MapView(mapView, userLocationLayer, zoomIntoUserLocation)
        MapContents(onClose = { navController.navigateUp() }, onLocation = {
            if (ContextCompat.checkSelfPermission(
                    ctx,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                        ctx,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            } else {
                zoomIntoUserLocation(0.5f)
            }
        }, viewModel)
    }
}

@Composable
fun MapView(
    mapView: MutableState<MapView?>,
    userLocationLayer: MutableState<UserLocationLayer?>,
    zoomIntoUserLocation: (Float) -> Unit
) {
    var isInflated by remember { mutableStateOf(false) }
    AndroidView(
        factory = { context ->
            isInflated = false
            MapView(context)
        }
    ) {
        mapView.value = it
        it.onStart()
        mapView.value!!.map.logo.setAlignment(
            com.yandex.mapkit.logo.Alignment(
                HorizontalAlignment.CENTER,
                VerticalAlignment.TOP
            )
        )
        if (!isInflated) {
            isInflated = true
            val mapKit = MapKitFactory.getInstance()
            mapKit.resetLocationManagerToDefault()
            userLocationLayer.value = mapKit.createUserLocationLayer(it.mapWindow)
            userLocationLayer.value!!.isVisible = true
            userLocationLayer.value!!.isHeadingEnabled = true
            userLocationLayer.value!!.setObjectListener(object : UserLocationObjectListener {
                override fun onObjectAdded(p0: UserLocationView) = Unit
                override fun onObjectRemoved(p0: UserLocationView) = Unit
                override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {
                    userLocationLayer.value!!.cameraPosition()?.let {
                        zoomIntoUserLocation(1f)
                    }
                }
            })
            mapView.value!!.map.move(
                CameraPosition(Point(55.751244, 37.618423), 11.0f, 0.0f, 0.0f),
                Animation(Animation.Type.SMOOTH, 0f),
                null
            )
        }
    }
}

@Composable
fun MapContents(onClose: () -> Unit, onLocation: () -> Unit, viewModel: MapHomeViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp)
        ) {
            SquareButton(
                modifier = Modifier.align(Alignment.CenterStart),
                icon = R.drawable.ic_cross,
                onClick = { onClose() }
            )
            SquareButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                icon = R.drawable.ic_location,
                onClick = {
                    onLocation()
                }
            )
        }
        Card(
            modifier = Modifier.align(Alignment.BottomCenter),
            shape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp)
        ) {
            Column(
                modifier = Modifier.padding(MaterialTheme.spacing.medium),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = MR.strings.map_card_title.resourceId),
                    style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.Center
                )
                VecoSearchTextField(textState = viewModel.search)
                VecoButton(text = stringResource(id = MR.strings.button_next.resourceId)) {}
            }
        }
    }
}

@Composable
fun SquareButton(modifier: Modifier = Modifier, @DrawableRes icon: Int, onClick: () -> Unit) {
    Button(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.size(48.dp),
        contentPadding = PaddingValues(12.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = icon),
            contentDescription = null
        )
    }
}
