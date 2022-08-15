package com.veco.vecoapp.android.ui.screen.review

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.R
import com.veco.vecoapp.android.ui.component.misc.VecoButton
import com.veco.vecoapp.android.ui.theme.lightGray

@Composable
fun ReviewRoute() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .padding(top = 184.dp, start = 102.dp, end = 102.dp)
                .size(156.dp),
            bitmap = ImageBitmap.imageResource(R.drawable.sand_clock_img),
            contentDescription = "sand_clock"

        )
        Text(
            modifier = Modifier.padding(top = 32.dp, start = 24.dp, end = 24.dp),
            text = stringResource(MR.strings.task_sent_1.resourceId),
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h2
        )
        Text(
            modifier = Modifier.padding(top = 4.dp, start = 24.dp, end = 24.dp),
            text = stringResource(MR.strings.task_sent_2.resourceId),
            color = MaterialTheme.colors.lightGray,
            style = MaterialTheme.typography.body1
        )

        Box(
            Modifier
                .fillMaxSize()
                .padding(bottom = 32.dp, top = 256.dp, start = 16.dp, end = 16.dp)
        ) {
            VecoButton(
                text = stringResource(MR.strings.button_continue.resourceId)
            ) {}
        }
    }
}

@Preview
@Composable
fun ReviewRoutePreview() {
    ReviewRoute()
}
