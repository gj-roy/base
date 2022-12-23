package vn.loitp.app.a.func.idleTime

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_idle_time.*
import vn.loitp.R

@LogTag("IdleTimeActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class IdleTimeActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_idle_time
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        startIdleTimeHandler(3 * 1000)
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = IdleTimeActivity::class.java.simpleName
        }
        updateText(delayMlsIdleTime = null, isIdleTime = null)
    }

    override fun onActivityUserIdleAfterTime(delayMlsIdleTime: Long, isIdleTime: Boolean) {
        super.onActivityUserIdleAfterTime(delayMlsIdleTime, isIdleTime)
        updateText(delayMlsIdleTime = delayMlsIdleTime, isIdleTime = isIdleTime)
    }

    @SuppressLint("SetTextI18n")
    private fun updateText(delayMlsIdleTime: Long?, isIdleTime: Boolean?) {
        logE("onActivityUserIdleAfterTime delayMlsIdleTime $delayMlsIdleTime, isIdleTime $isIdleTime")
        val tv = AppCompatTextView(this)
        tv.text =
            "onActivityUserIdleAfterTime delayMlsIdleTime $delayMlsIdleTime, isIdleTime $isIdleTime"
        ll.addView(tv)
    }
}