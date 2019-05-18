package linhdo.inface.extensions

import android.app.Activity
import android.os.Handler
import android.os.Looper
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import linhdo.customviews.InFaceToolbar
import linhdo.customviews.logging.InLogging
import linhdo.customviews.logging.Logger
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

//fun Fragment.setTitle(title: String) {
//    activity?.findViewById<InFaceToolbar>(R.id.toolbar)?.setTitle(title)
//}

fun Fragment.setSubTitle(title: String) {
    activity?.findViewById<InFaceToolbar>(R.id.toolbar)?.setSubTitle(title)
}

/***
 * LOGGER
 */
fun Fragment.error(message: String, isShowDialog: Boolean = true) {
    InLogging(context = if (isShowDialog) requireContext() else null, message = message, type = Logger.ERROR)
}

fun Fragment.debug(message: String) {
    InLogging(message = message, type = Logger.DEBUG)
}