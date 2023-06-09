package com.smogunov.foods.utils

import android.util.Log

const val GLOBAL_TAB_LOG = "GLOBAL_TAB_LOG"
fun log(message: String) {
    Log.d(GLOBAL_TAB_LOG, message)
}