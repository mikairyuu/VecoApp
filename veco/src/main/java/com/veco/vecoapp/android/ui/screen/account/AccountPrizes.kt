package com.veco.vecoapp.android.ui.screen.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.R
import com.veco.vecoapp.android.ui.SheetSettings
import com.veco.vecoapp.android.ui.component.misc.SuggestionCardBase
import com.veco.vecoapp.android.ui.theme.regBody3
import com.veco.vecoapp.android.ui.theme.secondaryText
import com.veco.vecoapp.android.ui.theme.spacing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random

data class PrizeOption(val name: String, val description: String, val price: Int)

val prizeList = listOf(
    PrizeOption(
        "Подписка Яндекс Плюс на месяц",
        "Смотрите фильмы на КиноПоиск HD, слушайте треки на Музыке," +
            " получайте и тратьте кешбэк баллами на сервисах Яндекса.",
        2500
    ),
    PrizeOption(
        "Скидка 10% при заказе от 1000 ₽",
        "Об оформлении стола с легкостью позаботится Delivery Club. На выбор тысячи ресторанов " +
            "и кафе на любой вкус и бюджет. Осталось только выбрать, и любимая еда уже на пути к вам.",
        2250
    ),
    PrizeOption(
        "Скидка 1% при покупке любого курса",
        "СберМаркет — это онлайн-сервис доставки продуктов и товаров из популярных магазинов в" +
            " вашем городе. Внимательные сборщики отбирают свежие и качественные продукты, а" +
            " курьеры доставляют заказ прямо до двери в удобный временной интервал.",
        200
    )
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AccountPrizes(
    bottomSheetState: MutableState<SheetSettings>,
    coroutineScope: CoroutineScope
) {
    val context = LocalContext.current
    Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
        SuggestionCardBase {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                text = stringResource(id = MR.strings.account_point_count.resourceId, 150),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight(700),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.size(MaterialTheme.spacing.medium))
        LazyColumn {
            items(10) {
                val prize = prizeList.random()
                val color = Color(Random.nextInt())
                PrizeRow(
                    title = prize.name,
                    price = prize.price,
                    desc = prize.description,
                    color = color
                ) {
                    bottomSheetState.value = SheetSettings(
                        state = bottomSheetState.value.state,
                        title = prize.name,
                        desc = prize.description,
                        points = prize.price,
                        buttonText = context.getString(MR.strings.button_acquire.resourceId),
                        color = color
                    )
                    coroutineScope.launch {
                        bottomSheetState.value.state.show()
                    }
                }
            }
        }
    }
}

@Composable
fun PrizeRow(title: String, price: Int, desc: String, color: Color, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(0.dp, 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = title, style = MaterialTheme.typography.body2)
            Text(
                text = desc,
                style = MaterialTheme.typography.regBody3,
                color = MaterialTheme.colors.secondaryText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = price.toString(),
                    style = MaterialTheme.typography.body2
                )
                Spacer(modifier = Modifier.width(2.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_veco_pts_clr),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }
    }
}
