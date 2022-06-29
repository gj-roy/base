package vn.loitp.app.activity.customviews.button.autosizebutton

import android.os.Bundle
import android.view.View
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LScreenUtil
import kotlinx.android.synthetic.main.activity_button_autosize.*
import vn.loitp.app.R

@LogTag("AutoSizeButtonActivity")
@IsFullScreen(false)
class AutoSizeButtonActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_button_autosize
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        btRotate.setOnClickListener(this)

        bt0.setPortraitSizeWInDp(50f)
        bt0.setPortraitSizeHInDp(50f)
        bt0.setLandscapeSizeWInDp(250f)
        bt0.setLandscapeSizeHInDp(250f)
        bt0.setOnClickListener(this)

        bt1.setPortraitSizeWInDp(150f)
        bt1.setPortraitSizeHInDp(150f)
        bt1.setLandscapeSizeWInDp(100f)
        bt1.setLandscapeSizeHInDp(100f)
        bt1.setOnClickListener(this)

        bt2.setPortraitSizeWInPx(LScreenUtil.screenWidth)
        bt2.setPortraitSizeHInPx(LScreenUtil.screenWidth / 10)
        bt2.setLandscapeSizeWInPx(LScreenUtil.screenWidth / 2)
        bt2.setLandscapeSizeHInPx(LScreenUtil.screenWidth / 2)
        bt2.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btRotate -> LActivityUtil.toggleScreenOritation(this)
            bt0, bt1, bt2 -> showShortInformation("Click")
        }
    }
}
