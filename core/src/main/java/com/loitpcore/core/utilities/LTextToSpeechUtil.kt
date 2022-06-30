package com.loitpcore.core.utilities

import android.speech.tts.TextToSpeech
import java.util.*

class LTextToSpeechUtil private constructor() : TextToSpeech.OnInitListener {
    companion object {
        val instance = LTextToSpeechUtil()
    }

    private val logTag = javaClass.simpleName
    private var tts: TextToSpeech? = null

    fun setupTTS() {
        tts = TextToSpeech(LAppResource.application, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                LLog.d(logTag, "This Language is not supported")
            } else {
                // speakOut("Example");
            }
        } else {
//            Log.d("TTS", "Initilization Failed!")
        }
    }

    @Suppress("DEPRECATION")
    fun speakOut(text: String) {
        if (tts == null) {
            return
        }
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun destroy() {
        tts?.let {
            it.stop()
            it.shutdown()
        }
    }
}