package com.kaleksandra.corenavigation

sealed class Direction(val path: String)
object MainDirection : Direction("main")