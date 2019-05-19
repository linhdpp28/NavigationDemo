package linhdo.inface.extensions

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

fun Application.getSavedConfigPrefs(): SharedPreferences = getSharedPreferences("CONFIG", Context.MODE_PRIVATE)

fun Application.getFBDatabase(): FirebaseFirestore = FirebaseFirestore.getInstance()

fun Application.getFBAuth(): FirebaseAuth = FirebaseAuth.getInstance()