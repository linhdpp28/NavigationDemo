package linhdo.inface.extensions

import android.os.Handler
import android.os.Looper
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import linhdo.inface.features.MainActivity
import org.jetbrains.anko.coroutines.experimental.Ref
import org.jetbrains.anko.coroutines.experimental.asReference

fun Fragment.postDelay(time: Long, block: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(block, time)
}

fun Fragment.getMainActivity(): Ref<MainActivity>? = (activity as? MainActivity)?.asReference()

fun Fragment.getAuthInstance() = FirebaseAuth.getInstance()

fun Fragment.navigationTo(@IdRes action: Int) {
    Navigation.findNavController(view ?: return).navigate(action)
}