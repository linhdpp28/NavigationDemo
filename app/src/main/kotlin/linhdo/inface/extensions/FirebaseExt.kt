package linhdo.inface.extensions

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

inline fun <reified T> T.getFBDatabase(): FirebaseFirestore = Firebase.firestore

inline fun <reified T> T.getFBAuth(): FirebaseAuth = FirebaseAuth.getInstance()