package com.smogunov.foods.utils

import android.util.Log

const val GLOBAL_TAG_LOG = "GLOBAL_TAG_LOG"
fun log(message: String) {
    Log.d(GLOBAL_TAG_LOG, message)
}