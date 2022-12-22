package vn.loitp.app.activity.customviews.switchToggle.appCompatWwitch

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LStoreUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_switch_appcompat.*
import vn.loitp.R

@LogTag("AppcompatSwitchActivity")
@IsFullScreen(false)
class AppcompatSwitchActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_switch_appcompat
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = AppcompatSwitchActivity::class.java.simpleName
        }
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
