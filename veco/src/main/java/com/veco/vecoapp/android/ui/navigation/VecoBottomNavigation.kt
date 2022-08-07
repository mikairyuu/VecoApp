package com.veco.vecoapp.android.ui.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.veco.vecoapp.MR
import dev.icerock.moko.graphics.colorInt

@Composable
fun VecoBottomNavigation() {
    var selectedItem by remember { mutableStateOf(VecoDestinations.HOME_ROUTE) }
    BottomNavigation(backgroundColor = Color.White) {
        destinationList.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(item.icon), contentDescription = "navigation icon") },
                label = {
                    Text(
                        stringResource(item.text),
                        style = MaterialTheme.typography.caption
                    )
                },
                selected = selectedItem == item,
                unselectedContentColor = Color(MR.colors.light_grey.color.colorInt()),
                selectedContentColor = Color(MR.colors.brand_default.color.colorInt()),
                onClick = { selectedItem = item }
            )
        }
    }
}