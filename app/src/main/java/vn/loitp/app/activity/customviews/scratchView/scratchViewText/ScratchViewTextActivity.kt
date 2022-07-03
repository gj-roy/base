package vn.loitp.app.activity.customviews.scratchView.scratchViewText

import android.annotation.SuppressLint
import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.views.scratchView.LScratchTextView
import kotlinx.android.synthetic.main.activity_scratch_view_text.*
import vn.loitp.app.R

@LogTag("ScratchViewTextActivity")
@IsFullScreen(false)
class ScratchViewTextActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_scratch_view_text
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        scratchViewTextView.setRevealListener(object : LScratchTextView.IRevealListener {

            override fun onRevealed(tv: LScratchTextView) {
                textView.text = "onRevealed"
            }

            override fun onRevealPercentChangedListener(stv: LScratchTextView, percent: Float) {
                textView.text = "onRevealPercentChangedListener percent: $percent"
            }
        })
    }
}
