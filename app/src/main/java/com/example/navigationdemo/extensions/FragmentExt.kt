package com.example.navigationdemo.extensions

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import com.example.navigationdemo.MainActivity
import org.jetbrains.anko.coroutines.experimental.Ref
import org.jetbrains.anko.coroutines.experimental.asReference

fun Fragment.postDelay(time: Long, block: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(block, time)
}

fun Fragment.getMainActivity(): Ref<MainActivity>? = (activity as? MainActivity)?.asReference()