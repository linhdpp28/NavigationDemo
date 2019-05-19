package linhdo.inface.extensions

import android.app.Activity
import android.os.Handler
import android.os.Looper
import androidx.annotation.IdRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import linhdo.inface.R
import linhdo.inface.features.auth.MainActivity
import org.jetbrains.anko.startActivity

fun Fragment.postDelay(time: Long, block: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(block, time)
}

fun Fragment.getMainActivity(): MainActivity? = (activity as? MainActivity)

inline fun <reified T : Activity> Fragment.startActivity(isFinish: Boolean = true) {
    activity?.startActivity<T>()
    if (isFinish) activity?.finish()
}

fun Fragment.navigationTo(@IdRes action: Int) {
    Navigation.findNavController(view ?: return).navigate(action)
}

fun Fragment.setSubTitle(title: String) {
    activity?.findViewById<Toolbar>(R.id.toolbar)?.subtitle = title
}