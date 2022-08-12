package com.veco.vecoapp.android.ui.screen.account

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.R
import com.veco.vecoapp.android.ui.component.misc.VecoSuggestionCard
import com.veco.vecoapp.android.ui.theme.red
import com.veco.vecoapp.android.ui.theme.regBody1
import com.veco.vecoapp.android.ui.theme.spacing
import dev.icerock.moko.graphics.colorInt

data class AccountOption(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val labelColor: Color = Color.Unspecified,
    val hasArrow: Boolean = true,
    val onClick: (navController: NavController) -> Unit
)

val accountOptions = listOf(
    AccountOption(
        icon = R.drawable.ic_account_profile,
        title = R.string.account_data,
        onClick = {
            it.navigate(AccountScreen.PersonalData.route)
        }
    ),
    AccountOption(
        icon = R.drawable.ic_account_lock,
        title = R.string.account_change_pwd,
        onClick = {
        }
    ),
    AccountOption(
        icon = R.drawable.ic_account_notification,
        title = R.string.account_notifications,
        onClick = {
        }
    ),
    AccountOption(
        icon = R.drawable.ic_account_feedback,
        title = R.string.account_feedback,
        onClick = {
        }
    ),
    AccountOption(
        icon = R.drawable.ic_account_policy,
        title = R.string.account_policy,
        onClick = {
        }
    ),
    AccountOption(
        icon = R.drawable.ic_account_exit,
        title = R.string.exit,
        labelColor = Color(MR.colors.red.color.colorInt()),
        hasArrow = false,
        onClick = {
        }
    )
)

@Composable
fun AccountHome(navController: NavController? = null) {
    Column(modifier = Modifier.padding(MaterialTheme.spacing.medium, MaterialTheme.spacing.small)) {
        VecoSuggestionCard {
            Image(
                modifier = Modifier
                    .size(60.dp)
                    .weight(2f, false),
                painter = painterResource(id = MR.images.gift_icon.drawableResId),
                contentDescription = null
            )
            Text(
                modifier = Modifier.weight(10f, true),
                text = "Обменивайте баллы на подарки от партнеров",
                style = MaterialTheme.typography.body1
            )
        }
        Column(modifier = Modifier.padding(0.dp, MaterialTheme.spacing.medium)) {
            accountOptions.forEach {
                AccountOptionButton(
                    icon = it.icon,
                    text = it.title,
                    onClick = it.onClick,
                    navController = navController!!,
                    labelColor = it.labelColor,
                    hasArrow = it.hasArrow
                )
            }
        }
    }
}

@Composable
fun AccountOptionButton(
    @DrawableRes icon: Int,
    @StringRes text: Int,
    onClick: (navController: NavController) -> Unit,
    navController: NavController,
    labelColor: Color = Color.Unspecified,
    hasArrow: Boolean = true
) {
    Row(
        modifier = Modifier
            .clickable { onClick(navController) }
            .padding(0.dp, 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            tint = Color.Unspecified,
            contentDescription = null
        )
        Text(
            text = stringResource(id = text),
            style = MaterialTheme.typography.regBody1,
            color = labelColor
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            if (hasArrow) {
                Icon(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    painter = painterResource(id = R.drawable.ic_veco_arrow),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
fun AccountHomePreview() {
    AccountHome()
}
