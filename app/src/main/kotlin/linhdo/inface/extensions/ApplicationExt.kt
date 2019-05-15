package linhdo.inface.extensions

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

fun Application.getSavedConfigPrefs(): SharedPreferences = getSharedPreferences("CONFIG", Context.MODE_PRIVATE)