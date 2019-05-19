package linhdo.inface.extensions

import android.app.Activity
import org.jetbrains.anko.startActivity

/***
 * @author: Lac
 * 19-May-19 - 4:54 PM
 */

inline fun <reified T : Activity> Activity.startActivity(isFinish: Boolean = true) {
    startActivity<T>()
    if (isFinish) finish()
}
