package vn.loitp.app.activity.customviews.wwlvideo.detail

import com.annotation.LogTag
import com.core.base.BaseFragment
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.wwlvideo.utils.WWLVideoDataset

@LogTag("DatabaseFirebaseSignInActivity")
class WWLVideoPlayerFragment : BaseFragment() {
    private var mUrl: String? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.wwl_video_player_fragment
    }

    fun startPlay(item: WWLVideoDataset.DatasetItem) {
        mUrl = item.url
    }

    fun stopPlay() {}

    fun switchFullscreen(selected: Boolean) {
        showShortInformation("switchFullscreen $selected", true)
    }

    fun hideControls() {}

    fun toggleControls() {
        showShortInformation("toggleControls", true)
    }
}
