package com.kaleksandra.corecommon.ext

import android.util.Log

private const val DEBUG_TAG = "DEBUG"

fun <Data> debug(data: Data, tag: String? = DEBUG_TAG) {
    Log.d(tag, data.toString())
}