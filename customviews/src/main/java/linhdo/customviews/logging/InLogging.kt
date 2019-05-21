package linhdo.customviews.logging

import android.content.Context
import org.jetbrains.anko.*
import java.io.Serializable

/***
 * @author: Lac
 * 18-May-19 - 5:16 PM
 */

enum class LoggerState {
    WARNING,
    ERROR,
    INFO,
    DEBUG,
    VERBOSE
}

@Suppress("DataClassPrivateConstructor")
data class InLogging private constructor(val type: LoggerState, val message: String, val context: Context? = null) : AnkoLogger, Serializable {
    init {
        val i = "|   ${context?.javaClass?.simpleName ?: "INFACE"} : $message   |"
        val line = "-".repeat(i.length)
        val log = "$line\n $i\n $line\n"
        when (type) {
            LoggerState.WARNING -> warn(log)
            LoggerState.ERROR -> {
                error(log)
                context?.alert(message) {
                    okButton { it.dismiss() }
                }?.show()
            }
            LoggerState.INFO -> info(log)
            LoggerState.DEBUG -> debug(log)
            LoggerState.VERBOSE -> verbose(log)
        }
    }

    companion object {
        fun inError(context: Context? = null, message: String) {
            InLogging(LoggerState.ERROR, message, context)
        }

        fun inWarning(message: String) {
            InLogging(LoggerState.WARNING, message)
        }

        fun inDebug(message: String) {
            InLogging(LoggerState.DEBUG, message)
        }

        fun inInfo(message: String) {
            InLogging(LoggerState.INFO, message)
        }

        fun inVerbose(message: String) {
            InLogging(LoggerState.VERBOSE, message)
        }
    }
}