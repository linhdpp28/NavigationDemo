package linhdo.inface.extensions

import android.util.Patterns

fun String?.emailValidator(): Boolean = if (this.isNullOrEmpty()) false
else Patterns.EMAIL_ADDRESS.matcher(this).matches()