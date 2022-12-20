package com.loitpcore.core.utilities

import android.media.MediaPlayer
import com.loitp.core.utilities.LAppResource
import java.io.IOException

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LSoundUtil {
    companion object {
        fun startMusicFromAsset(fileName: String) {
            val mediaPlayer = MediaPlayer()
            try {
                val assetFileDescriptor = LAppResource.application.assets.openFd(fileName)
                mediaPlayer.setDataSource(
                    assetFileDescriptor.fileDescriptor,
                    assetFileDescriptor.startOffset,
                    assetFileDescriptor.length
                )
                assetFileDescriptor.close()
                mediaPlayer.prepare()
                mediaPlayer.start()
                mediaPlayer.setOnCompletionListener { player ->
                    var mPlayer = player
                    mPlayer?.let {
                        it.stop()
                        it.reset()
                        it.release()
                        mPlayer = null
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
