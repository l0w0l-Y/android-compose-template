package com.kaleksandra.coreui.compose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun string(@StringRes id: Int) = stringResource(id = id)

@Composable
fun string(@StringRes id: Int, vararg args: Any) = stringResource(id = id, *args)

@Composable
fun painter(@DrawableRes id: Int) = painterResource(id = id)