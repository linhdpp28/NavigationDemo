package linhdo.customviews.logging

import android.content.Context
import org.jetbrains.anko.*

/***
 * @author: Lac
 * 18-May-19 - 5:16 PM
 */

class InLogging(context: Context? = null, message: String, private val type: Logger = Logger.DEBUG) : AnkoLogger {
    init {
        logger(context, message)
    }

    private fun logger(context: Context? = null, message: String) {
        val i = "|   $loggerTag : $message   |"
        val line = "-".repeat(i.length)
        val log = "\n$line\n$i\n$line\n"
        when (type) {
            Logger.WARNING -> warn(log)
            Logger.ERROR -> {
                error(log)
                context?.alert(message) {
                    okButton { it.dismiss() }
                }?.show()
            }
            Logger.INFO -> info(log)
            Logger.DEBUG -> debug(log)
            Logger.VERBOSE -> verbose(log)
        }
    }
}

enum class Logger {
    WARNING,
    ERROR,
    INFO,
    DEBUG,
    VERBOSE
}