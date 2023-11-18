package com.kaleksandra.corenavigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class Screen(
    val route: Direction,
    @StringRes val resourceId: Int,
    @DrawableRes val iconId: Int
)