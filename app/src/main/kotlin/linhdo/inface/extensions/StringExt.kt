package linhdo.inface.extensions

import android.util.Patterns

fun String.emailValidator() = Patterns.EMAIL_ADDRESS.matcher(this).matches()