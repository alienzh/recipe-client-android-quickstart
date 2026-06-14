package io.agora.scene.convoai

import android.util.Log

/**
 * Minimal logging shim for the vendored convoaiApi toolkit.
 *
 * The upstream Conversational-AI-Demo wires CovLogger to the XLog library plus
 * app-specific printers. This quickstart only needs the toolkit's log calls to
 * reach logcat, so we route them to android.util.Log under the same package the
 * vendored sources import (io.agora.scene.convoai.CovLogger).
 */
internal object CovLogger {
    fun v(tag: String, message: String) { Log.v(tag, message) }
    fun d(tag: String, message: String) { Log.d(tag, message) }
    fun i(tag: String, message: String) { Log.i(tag, message) }
    fun w(tag: String, message: String) { Log.w(tag, message) }
    fun e(tag: String, message: String) { Log.e(tag, message) }
    fun json(tag: String, json: String) { Log.d(tag, json) }
    fun xml(tag: String, xml: String) { Log.d(tag, xml) }
}
