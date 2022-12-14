package vn.loitp.app.activity.animation.morphTransitions

import android.os.Bundle
import com.loitpcore.animation.morphTransitions.FabTransform
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_morph_transitions_full_screen.*
import vn.loitp.app.R

@LogTag("MorphTransitionsFullScreenActivity")
@IsFullScreen(false)
class MorphTransitionsFullScreenActivity : BaseFontActivity() {
    override fun setLayoutResourceId(): Int {
        return R.layout.activity_morph_transitions_full_screen
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        FabTransform.setup(this, rootView)
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MorphTransitionsFullScreenActivity::class.java.simpleName
        }
    }
}
