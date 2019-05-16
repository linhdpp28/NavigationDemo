package linhdo.inface.extensions

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

fun Application.getSavedConfigPrefs(): SharedPreferences = getSharedPreferences("CONFIG", Context.MODE_PRIVATE)

fun Application.getFBRef() = FirebaseDatabase.getInstance().reference

fun Application.getFBAuth() = FirebaseAuth.getInstance()