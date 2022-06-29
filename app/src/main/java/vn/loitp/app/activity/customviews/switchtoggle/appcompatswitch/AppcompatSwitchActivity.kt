package vn.loitp.app.activity.customviews.switchtoggle.appcompatswitch

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LStoreUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_switch_appcompat.*
import vn.loitp.app.R

@LogTag("AppcompatSwitchActivity")
@IsFullScreen(false)
class AppcompatSwitchActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_switch_appcompat
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        textView.text = LStoreUtil.readTxtFromRawFolder(nameOfRawFile = R.raw.lswitch)

        val isDarkTheme = LUIUtil.isDarkTheme()
        sw.isChecked = isDarkTheme

        sw.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                LUIUtil.setDarkTheme(isDarkTheme = true)
            } else {
                LUIUtil.setDarkTheme(isDarkTheme = false)
            }
        }
    }
}
