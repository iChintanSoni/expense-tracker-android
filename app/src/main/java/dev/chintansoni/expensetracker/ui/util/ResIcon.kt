package dev.chintansoni.expensetracker.ui.util

import androidx.annotation.DrawableRes
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

@Composable
fun DrawableIcon(@DrawableRes resId: Int, contentDescription: String? = null) {
    Icon(
        painter = painterResource(id = resId),
        contentDescription = contentDescription
    )
}