package linhdo.inface.extensions

import android.os.Handler
import android.os.Looper
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import linhdo.inface.features.MainActivity

fun Fragment.postDelay(time: Long, block: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(block, time)
}

fun Fragment.getMainActivity(): MainActivity? = (activity as? MainActivity)

fun Fragment.navigationTo(@IdRes action: Int) {
    Navigation.findNavController(view ?: return).navigate(action)
}