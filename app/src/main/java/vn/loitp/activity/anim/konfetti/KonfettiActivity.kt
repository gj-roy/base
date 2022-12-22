package vn.loitp.activity.anim.konfetti

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_0.lActionBar
import kotlinx.android.synthetic.main.activity_konfetti.*
import vn.loitp.app.R

@LogTag("KonfettiActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class KonfettiActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_konfetti
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
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
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/DanielMartinus/konfetti"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = KonfettiActivity::class.java.simpleName
        }

        btnFestive.setSafeOnClickListener {
            konfettiView.start(Presets.festive())
        }
        btnExplode.setSafeOnClickListener {
            konfettiView.start(Presets.explode())
        }
        btnParade.setSafeOnClickListener {
            konfettiView.start(Presets.parade())
        }
        btnRain.setSafeOnClickListener {
            konfettiView.start(Presets.rain())
        }
    }
}