package com.kaleksandra.corenavigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.google.gson.Gson

//TODO: UPDATE ALL NAVIGATION
//TODO: UPDATE NAVIGATION WITH ARGS

fun NavController.navigate(direction: Direction, builder: (NavOptionsBuilder.() -> Unit) = {}) {
    try {
        this.navigate(direction.path, builder)
    } catch (exception: Exception) {
        Log.d("Navigation exception: ", exception.toString())
    }
}

fun NavController.navigateWithArgs(direction: Direction,  vararg args: Any, builder: (NavOptionsBuilder.() -> Unit) = {}){
    try {
        val gson = Gson()
        val json: String = gson.toJson(args)
        this.navigate("${direction.path}/$json", builder)
    } catch (exception: Exception) {
        Log.d("Navigation exception: ", exception.toString())
    }
}

fun NavController.navigateWithArgs(direction: Direction, name: String, vararg args: Any, builder: (NavOptionsBuilder.() -> Unit) = {}){
    try {
        val gson = Gson()
        val json: String = gson.toJson(args)
        this.navigate("${direction.path}?$name=$json", builder)
    } catch (exception: Exception) {
        Log.d("Navigation exception: ", exception.toString())
    }
}

fun NavController.navigate(
    direction: Direction,
    vararg args: Any,
    builder: (NavOptionsBuilder.() -> Unit) = {}
) {
    try {
        this.navigate("${direction.path}/${args.joinToString(",")}", builder)
    } catch (exception: Exception) {
        Log.d("Navigation exception: ", exception.toString())
    }
}