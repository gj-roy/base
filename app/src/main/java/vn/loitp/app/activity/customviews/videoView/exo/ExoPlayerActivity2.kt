package vn.loitp.app.activity.customviews.videoView.exo

import android.content.res.Configuration
import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LScreenUtil
import com.loitpcore.views.exo.PlayerManager
import kotlinx.android.synthetic.main.activity_video_exo_player2.*
import kotlinx.android.synthetic.main.exo_playback_control_view.*
import vn.loitp.app.R

// custom UI exo_playback_control_view.xml

@LogTag("ExoPlayerActivity2")
@IsFullScreen(false)
class ExoPlayerActivity2 : BaseFontActivity() {
    private var playerManager: PlayerManager? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_video_exo_player2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        playerView.useController = false
        val linkPlay =
            "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd"
        playerManager = PlayerManager(this)

        controls.showTimeoutMs = 0
        playerManager?.init(context = this, playerView = playerView, linkPlay = linkPlay)
        controls.player = playerManager?.player
        playerManager?.updateSizePlayerView(playerView = playerView, exoFullscreen = exo_fullscreen)
        exo_fullscreen.setOnClickListener {
            playerManager?.toggleFullscreen(this)
        }
    }

    override fun onResume() {
        super.onResume()
        playerManager?.resumeVideo()
    }

    public override fun onPause() {
        super.onPause()
        playerManager?.pauseVideo()
    }

    public override fun onDestroy() {
        playerManager?.release()
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (LScreenUtil.isLandscape()) {
            playerManager?.toggleFullscreen(this)
        } else {
            super.onBackPressed()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            playerManager?.updateSizePlayerView(
                playerView = playerView,
                exoFullscreen = exo_fullscreen
            )
        } else {
            playerManager?.updateSizePlayerView(
                playerView = playerView,
                exoFullscreen = exo_fullscreen
            )
        }
    }
}