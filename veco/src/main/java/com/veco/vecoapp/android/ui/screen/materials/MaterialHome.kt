package com.veco.vecoapp.android.ui.screen.materials

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.veco.vecoapp.android.R
import com.veco.vecoapp.android.ui.component.ConnectionCheckScaffold
import com.veco.vecoapp.android.ui.navigation.Screen
import com.veco.vecoapp.android.ui.testMaterialText
import com.veco.vecoapp.android.ui.theme.body3
import com.veco.vecoapp.android.ui.theme.spacing
import com.veco.vecoapp.android.ui.utils.defaultGradient
import com.veco.vecoapp.utils.MaterialElement
import com.veco.vecoapp.utils.MaterialTextParser
import kotlin.random.Random

val parsedMatText = MaterialTextParser.parse(
    testMaterialText
)

data class Material(
    val id: Int,
    val title: String,
    @DrawableRes val image: Int = R.drawable.test_material_img,
    val text: List<MaterialElement> = parsedMatText
)

val materialList =
    listOf(
        Material(
            id = 0,
            title = "Жизнь IT-инженера в Эстонии: тотальная русскоязычность, прекрасная экология"
        ),
        Material(
            id = 1,
            title = "Экология в моде: Upcycling и recycling одежды и предметов интерьера"
        ),
        Material(
            id = 2,
            title = "Арт-экология. Уличные тренды и успешные городские проекты"
        )
    )

@Composable
fun MaterialHome(navController: NavHostController) {
    ConnectionCheckScaffold(onConnection = { }) {
        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            items(materialList) { item ->
                MaterialCard(
                    image = ImageBitmap.imageResource(R.drawable.test_material_img),
                    title = item.title,
                    isPopular = Random.nextBoolean(),
                    onClick = { navController.navigate("${Screen.MaterialDetails.route}/${item.id}") }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MaterialCard(image: ImageBitmap, title: String, isPopular: Boolean, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        onClick = onClick
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .height(216.dp)
                    .fillMaxWidth(),
                bitmap = image,
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Text(text = title, modifier = Modifier.padding(12.dp, 8.dp, 12.dp, 16.dp))
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
                        text = "Популярное",
                        style = MaterialTheme.typography.body3,
                        color = Color.White
                    )
                }
            }
        }
    }
}
