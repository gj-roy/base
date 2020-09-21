package vn.loitp.app.activity.customviews.button.qbutton

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_button_q.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_button_q)
@LogTag("QButtonActivity")
@IsFullScreen(false)
class QButtonActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn.setCornerRadious(5)
        btn.setStrokeWidth(5)
        btn.setStrokeDashGap(5)
        btn.setStrokeDashWidth(90)
        btn.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
        btn.setStrokeColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
    }
}
