package com.kaleksandra.corecommon.ext

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@SuppressLint("ComposableNaming")
@Composable
inline fun <reified T> Flow<T>.observe(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline action: suspend (T) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.lifecycleScope.launch {
            flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect(action)
        }
    }
}

fun <T> EventChannel() = Channel<T>()

fun <T> Channel<T>.send(scope: CoroutineScope, value: T) {
    scope.launch {
        send(value)
    }
}