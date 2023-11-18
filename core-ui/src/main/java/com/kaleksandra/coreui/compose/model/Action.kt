package com.kaleksandra.coreui.compose.model

import androidx.compose.ui.graphics.vector.ImageVector

sealed class Action
data class IconAction(
    val imageVector: ImageVector,
    val contentDescription: String,
    val action: () -> Unit,
) : Action()

data class TextAction(
    val text: String,
    val action: () -> Unit,
) : Action()