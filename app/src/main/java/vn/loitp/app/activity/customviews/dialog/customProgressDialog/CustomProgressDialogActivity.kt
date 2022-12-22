package vn.loitp.app.activity.customviews.dialog.customProgressDialog

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_custom_progress_dialog.*
import vn.loitp.R

@LogTag("CustomProgressDialogActivity")
@IsFullScreen(false)
class CustomProgressDialogActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_custom_progress_dialog
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
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = CustomProgressDialogActivity::class.java.simpleName
        }
        bt0.setSafeOnClickListener {
            showDialogProgress()
            LUIUtil.setDelay(mls = 4000, runnable = {
                hideDialogProgress()
            })
        }
    }
}
