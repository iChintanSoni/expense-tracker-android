package dev.chintansoni.expensetracker.ui.util

import android.util.Log
import android.util.Log.ASSERT
import android.util.Log.DEBUG
import android.util.Log.ERROR
import android.util.Log.INFO
import android.util.Log.VERBOSE
import android.util.Log.WARN
import dev.chintansoni.expensetracker.BuildConfig

class Logger() {

    private val isDebugMode: Boolean = BuildConfig.DEBUG

    private fun logAny(priority: Int, tag: String, message: String, throwable: Throwable?) {
        when (priority) {
            DEBUG -> {
                Log.d(tag, message, throwable)
            }
            INFO -> {
                Log.i(tag, message, throwable)
            }
            WARN -> {
                Log.w(tag, message, throwable)
            }
            ERROR -> {
                Log.e(tag, message, throwable)
            }
            ASSERT -> {
                Log.e(tag, message, throwable)
            }
            else -> {
                Log.v(tag, message, throwable)
            }
        }
    }

    fun logDebug(tag: String, message: String, throwable: Throwable?) {
        if (isDebugMode) {
            logAny(DEBUG, tag, message, throwable)
        }
    }

    fun logInfo(tag: String, message: String, throwable: Throwable?) {
        if (isDebugMode) {
            logAny(INFO, tag, message, throwable)
        }
    }

    fun logWarn(tag: String, message: String, throwable: Throwable?) {
        if (isDebugMode) {
            logAny(WARN, tag, message, throwable)
        }
    }

    fun logError(tag: String, message: String, throwable: Throwable?) {
        if (isDebugMode) {
            logAny(ERROR, tag, message, throwable)
        }
    }

    fun logAssert(tag: String, message: String, throwable: Throwable?) {
        if (isDebugMode) {
            logAny(ASSERT, tag, message, throwable)
        }
    }

    fun logVerbose(tag: String, message: String, throwable: Throwable?) {
        if (isDebugMode) {
            logAny(VERBOSE, tag, message, throwable)
        }
    }


}