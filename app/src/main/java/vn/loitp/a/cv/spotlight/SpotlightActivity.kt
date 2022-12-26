package vn.loitp.a.cv.spotlight

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import com.matadesigns.spotlight.SpotlightBuilder
import com.matadesigns.spotlight.abstraction.SpotlightListener
import kotlinx.android.synthetic.main.a_spotlight.*
import vn.loitp.R

@LogTag("SpotlightActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class SpotlightActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_spotlight
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        setupSpotlight()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.apply {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = this,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/NicholasMata/Spotlight"
                        )
                    }
                )
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = SpotlightActivity::class.java.simpleName
        }
    }

    private fun setupSpotlight() {
        var builder: SpotlightBuilder? = null
        builder = SpotlightBuilder(this)
            .setInset(20)
            .setTargetView(firstView)
            .setTitle("First View")
            .setDescription("This is the first view")
            .setListener(object : SpotlightListener {
                override fun onEnd(targetView: View?) {
                    // This is called when a target view has been dismissed
                    when (targetView) {
                        firstView -> {
                            // When the first view's spotlight ends then set the
                            // target view to the second view.
                            // Update title and description
                            builder?.setTitle("Second View")
                                ?.setDescription("This is the second view")
                                ?.setTargetView(secondView)
                        }
                        secondView -> {
                            // When the second view's spotlight ends don't continue on.
                            return
                        }
                    }
                    builder?.build()?.startSpotlight()
                }

                override fun onStart(targetView: View?) {}
            })
        builder.build().startSpotlight()
    }
}
