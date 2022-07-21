package vn.loitp.app.activity.customviews.seekBar.verticalSeekBar

import android.os.Bundle
import android.widget.SeekBar
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_seekbar_vertical.*
import vn.loitp.app.R

// https://github.com/h6ah4i/android-verticalseekbar

@LogTag("VerticalSeekbarActivity")
@IsFullScreen(false)
class VerticalSeekbarActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_seekbar_vertical
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        seekBar1.max = 100
        seekBar1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                logD("onProgressChanged $progress")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                logD("onStartTrackingTouch")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                logD("onStopTrackingTouch")
            }
        })
        btSetProgress.setOnClickListener {
            seekBar1.progress = 30
        }
    }
}